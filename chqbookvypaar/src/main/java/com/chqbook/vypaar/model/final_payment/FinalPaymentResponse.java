package com.chqbook.vypaar.model.final_payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FinalPaymentResponse implements Serializable {
    @SerializedName("metadata")
    @Expose
    private FinalPaymentMetadata metadata;
    @SerializedName("data")
    @Expose
    private FinalPaymentData data;

    public FinalPaymentMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(FinalPaymentMetadata metadata) {
        this.metadata = metadata;
    }

    public FinalPaymentData getData() {
        return data;
    }

    public void setData(FinalPaymentData data) {
        this.data = data;
    }

}
