package com.chqbook.vypaar.model.initialise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PromoData implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("promo_logo")
    @Expose
    private String promoLogo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPromoLogo() {
        return promoLogo;
    }

    public void setPromoLogo(String promoLogo) {
        this.promoLogo = promoLogo;
    }
}
