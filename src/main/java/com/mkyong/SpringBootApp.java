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
import java.util.List;
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

    static List<String> allLostMessages = new ArrayList<>();

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
                new Thread(
                        taskSendMessage(
                                queue,
                                new Message(sentMessage, System.currentTimeMillis(), 0L))).start();
                synchronized (allLostMessages) {
                    allLostMessages.add(String.valueOf(sentMessage));
                }
            }
            Thread.sleep(clientMessageProperties.getAllResponseDelay());
        }

        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>> SENT MESSAGES = {};       DELIVERED MESSAGES = {};       LOST MESSAGES = {} ({}); <<<<<<<<<<<<<<<<<<<<<<<<", sentMessage, deliveredMessages, (sentMessage-deliveredMessages.get()), lostMessages);
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>> ALL LOST MESSAGES = {} <<<<<<<<<<<<<<<<<<<<<<<<", allLostMessages);
    }

    private Runnable taskSendMessage(String queue, Message message) {
        return () -> {
            LOGGER.debug(" >>|  {}", message);
            String response = queueService.sendMessage(queue, new Gson().toJson(message));
            if (response!=null) {
                Message messageResponse = new Gson().fromJson(response, Message.class);
                messageResponse.setResponseTimeMillis(System.currentTimeMillis());

                float timeSec = (float) (messageResponse.getResponseTimeMillis()-messageResponse.getRequestTimeMillis()) / 1000;
                LOGGER.debug("|<<   {} ... {}", messageResponse, timeSec);
                deliveredMessages.incrementAndGet();
                synchronized (allLostMessages) { allLostMessages.remove(String.valueOf(messageResponse.getId())); }
            } else {
                LOGGER.error("|<<   {}", response);
                lostMessages.incrementAndGet();
            }
        };
    }
}
