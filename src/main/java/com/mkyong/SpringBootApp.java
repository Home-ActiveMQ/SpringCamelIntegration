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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.gson.Gson;

@EnableConfigurationProperties
@PropertySource(value = "classpath:jms-queue-test-application.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${jboss.home.dir}/standalone/configuration/jms-queue-test-application.properties", ignoreResourceNotFound = true)
@SpringBootApplication
public class SpringBootApp extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LogManager.getLogger(SpringBootApp.class);

    private AtomicInteger deliveredMessages;

    private AtomicInteger lostMessages;

    static Map<String, String> allLostMessages = new HashMap();

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
        deliveredMessages = new AtomicInteger();
        lostMessages = new AtomicInteger();
        int sentMessage = 0;
        System.out.print("Enter queue name: ");
        final String queue = new BufferedReader(new InputStreamReader(System.in)).readLine();

        if (StringUtils.isNotBlank(queue)) {
            while (sentMessage < clientMessageProperties.getSentMessages()) {
                sentMessage++;
                long requestTimeMillis = System.currentTimeMillis();
                Message message = new Message(sentMessage, requestTimeMillis, 0L);
                new Thread(
                        taskSendMessage(queue, message))
                        .start();
                synchronized (allLostMessages) {
                    allLostMessages.put(String.valueOf(message.getId()), String.valueOf(requestTimeMillis));
                }
                int waitingMessages = sentMessage-deliveredMessages.get();
                final int concurrentConsumers = 20;
                final long timeToLive = 1500L;
                if (concurrentConsumers <= waitingMessages) {
                    LOGGER.warn("SENT MESSAGES = {};       WAITING MESSAGES = {};       DELIVERED MESSAGES = {};", sentMessage, waitingMessages, deliveredMessages);
                    Thread.sleep(timeToLive);
                }
            }
//            LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>  <<<<<<<<<<<<<<<<<<<<<<<<"); // TODO
            Thread.sleep(clientMessageProperties.getAllResponseDelay());
        }

        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>> SENT MESSAGES = {};       DELIVERED MESSAGES = {};       LOST MESSAGES = {} ({}); <<<<<<<<<<<<<<<<<<<<<<<<", sentMessage, deliveredMessages, (sentMessage-deliveredMessages.get()), lostMessages);
//        LOGGER.error(">>>>>>>>>>>>>>>>>>>>>>>> ALL LOST MESSAGES = {} <<<<<<<<<<<<<<<<<<<<<<<<", allLostMessages);
        LOGGER.error(">>>>>>>>>>>>>>>>>>>>>>>> ALL LOST MESSAGES <<<<<<<<<<<<<<<<<<<<<<<<");
        for (Map.Entry<String, String> allLostMessage:  allLostMessages.entrySet()) {
            String strRequestTimeMilliss = allLostMessage.getValue();
            long requestTimeMillis = Long.valueOf(strRequestTimeMilliss);
            float timeToLive = (float) (System.currentTimeMillis() - requestTimeMillis) / 1000;
            allLostMessage.setValue(String.valueOf(timeToLive));
            LOGGER.error("{\"id\":{},\"time\":{}}", allLostMessage.getKey(), allLostMessage.getValue());

        }
    }

    private Runnable taskSendMessage(String queue, Message message) {
        return () -> {
            LOGGER.debug(" >>|  {}", message);
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
}
