package com.chqbook.vypaar.model.final_payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FinalPaymentResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private FinalPaymentData data;

    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public FinalPaymentData getData() {
        return data;
    }

    public void setData(FinalPaymentData data) {
        this.data = data;
    }
}
