package com.mkyong.ws;

import org.springframework.web.client.RestTemplate;
import java.util.concurrent.ThreadLocalRandom;

public class RestCryptoClientImpl extends RestTemplate implements RestCryptoClient {

    private long serialVersionUID = ThreadLocalRandom.current().nextLong(100000000000L, 999999999999L);
    private String host;
    private String contextRoot;
    private int port;

    public RestCryptoClientImpl(String host, int port, String contextRoot) {
        this.host = host;
        this.port = port;
        this.contextRoot = contextRoot;
    }

    @Override
    public <T> T getForObject(String path, Class<T> responseType) {
        String url = getUrl() + "/" + path;
        return super.getForObject(url, responseType);
    }

    @Override
    public String toString() {
        return "{ \"restTemplate\" : \"" + serialVersionUID + "\" }";
    }

    private String getUrl() {
        return "http://" + host + ":" + port + "/" + contextRoot;
    }
}
