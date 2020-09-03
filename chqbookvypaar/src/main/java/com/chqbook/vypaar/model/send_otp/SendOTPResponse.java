package com.chqbook.vypaar.model.send_otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendOTPResponse {
    @SerializedName("data")
    @Expose
    private SendOtpData data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public SendOtpData getData() {
        return data;
    }

    public void setData(SendOtpData data) {
        this.data = data;
    }
}
