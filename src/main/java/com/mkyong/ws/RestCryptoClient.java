package com.mkyong.ws;

public interface RestCryptoClient {

    <RESPONSE> RESPONSE getForObject(String path, Class<RESPONSE> responseType);

    <REQUEST, RESPONSE> RESPONSE postForObject(String path, REQUEST request, Class<RESPONSE> responseType);

}
