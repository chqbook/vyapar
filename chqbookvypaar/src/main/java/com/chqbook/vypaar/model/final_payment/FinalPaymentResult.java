package com.chqbook.vypaar.model.final_payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FinalPaymentResult implements Serializable {
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("amount")
    @Expose
    private int amount;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
