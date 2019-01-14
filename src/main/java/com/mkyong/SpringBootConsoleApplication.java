package com.mkyong;

import com.mkyong.config.properties.ClientMessageProperties;
import com.mkyong.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    private static final Logger LOGGER = LogManager.getLogger(SpringBootConsoleApplication.class);

    private AtomicInteger deliveredMessages;

    private AtomicInteger lostMessages;

    static List<String> allLostMessages = new ArrayList<>();

    @Autowired
    private ClientMessageProperties clientMessageProperties;

    @Autowired
    private MessageService messageService;

    public static void main(String[] args) {

        //TODO:  disabled banner, don't want to see the spring logo
//        SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);

        SpringApplication.run(SpringBootConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        deliveredMessages = new AtomicInteger();
        lostMessages = new AtomicInteger();
        for (int message = 1; message <= clientMessageProperties.getSentMessages(); message++) {
            new Thread(taskSendMessage(message)).start();
            synchronized (allLostMessages) { allLostMessages.add("" + message); }
        }

        Thread.sleep(clientMessageProperties.getAllResponseDelay());
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>> SENT MESSAGES = {}       DELIVERED MESSAGES = {}       LOST MESSAGES = {} <<<<<<<<<<<<<<<<<<<<<<<<", clientMessageProperties.getSentMessages(), deliveredMessages, lostMessages);
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>> ALL LOST MESSAGES = {} <<<<<<<<<<<<<<<<<<<<<<<<", allLostMessages);
    }

    private Runnable taskSendMessage(Object message) {
        return () -> {
                LOGGER.debug(" >>|  {}", message);
            String response = messageService.sendMessage("" + message);
            if (response!=null) {
                LOGGER.debug("|<<   {}", response);
                deliveredMessages.incrementAndGet();
                synchronized (allLostMessages) { allLostMessages.remove(response); }
            } else {
                LOGGER.error("|<<   {}", response);
                lostMessages.incrementAndGet();
            }
        };
    }
}
