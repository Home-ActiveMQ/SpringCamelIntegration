package com.mkyong.controller.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Spring ClientHttpRequestInterceptor with RestTemplate
 *
 * @see https://objectpartners.com/2018/03/01/log-your-resttemplate-request-and-response-without-destroying-the-body
 *      https://howtodoinjava.com/spring-restful/clienthttprequestinterceptor
 */

public class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LogManager.getLogger(LoggingClientHttpRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("============================== Request Message =========================================");
            LOGGER.debug("Address     : {}", request.getURI());
            LOGGER.debug("Http-Method : {}", request.getMethod());
            LOGGER.debug("Headers     : {}", request.getHeaders());
            LOGGER.debug("Payload     : {}", new String(body, "UTF-8"));
            LOGGER.debug("========================================================================================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("============================== Response Message ========================================");
            LOGGER.debug("Response-Code : {}", response.getStatusCode());
            LOGGER.debug("Headers       : {}", response.getHeaders());
            LOGGER.debug("Messages      : {}", response.getStatusText());
            LOGGER.debug("Payload       : {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            LOGGER.debug("========================================================================================");
        }
    }
}
