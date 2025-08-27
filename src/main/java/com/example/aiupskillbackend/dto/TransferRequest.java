package com.example.aiupskillbackend.dto;

import java.math.BigDecimal;

public class TransferRequest {
    private String fromUserId;
    private String toUserId;
    private BigDecimal amount;

    public TransferRequest() {
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
