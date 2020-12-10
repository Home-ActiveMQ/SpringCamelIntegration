package com.mkyong;

import com.mkyong.config.properties.ClientMessageProperties;
import com.mkyong.data.Message;
import com.mkyong.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.PropertySource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.gson.Gson;

@EnableConfigurationProperties
@PropertySource(value = "classpath:jms-queue-test-application.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${jboss.home.dir}/standalone/configuration/jms-queue-test-application.properties", ignoreResourceNotFound = true)
@SpringBootApplication
public class SpringBootApp extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LogManager.getLogger(SpringBootApp.class);

    private AtomicInteger deliveredMessages = new AtomicInteger();

    private AtomicInteger lostMessages = new AtomicInteger();

    static Map<String, String> allLostMessages = new HashMap();

    private int sentMessage = 0;

    private int discardedMessage = 0;

    private final long speedSending = 20L;

    @Autowired
    private ClientMessageProperties clientMessageProperties;

    @Autowired
    private QueueService queueService;

    public static void main(String[] args) {
        //TODO:  disabled banner, don't want to see the spring logo
//        SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);

        SpringApplication.run(SpringBootApp.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException, IOException {
        System.out.print("Enter queue name: ");
        final String queue = new BufferedReader(new InputStreamReader(System.in)).readLine();

        if (StringUtils.isNotBlank(queue)) {




            while (sentMessage < clientMessageProperties.getSentMessages()) {
                boolean isSendMessage = sendMessage(queue);

//                if (isSendMessage) {
//                    LOGGER.error(">>>>>>>>>>>>>>>>>>>>>>>> SENT MESSAGES = {} ({});       REFUSED MESSAGES = {};       DELIVERED MESSAGES = {};       LOST MESSAGES = {}; <<<<<<<<<<<<<<<<<<<<<<<<", sentMessage, (deliveredMessages.get() + discardedMessage + (sentMessage-deliveredMessages.get())), discardedMessage, deliveredMessages, (sentMessage-deliveredMessages.get()));
//                    LOGGER.error(">>>>>>>>>>>>>>>>>>>>>>>> ALL LOST MESSAGES <<<<<<<<<<<<<<<<<<<<<<<<");
//                    for (Map.Entry<String, String> allLostMessage:  allLostMessages.entrySet()) {
//                        String strRequestTimeMilliss = allLostMessage.getValue();
//                        long requestTimeMillis = Long.valueOf(strRequestTimeMilliss);
//                        float timeToLive = (float) (System.currentTimeMillis() - requestTimeMillis) / 1000;
//                        allLostMessage.setValue(String.valueOf(timeToLive));
//                        LOGGER.error("{\"id\":{},\"time\":{}}", allLostMessage.getKey(), allLostMessage.getValue());
//
//                    }
//                }

                Thread.sleep(speedSending);
            }

            Thread.sleep(clientMessageProperties.getAllResponseDelay());

//            LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>> SENTED MESSAGES = {} ({});       REFUSED MESSAGES = {};       DELIVERED MESSAGES = {};       LOST MESSAGES = {}; <<<<<<<<<<<<<<<<<<<<<<<<", sentMessage, (deliveredMessages.get() + discardedMessage + (sentMessage-deliveredMessages.get())), discardedMessage, deliveredMessages, (sentMessage-deliveredMessages.get()));
//            LOGGER.error(">>>>>>>>>>>>>>>>>>>>>>>> ALL LOST MESSAGES <<<<<<<<<<<<<<<<<<<<<<<<");
//            for (Map.Entry<String, String> allLostMessage:  allLostMessages.entrySet()) {
//                String strRequestTimeMilliss = allLostMessage.getValue();
//                long requestTimeMillis = Long.valueOf(strRequestTimeMilliss);
//                float timeToLive = (float) (System.currentTimeMillis() - requestTimeMillis) / 1000;
//                allLostMessage.setValue(String.valueOf(timeToLive));
//                LOGGER.error("{\"id\":{},\"time\":{}}", allLostMessage.getKey(), allLostMessage.getValue());
//
//            }

            lostMessages();


        }
    }

    boolean sendMessage(String queue) {
        final int concurrentConsumers = 20;
        int waitingMessages = sentMessage-deliveredMessages.get();
        int _availableQueues = concurrentConsumers-(sentMessage-deliveredMessages.get());
        int availableQueues = (0 < _availableQueues) ? _availableQueues : 0;

        long requestTimeMillis = System.currentTimeMillis();
        if (concurrentConsumers <= waitingMessages) {
            Message message = new Message(sentMessage+1+discardedMessage, requestTimeMillis, 0L);
            LOGGER.warn(">>|  REFUSED MESSAGE = {}       SENTED MESSAGES = {} ({})       DELIVERED MESSAGES = {}       WAITING MESSAGES = {}       AVAILABLE QUEUES = {}", message, sentMessage, (deliveredMessages.get() + discardedMessage + (sentMessage-deliveredMessages.get()) + 1), deliveredMessages, waitingMessages, availableQueues);
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
            final int concurrentConsumers = 20;
            int waitingMessages = sentMessage-deliveredMessages.get();
            int _availableQueues = concurrentConsumers-(sentMessage-deliveredMessages.get());
            int availableQueues = (0 < _availableQueues) ? _availableQueues : 0;

            LOGGER.debug(">>|  SENT MESSAGE = {}       SENTED MESSAGES = {} ({})       DELIVERED MESSAGES = {}       WAITING MESSAGES = {}       AVAILABLE QUEUES = {}", message, sentMessage, (deliveredMessages.get() + discardedMessage + (sentMessage-deliveredMessages.get())), deliveredMessages, waitingMessages, availableQueues);

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

    private void lostMessages() {
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>> SENTED MESSAGES = {} ({});       REFUSED MESSAGES = {};       DELIVERED MESSAGES = {};       LOST MESSAGES = {}; <<<<<<<<<<<<<<<<<<<<<<<<", sentMessage, (deliveredMessages.get() + discardedMessage + (sentMessage-deliveredMessages.get())), discardedMessage, deliveredMessages, (sentMessage-deliveredMessages.get()));
        LOGGER.error(">>>>>>>>>>>>>>>>>>>>>>>> ALL LOST MESSAGES <<<<<<<<<<<<<<<<<<<<<<<<");
        for (Map.Entry<String, String> allLostMessage:  allLostMessages.entrySet()) {
            String strRequestTimeMilliss = allLostMessage.getValue();
            long requestTimeMillis = Long.valueOf(strRequestTimeMilliss);
            float timeToLive = (float) (System.currentTimeMillis() - requestTimeMillis) / 1000;
            allLostMessage.setValue(String.valueOf(timeToLive));
            LOGGER.error("{\"id\":{},\"time\":{}}", allLostMessage.getKey(), allLostMessage.getValue());

        }
    }
}
