package com.mkyong.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

@Component
@ConfigurationProperties("clientMessage")
public class ClientMessageProperties {

    private int sentMessages;

    private int responseDelay;

    private int allResponseDelay;

    @Value("${authenticate.provider.ldap.user-base-cn:}")
    private String userBaseCn;

    @Value("${authenticate.provider.ldap.role-dn:}")
    private String roleDn;

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

//    public List<String> getUserBaseCn() {
//        String[] arr = userBaseCn.split(";");
//        return Arrays.asList(arr);
//    }
    public String[] getUserBaseCn() {
        return getUserBaseCn(";");
    }

    public String[] getUserBaseCn(String spliterator) {
        String userBaseCn = this.userBaseCn.replaceAll(" ", "");
        return StringUtils.isNotBlank(userBaseCn) ? userBaseCn.split(spliterator) : new String[]{};
    }

//    public List<String> getRoleDn() {
//        String[] arr = roleCn.split(";");
//        return Arrays.asList(arr);
//    }
    public String[] getRoleDn() {
        return getRoleDn(";");
    }

    public String[] getRoleDn(String spliterator) {
        String roleCn = this.roleDn.replaceAll(" ", "");
        return StringUtils.isNotBlank(roleCn) ? roleCn.split(spliterator) : new String[]{};
    }
}
