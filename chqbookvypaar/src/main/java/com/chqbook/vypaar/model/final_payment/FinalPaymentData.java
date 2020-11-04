package com.chqbook.vypaar.model.final_payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FinalPaymentData implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("results")
    @Expose
    private FinalPaymentResult results;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FinalPaymentResult getResults() {
        return results;
    }

    public void setResults(FinalPaymentResult results) {
        this.results = results;
    }

}
