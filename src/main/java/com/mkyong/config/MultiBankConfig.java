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
import javax.annotation.PostConstruct;

/**
 * @see https://stackoverflow.com/questions/41025627/how-can-i-name-a-service-with-multiple-names-in-spring
 * @see https://stackoverflow.com/questions/4540713/add-bean-programmatically-to-spring-web-app-context
 *
 * https://dbdiagram.io/d/5c6ef578f7c5bb70c72f15f6
 */
@Configuration
public class MultiBankConfig {

    private static final Logger LOGGER = LogManager.getLogger(MultiBankConfig.class);

    @Autowired
    private BankService bankService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();

//        beanFactory.registerSingleton("test1Processor", TestProcessor.class);
//        beanFactory.registerSingleton("test2Processor", TestProcessor.class);
//        beanFactory.registerSingleton("test3Processor", TestProcessor.class);
        for (Bank bank: bankService.findAll()) beanFactory.registerSingleton(bank.getUniqueUrl(), TestProcessor.class);
    }

//    @Bean(name = { "test1Processor", "test2Processor", "test3Processor" })
//    public TestProcessor createTestProcessor(@Autowired TestProcessor testProcessor) {
//        return testProcessor;
//    }
}
