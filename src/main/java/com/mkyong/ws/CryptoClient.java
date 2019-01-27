package com.mkyong.ws;

/**
 * @see https://github.com/JobTest/FASTTACK/blob/dev-multithread/common-core/src/main/java/com/cts/fasttack/common/core/rest/client/RestClientImpl.java
 */

public class CryptoClient {

    private RestCryptoClient restCryptoClient;

    public CryptoClient(RestCryptoClient restCryptoClient) {
        this.restCryptoClient = restCryptoClient;
    }

    public String encrypt(String path) {
        return restCryptoClient.toString() + " " + restCryptoClient.getForObject(path, String.class);
    }
}
