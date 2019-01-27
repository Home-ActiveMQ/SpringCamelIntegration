package com.mkyong.ws;

public interface RestCryptoClient {

    <T> T getForObject(String path, Class<T> responseType);

}
