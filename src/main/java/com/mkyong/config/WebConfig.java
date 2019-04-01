package com.mkyong.config;

import com.mkyong.controller.factory.CryptoHttpsClientRequestFactory;
import com.mkyong.controller.interceptor.VisaAuthorizationInterceptor;
import com.mkyong.ws.RestCryptoClient;
import com.mkyong.ws.RestCryptoClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * @see https://github.com/JobTest/FASTTACK/blob/dev-multithread/crypto/crypto-client/src/main/java/com/cts/fasttack/crypto/client/conf/CryptoClientConfiguration.java
 *
 * @see http://supaprapat.com/archives/217
 */

@Configuration
public class WebConfig {

    public static final String tls = "http";

    public static final String host = "localhost";

    public static final int port = 8080;

    public static final String contextRoot = "SpringCamelIntegration-0.0.1-SNAPSHOT";


    @Autowired
    private CryptoHttpsClientRequestFactory httpsClientRequestFactory;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RestCryptoClient restCryptoClient() {
//        return new RestCryptoClientImpl("localhost", 8080, "SpringCamelIntegration-0.0.1-SNAPSHOT");

        RestTemplate restTemplate = new RestTemplate();

//        restTemplate.setRequestFactory(cryptoSimpleHttpClientRequestFactory());
        restTemplate.setRequestFactory(cryptoSimpleHttpsClientRequestFactory());
//        restTemplate.setInterceptors(Collections.singletonList(loggingClientHttpRequestInterceptor()));
        restTemplate.setInterceptors(interceptors());
        return new RestCryptoClientImpl(tls, host, port, contextRoot, restTemplate);
    }

    @Bean
    public ClientHttpRequestFactory cryptoSimpleHttpClientRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        return new BufferingClientHttpRequestFactory(factory);
    }

    @Bean
    public ClientHttpRequestFactory cryptoSimpleHttpsClientRequestFactory() {
        int defaultMaxPerRoute = 10;          // 10
        int maxTotal = 10;                    // 10
        int connectionTimeoutInMills = 10000; // 10 seconds
        int readTimeoutInMills = 10000;       // 10 seconds

        SSLConnectionSocketFactory sslSocketFactory = createSSLConnectionSocketFactory();

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setMaxTotal(maxTotal);

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setSSLSocketFactory(sslSocketFactory)
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(connectionTimeoutInMills).setSocketTimeout(readTimeoutInMills).build())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new BufferingClientHttpRequestFactory(requestFactory);
    }

    private SSLConnectionSocketFactory createSSLConnectionSocketFactory() {
        return new SSLConnectionSocketFactory(httpsClientRequestFactory.getSocketFactory(),
                null, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    }

    @Bean
    public List<ClientHttpRequestInterceptor> interceptors() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(visaAuthorizationInterceptor());
        interceptors.add(loggingClientHttpRequestInterceptors());
        return interceptors;
    }

    @Bean
    public ClientHttpRequestInterceptor loggingClientHttpRequestInterceptors() {
        return new LoggingClientHttpRequestInterceptor();
    }

    @Bean
    public ClientHttpRequestInterceptor visaAuthorizationInterceptor() {
        return new VisaAuthorizationInterceptor();
    }
}
