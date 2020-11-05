package com.chqbook.vypaar.model.initialise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ButtonsMetadata implements Serializable {
    @SerializedName("otp_button")
    @Expose
    private String otpButton;
    @SerializedName("review_tx_button")
    @Expose
    private String reviewTxButton;
    @SerializedName("redirect_button")
    @Expose
    private String redirectButton;

    public String getOtpButton() {
        return otpButton;
    }

    public void setOtpButton(String otpButton) {
        this.otpButton = otpButton;
    }

    public String getReviewTxButton() {
        return reviewTxButton;
    }

    public void setReviewTxButton(String reviewTxButton) {
        this.reviewTxButton = reviewTxButton;
    }

    public String getRedirectButton() {
        return redirectButton;
    }

    public void setRedirectButton(String redirectButton) {
        this.redirectButton = redirectButton;
    }
}
