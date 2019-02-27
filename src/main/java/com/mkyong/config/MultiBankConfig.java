package com.mkyong.config;

import com.mkyong.jms.processor.TestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @see https://stackoverflow.com/questions/41025627/how-can-i-name-a-service-with-multiple-names-in-spring
 */
@Configuration
public class MultiBankConfig {

    @Bean(name = {"test1Processor", "test2Processor", "test3Processor"})
    public TestProcessor createTestProcessor(@Autowired TestProcessor testProcessor) {
        return testProcessor;
    }
}
