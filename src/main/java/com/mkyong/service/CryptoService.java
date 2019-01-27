package com.mkyong.service;

import com.mkyong.ws.CryptoClient;
import com.mkyong.ws.RestCryptoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @see https://github.com/JobTest/FASTTACK/blob/dev-multithread/crypto/crypto-client/src/main/java/com/cts/fasttack/crypto/client/rest/JmsCryptoRestClientImpl.java
 *      https://github.com/JobTest/FASTTACK/blob/dev-multithread/crypto/crypto-client/src/main/java/com/cts/fasttack/crypto/client/rest/AbstractCryptoRestClientImpl.java
 */

@Service
public class CryptoService extends CryptoClient {

    @Autowired
    public CryptoService(@Qualifier("restCryptoClient") RestCryptoClient restCryptoClient) {
        super(restCryptoClient);
    }

    @Override
    public String encrypt(String path) {
        return super.encrypt(path);
    }
}
