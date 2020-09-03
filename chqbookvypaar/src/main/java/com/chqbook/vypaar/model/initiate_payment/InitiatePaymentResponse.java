package com.chqbook.vypaar.model.initiate_payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InitiatePaymentResponse implements Serializable {
    @SerializedName("metdata")
    @Expose
    private Metadata Metadata;
    @SerializedName("data")
    @Expose
    private InitialData data;


    public Metadata getMetadata() {
        return Metadata;
    }

    public void setMetadata(Metadata Metadata) {
        this.Metadata = Metadata;
    }

    public InitialData getData() {
        return data;
    }

    public void setData(InitialData data) {
        this.data = data;
    }

}

