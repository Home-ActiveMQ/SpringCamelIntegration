package com.mkyong.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenLifecycleRequestDto {

    private String operationReasonCode;

    private String operatorID;

    private String operationReason;

    private String operationType;

    public String getOperationReasonCode() {
        return operationReasonCode;
    }

    public void setOperationReasonCode(String operationReasonCode) {
        this.operationReasonCode = operationReasonCode;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public String getOperationReason() {
        return operationReason;
    }

    public void setOperationReason(String operationReason) {
        this.operationReason = operationReason;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
