package com.mkyong.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("clientMessage")
public class ClientMessageProperties {

    private int sentMessages;

    private int responseDelay;

    private int allResponseDelay;

    public int getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(int sentMessages) {
        this.sentMessages = sentMessages;
    }

    public int getResponseDelay() {
        return responseDelay;
    }

    public void setResponseDelay(int responseDelay) {
        this.responseDelay = responseDelay;
    }

    public int getAllResponseDelay() {
        return allResponseDelay;
    }

    public void setAllResponseDelay(int allResponseDelay) {
        this.allResponseDelay = allResponseDelay;
    }
}
