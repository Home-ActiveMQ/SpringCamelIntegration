package com.mkyong.config;

import com.mkyong.entity.Bank;
import com.mkyong.jms.processor.TestProcessor;
import com.mkyong.service.BankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * @see https://stackoverflow.com/questions/41025627/how-can-i-name-a-service-with-multiple-names-in-spring
 *      https://stackoverflow.com/questions/29743320/how-exactly-does-the-spring-beanpostprocessor-work
 * @see https://stackoverflow.com/questions/4540713/add-bean-programmatically-to-spring-web-app-context
 *      https://medium.com/@lofidewanto/creating-spring-bean-dynamically-in-the-runtime-d9e32c41d286
 *
 * https://www.dataart.ru/news/malen-kie-sekrety-spring
 * https://proselyte.net/tutorials/spring-tutorial-full-version/postbeanprocessor-interface
 */
@Configuration
@Component("multiBankConfig")
public class MultiBankConfig {

    private static final Logger LOGGER = LogManager.getLogger(MultiBankConfig.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BankService bankService;

//    @Bean(name = {"test1Processor", "test2Processor", "test3Processor"})
//    public TestProcessor createTestProcessor() {
//        return new TestProcessor();
//    }

    @PostConstruct
    public void init() {
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();

        if (0<bankService.count()) {
            for (Bank bank : bankService.findAll()) beanFactory.registerSingleton(bank.getUniqueUrl() + "Processor", new TestProcessor(bank.getTitle()));
        } else {
            beanFactory.registerSingleton("testProcessor", new TestProcessor("testProcessor"));
        }
    }
}
