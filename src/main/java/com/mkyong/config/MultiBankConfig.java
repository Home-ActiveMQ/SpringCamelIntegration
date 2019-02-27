package com.mkyong.config;

import com.mkyong.entity.Bank;
import com.mkyong.jms.processor.TestProcessor;
import com.mkyong.service.BankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

/**
 * @see https://stackoverflow.com/questions/41025627/how-can-i-name-a-service-with-multiple-names-in-spring
 */
@Configuration
public class MultiBankConfig {

    private static final Logger LOGGER = LogManager.getLogger(MultiBankConfig.class);

    @Autowired
    private BankService bankService;

    @PostConstruct
    public void init() {
        LOGGER.debug(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        for (Bank bank: bankService.findAll()) {
            LOGGER.debug(bank);
        }
        LOGGER.debug(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
    }

    @Bean(name = {"test1Processor", "test2Processor", "test3Processor"})
    public TestProcessor createTestProcessor(@Autowired TestProcessor testProcessor) {
        return testProcessor;
    }
}
