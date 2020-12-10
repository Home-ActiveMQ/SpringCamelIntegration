package com.mkyong;

import com.mkyong.config.properties.ClientMessageProperties;
import com.mkyong.service.Test1QueueService;
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
import java.util.concurrent.ExecutionException;

@EnableConfigurationProperties
@PropertySource(value = "classpath:jms-queue-test-application.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${jboss.home.dir}/standalone/configuration/jms-queue-test-application.properties", ignoreResourceNotFound = true)
@SpringBootApplication
public class SpringBootApp extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LogManager.getLogger(SpringBootApp.class);

    @Autowired
    private ClientMessageProperties clientMessageProperties;

    @Autowired
    private Test1QueueService test1QueueService;

    public static void main(String[] args) {
        //TODO:  disabled banner, don't want to see the spring logo
//        SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);

        SpringApplication.run(SpringBootApp.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException, IOException, ExecutionException {
        final long speedSending = 20L;

        System.out.print("Enter queue name: ");
        final String queue = new BufferedReader(new InputStreamReader(System.in)).readLine();

        if (StringUtils.isNotBlank(queue)) {
//            while (test1QueueService.getSentedMessages() < clientMessageProperties.getSentMessages()) {
////                test1QueueService.sendMessage(queue);
//                test1QueueService.countSendMessage(queue);
//                Thread.sleep(speedSending);
//            }
//
//            Thread.sleep(clientMessageProperties.getAllResponseDelay());
//            test1QueueService.allLostMessages();


//            test1QueueService.countSendMessage(queue);
////            test1QueueService.countSendMessage(queue).get();
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//
//            test1QueueService.getAllLostMessages();
//
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//            test1QueueService.countSendMessage(queue);
//            Thread.sleep(speedSending);
//
//            test1QueueService.getAllLostMessages();

            while (test1QueueService.getSentedMessages() < clientMessageProperties.getSentMessages()) {
                test1QueueService.countSendMessage(queue);
                Thread.sleep(speedSending);
            }
            test1QueueService.getAllLostMessages();
        }
    }
}
