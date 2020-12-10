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

    private int sentMessage = 0;

    private int discardedMessage = 0;

    @Value("${jmsConfiguration.concurrentConsumers:0}")
    int concurrentConsumers;

    @Autowired
    private QueueService queueService;

    public int getSentMessage() {
        return sentMessage;
    }

    public boolean sendMessage(String queue) {
//        int waitingMessages = sentMessage-deliveredMessages.get();
//        int _availableQueues = concurrentConsumers-(sentMessage-deliveredMessages.get());
//        int availableQueues = (0 < _availableQueues) ? _availableQueues : 0;

        long requestTimeMillis = System.currentTimeMillis();
        if (concurrentConsumers <= getWaitingMessages()) {
            Message message = new Message(sentMessage+1+discardedMessage, requestTimeMillis, 0L);
            LOGGER.warn(">>|  REFUSED MESSAGE = {}       SENTED MESSAGES = {} ({})       DELIVERED MESSAGES = {}       WAITING MESSAGES = {}       AVAILABLE QUEUES = {}", message, sentMessage, (deliveredMessages.get() + discardedMessage + (sentMessage-deliveredMessages.get()) + 1), deliveredMessages, getWaitingMessages(), getAvailableQueues());
            discardedMessage++;
            return false;
        } else {
            Message message = new Message(sentMessage+1, requestTimeMillis, 0L);
            new Thread(
                    sendMessage(queue, message))
                    .start();
            sentMessage++;
            synchronized (allLostMessages) {
                allLostMessages.put(String.valueOf(message.getId()), String.valueOf(requestTimeMillis));
            }
            return true;
        }
    }

    private Runnable sendMessage(String queue, Message message) {
        return () -> {
//            final int concurrentConsumers = 20;
//            int waitingMessages = sentMessage-deliveredMessages.get();
//            int _availableQueues = concurrentConsumers-(sentMessage-deliveredMessages.get());
//            int availableQueues = (0 < _availableQueues) ? _availableQueues : 0;

            LOGGER.debug(">>|  SENT MESSAGE = {}       SENTED MESSAGES = {} ({})       DELIVERED MESSAGES = {}       WAITING MESSAGES = {}       AVAILABLE QUEUES = {}", message, sentMessage, (deliveredMessages.get() + discardedMessage + (sentMessage-deliveredMessages.get())), deliveredMessages, getWaitingMessages(), getAvailableQueues());

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
//        int _availableQueues = concurrentConsumers-(sentMessage-deliveredMessages.get());
//        int availableQueues = (0 < _availableQueues) ? _availableQueues : 0;
        LOGGER.info(">>>>>>>>>> SENTED MESSAGES = {} ({})       REFUSED MESSAGES = {}       DELIVERED MESSAGES = {};       WAITING MESSAGES = {}       LOST MESSAGES = {}       AVAILABLE QUEUES = {} <<<<<<<<<<", sentMessage, (deliveredMessages.get() + discardedMessage + (sentMessage-deliveredMessages.get())), discardedMessage, deliveredMessages, getWaitingMessages(), (sentMessage-deliveredMessages.get()), getAvailableQueues());

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
        return sentMessage-deliveredMessages.get();
    }

    public int getAvailableQueues() {
        int _availableQueues = concurrentConsumers-(sentMessage-deliveredMessages.get());
        return  (0 < _availableQueues) ? _availableQueues : 0;
    }
}
