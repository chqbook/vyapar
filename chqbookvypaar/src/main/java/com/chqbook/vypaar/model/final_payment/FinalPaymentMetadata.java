package com.chqbook.vypaar.model.final_payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FinalPaymentMetadata implements Serializable {
    @SerializedName("promo_message")
    @Expose
    private String promoMessage;

    public String getPromoMessage() {
        return promoMessage;
    }

    public void setPromoMessage(String promoMessage) {
        this.promoMessage = promoMessage;
    }
}
