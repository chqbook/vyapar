package com.chqbook.vypaar.model.initialise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InitilaiseResults implements Serializable {
    @SerializedName("logos")
    @Expose
    private Logos logos;
    @SerializedName("promo_data")
    @Expose
    private PromoData promoData;
    @SerializedName("buttons_metadata")
    @Expose
    private ButtonsMetadata buttonsMetadata;
    @SerializedName("timers_metadata")
    @Expose
    private TimersMetadata timersMetadata;

    public Logos getLogos() {
        return logos;
    }

    public void setLogos(Logos logos) {
        this.logos = logos;
    }

    public PromoData getPromoData() {
        return promoData;
    }

    public void setPromoData(PromoData promoData) {
        this.promoData = promoData;
    }

    public ButtonsMetadata getButtonsMetadata() {
        return buttonsMetadata;
    }

    public void setButtonsMetadata(ButtonsMetadata buttonsMetadata) {
        this.buttonsMetadata = buttonsMetadata;
    }

    public TimersMetadata getTimersMetadata() {
        return timersMetadata;
    }

    public void setTimersMetadata(TimersMetadata timersMetadata) {
        this.timersMetadata = timersMetadata;
    }
}
