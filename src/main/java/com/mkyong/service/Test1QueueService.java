package com.mkyong.service;

import com.mkyong.data.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Test1QueueService {

    private static final Logger LOGGER = LogManager.getLogger(Test1QueueService.class);

    private AtomicInteger deliveredMessages = new AtomicInteger();

    private AtomicInteger lostMessages = new AtomicInteger();

    private Map<String, String> allLostMessages = new HashMap();

    private int sentedMessages = 0;

    private int discardedMessages = 0;

    private final long toDelayChecking = 100L;

    @Value("${jmsConfiguration.concurrentConsumers:0}")
    int concurrentConsumers;

    @Autowired
    private QueueService queueService;

    /**
     * @see https://vertex-academy.com/tutorials/ru/java-8-completablefuture
     */
    public CompletableFuture<Message> countSendMessage(String queue) {
        long requestTimeMillis = System.currentTimeMillis();
        if (concurrentConsumers <= getWaitingMessages()) {
            Message message = new Message(discardedMessages + sentedMessages + 1, requestTimeMillis, 0L);
            LOGGER.warn(">>|  REFUSED MESSAGE = {}       SENTED MESSAGES = {} ({})       DELIVERED MESSAGES = {}       WAITING MESSAGES = {}       AVAILABLE QUEUES = {}", message, sentedMessages, (deliveredMessages.get() + discardedMessages + (sentedMessages -deliveredMessages.get()) + 1), deliveredMessages, getWaitingMessages(), getAvailableQueues());
            discardedMessages++;
            return CompletableFuture.supplyAsync(() -> message);
        } else {
            Message message = new Message(sentedMessages +1, requestTimeMillis, 0L);
            CompletableFuture<Message> sendMessage = CompletableFuture.supplyAsync(() -> sendMessage(queue, message));
            sentedMessages++;
            synchronized (allLostMessages) {
                allLostMessages.put(String.valueOf(message.getId()), String.valueOf(requestTimeMillis));
            }
            return sendMessage;
        }
    }

    private Message sendMessage(String queue, Message message) {
        LOGGER.debug(">>|  SENT MESSAGE = {}       SENTED MESSAGES = {} ({})       DELIVERED MESSAGES = {}       WAITING MESSAGES = {}       AVAILABLE QUEUES = {}", message, sentedMessages, (deliveredMessages.get() + discardedMessages + (sentedMessages -deliveredMessages.get())), deliveredMessages, getWaitingMessages(), getAvailableQueues());

        // TODO:
        String response = queueService.sendMessage(queue, new Gson().toJson(message));
        if (response!=null) {
            synchronized (allLostMessages) {
                Message messageResponse = new Gson().fromJson(response, Message.class);
                messageResponse.setResponseTimeMillis(System.currentTimeMillis());
                LOGGER.debug("|<<   {}", messageResponse);
                deliveredMessages.incrementAndGet();
                allLostMessages.remove(String.valueOf(messageResponse.getId()));
                return messageResponse;
            }
        } else {
            LOGGER.error("|<<   {}", response);
            lostMessages.incrementAndGet();
        }
        return null;
    }

    public int getSentedMessages() {
        return sentedMessages;
    }

    public void getAllLostMessages() {
        while (!isAvailableQueues()) {
            try {
                Thread.sleep(toDelayChecking);
            } catch (InterruptedException e) {}
        }
        allLostMessages();
    }

    public int getWaitingMessages() {
        return sentedMessages -deliveredMessages.get();
    }

    public int getAvailableQueues() {
        int _availableQueues = concurrentConsumers-(sentedMessages -deliveredMessages.get());
        return  (0 < _availableQueues) ? _availableQueues : 0;
    }

    public boolean isAvailableQueues() {
        return ((getWaitingMessages() == 0) && (getAvailableQueues() == getConcurrentConsumers())) ? true : false;
    }

    public int getConcurrentConsumers() {
        return concurrentConsumers;
    }

    private void allLostMessages() {
        LOGGER.info(">>>>>>>>>> SENTED MESSAGES = {} ({})       REFUSED MESSAGES = {}       DELIVERED MESSAGES = {};       WAITING MESSAGES = {}       LOST MESSAGES = {}       AVAILABLE QUEUES = {} <<<<<<<<<<", sentedMessages, (deliveredMessages.get() + discardedMessages + (sentedMessages -deliveredMessages.get())), discardedMessages, deliveredMessages, getWaitingMessages(), (sentedMessages -deliveredMessages.get()), getAvailableQueues());

        LOGGER.error(">>>>>>>>>> ALL LOST MESSAGES <<<<<<<<<<");
        for (Map.Entry<String, String> allLostMessage:  allLostMessages.entrySet()) {
            String strRequestTimeMilliss = allLostMessage.getValue();
            long requestTimeMillis = Long.valueOf(strRequestTimeMilliss);
            float timeToLive = (float) (System.currentTimeMillis() - requestTimeMillis) / 1000;
            allLostMessage.setValue(String.valueOf(timeToLive));
            LOGGER.error("{\"id\":{},\"time\":{}}", allLostMessage.getKey(), allLostMessage.getValue());

        }
    }
}
