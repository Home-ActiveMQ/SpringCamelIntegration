package com.mkyong.config;

import com.mkyong.ws.RestCryptoClient;
import com.mkyong.ws.RestCryptoClientImpl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * @see https://github.com/JobTest/FASTTACK/blob/dev-multithread/crypto/crypto-client/src/main/java/com/cts/fasttack/crypto/client/conf/CryptoClientConfiguration.java
 */

@Configuration
public class WebConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RestCryptoClient restCryptoClient() {
        return new RestCryptoClientImpl("localhost", 8080, "SpringCamelIntegration-0.0.1-SNAPSHOT");
    }
}
