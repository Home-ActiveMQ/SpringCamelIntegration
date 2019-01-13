package com.mkyong;

import com.mkyong.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    private static final Logger LOGGER = LogManager.getLogger(SpringBootConsoleApplication.class);

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
    public void run(String... args) {
        for (int message = 1; message <= 20; message++) new Thread(taskSendMessage(message)).start();
    }

    private Runnable taskSendMessage(Object message) {
        return () -> {
                LOGGER.debug(" >>|  {}", message);
            String response = messageService.sendMessage("" + message + "");
            if (response!=null) LOGGER.debug("|<<   {}", response);
            else LOGGER.error("|<<   {}", response);
        };
    }
}