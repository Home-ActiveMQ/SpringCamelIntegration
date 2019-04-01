package com.mkyong.config;

import com.mkyong.ws.RestCryptoClient;
import com.mkyong.ws.RestCryptoClientImpl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import com.mkyong.controller.interceptor.LoggingClientHttpRequestInterceptor;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;

/**
 * @see https://github.com/JobTest/FASTTACK/blob/dev-multithread/crypto/crypto-client/src/main/java/com/cts/fasttack/crypto/client/conf/CryptoClientConfiguration.java
 */

@Configuration
public class WebConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RestCryptoClient restCryptoClient() {
//        return new RestCryptoClientImpl("localhost", 8080, "SpringCamelIntegration-0.0.1-SNAPSHOT");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(cryptoSimpleHttpsClientRequestFactory());
        restTemplate.setInterceptors(Collections.singletonList(loggingClientHttpRequestInterceptor()));
        return new RestCryptoClientImpl("localhost", 8080, "SpringCamelIntegration-0.0.1-SNAPSHOT", restTemplate);
    }

    @Bean
    public ClientHttpRequestFactory cryptoSimpleHttpsClientRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        return new BufferingClientHttpRequestFactory(factory);
    }

    @Bean
    public ClientHttpRequestInterceptor loggingClientHttpRequestInterceptor() {
        return new LoggingClientHttpRequestInterceptor();
    }
}
