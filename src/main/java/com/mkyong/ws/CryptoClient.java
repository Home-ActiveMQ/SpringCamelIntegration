package com.mkyong.ws;

import com.mkyong.dto.TokenLifecycleRequestDto;
import com.mkyong.dto.TokenLifecycleResponseDto;

/**
 * @see https://github.com/JobTest/FASTTACK/blob/dev-multithread/common-core/src/main/java/com/cts/fasttack/common/core/rest/client/RestClientImpl.java
 */

public abstract class CryptoClient {

    private RestCryptoClient restCryptoClient;

    public CryptoClient(RestCryptoClient restCryptoClient) {
        this.restCryptoClient = restCryptoClient;
    }

    public String encrypt(String path) {
        return restCryptoClient.toString() + " " + restCryptoClient.getForObject(path, String.class);
    }

    public TokenLifecycleResponseDto lifecycle(String path, TokenLifecycleRequestDto request) {
        return restCryptoClient.postForObject(path, request, TokenLifecycleResponseDto.class);
    }
}
