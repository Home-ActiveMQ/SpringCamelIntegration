package com.mkyong.ws;

import org.springframework.web.client.RestTemplate;
import java.util.concurrent.ThreadLocalRandom;

//public class RestCryptoClientImpl extends RestTemplate implements RestCryptoClient {
public class RestCryptoClientImpl implements RestCryptoClient {

    private long serialVersionUID = ThreadLocalRandom.current().nextLong(100000000000L, 999999999999L);
    private String tls;
    private String host;
    private String contextRoot;
    private int port;
    private RestTemplate restTemplate;

    public RestCryptoClientImpl(String host, int port, String contextRoot) {
        this.host = host;
        this.port = port;
        this.contextRoot = contextRoot;
    }

    public RestCryptoClientImpl(String tls, String host, int port, String contextRoot, RestTemplate restTemplate) {
        this.tls = tls;
        this.host = host;
        this.port = port;
        this.contextRoot = contextRoot;
        this.restTemplate = restTemplate;
    }

    @Override
    public <RESPONSE> RESPONSE getForObject(final String path, final Class<RESPONSE> responseType) {
        String url = getUrl() + "/" + path;
//        return super.getForObject(url, responseType);
        return restTemplate.getForObject(url, responseType);
    }

    @Override
    public <REQUEST, RESPONSE> RESPONSE postForObject(final String path, final REQUEST request, final Class<RESPONSE> responseType) {
        String url = getUrl() + "/" + path;
        return restTemplate.postForObject(url, request, responseType);
    }

    @Override
    public String toString() {
        return "{ \"restTemplate\" : \"" + serialVersionUID + "\" }";
    }

    private String getUrl() {
        return tls +  "://" + host + ":" + port + "/" + contextRoot;
    }
}
