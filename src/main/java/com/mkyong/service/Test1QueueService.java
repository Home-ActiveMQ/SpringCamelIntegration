package com.mkyong.service;

import com.mkyong.data.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;
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

    @Value("${jmsConfiguration.concurrentConsumers:0}")
    int concurrentConsumers;

    @Autowired
    private QueueService queueService;

    public int getSentedMessages() {
        return sentedMessages;
    }

    public int getConcurrentConsumers() {
        return concurrentConsumers;
    }

    public void countSendMessage(String queue) {
        long requestTimeMillis = System.currentTimeMillis();
        if (concurrentConsumers <= getWaitingMessages()) {
            Message message = new Message(discardedMessages + sentedMessages + 1, requestTimeMillis, 0L);
            LOGGER.warn(">>|  REFUSED MESSAGE = {}       SENTED MESSAGES = {} ({})       DELIVERED MESSAGES = {}       WAITING MESSAGES = {}       AVAILABLE QUEUES = {}", message, sentedMessages, (deliveredMessages.get() + discardedMessages + (sentedMessages -deliveredMessages.get()) + 1), deliveredMessages, getWaitingMessages(), getAvailableQueues());
            discardedMessages++;
        } else {
            Message message = new Message(sentedMessages +1, requestTimeMillis, 0L);
            new Thread(
                    sendMessage(queue, message))
                    .start();
            sentedMessages++;
            synchronized (allLostMessages) {
                allLostMessages.put(String.valueOf(message.getId()), String.valueOf(requestTimeMillis));
            }
        }
    }

    public void sendMessage(String queue) {
        long requestTimeMillis = System.currentTimeMillis();

        Message message = new Message(sentedMessages + 1, requestTimeMillis, 0L);
        new Thread(
                sendMessage(queue, message))
                .start();
        sentedMessages++;
        synchronized (allLostMessages) {
            allLostMessages.put(String.valueOf(message.getId()), String.valueOf(requestTimeMillis));
        }
    }

    private Runnable sendMessage(String queue, Message message) {
        return () -> {
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
                }
            } else {
                LOGGER.error("|<<   {}", response);
                lostMessages.incrementAndGet();
            }
        };
    }

    public void allLostMessages() {
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

    public int getWaitingMessages() {
        return sentedMessages -deliveredMessages.get();
    }

    public int getAvailableQueues() {
        int _availableQueues = concurrentConsumers-(sentedMessages -deliveredMessages.get());
        return  (0 < _availableQueues) ? _availableQueues : 0;
    }
}
