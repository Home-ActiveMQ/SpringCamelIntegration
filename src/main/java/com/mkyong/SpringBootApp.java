package com.mkyong;

import com.mkyong.config.properties.ClientMessageProperties;
import com.mkyong.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class SpringBootApp extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LogManager.getLogger(SpringBootApp.class);

    private AtomicInteger deliveredMessages;

    private AtomicInteger lostMessages;

    private final String runCamelTest = "yes";

    private final String stopCamelTest = "yes";

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
        BufferedReader readComand = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Do you want run a Camel-Test in the console (yes/NO): ");
        boolean isRunCamelTest = runCamelTest.equals(readComand.readLine());
        if (isRunCamelTest) {
            deliveredMessages = new AtomicInteger();
            lostMessages = new AtomicInteger();
            for (int message = 1; message <= clientMessageProperties.getSentMessages(); message++) {
                new Thread(taskSendMessage(message)).start();
                synchronized (allLostMessages) {
                    allLostMessages.add("" + message);
                }
            }

            Thread.sleep(clientMessageProperties.getAllResponseDelay());
            LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>> SENT MESSAGES = {}       DELIVERED MESSAGES = {}       LOST MESSAGES = {} <<<<<<<<<<<<<<<<<<<<<<<<", clientMessageProperties.getSentMessages(), deliveredMessages, lostMessages);
            LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>> ALL LOST MESSAGES = {} <<<<<<<<<<<<<<<<<<<<<<<<", allLostMessages);
        }

        System.out.print("Do you want stop the Camel-Test (yes/NO): ");
        isRunCamelTest = stopCamelTest.equals(readComand.readLine());
        if (isRunCamelTest) System.exit(0);
    }

    private Runnable taskSendMessage(Object message) {
        return () -> {
                LOGGER.debug(" >>|  {}", message);
            String response = queueService.sendMessage("" + message);
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
