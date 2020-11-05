package com.chqbook.vypaar.model.initialise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimersMetadata implements Serializable {
    @SerializedName("resend_otp")
    @Expose
    private int resendOtp;
    @SerializedName("success_redirect")
    @Expose
    private int successRedirect;
    @SerializedName("failure_redirect")
    @Expose
    private int failureRedirect;

    public int getResendOtp() {
        return resendOtp;
    }

    public void setResendOtp(int resendOtp) {
        this.resendOtp = resendOtp;
    }

    public int getSuccessRedirect() {
        return successRedirect;
    }

    public void setSuccessRedirect(int successRedirect) {
        this.successRedirect = successRedirect;
    }

    public int getFailureRedirect() {
        return failureRedirect;
    }

    public void setFailureRedirect(int failureRedirect) {
        this.failureRedirect = failureRedirect;
    }
}
