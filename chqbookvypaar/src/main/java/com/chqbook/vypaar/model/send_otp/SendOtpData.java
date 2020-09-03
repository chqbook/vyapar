package com.chqbook.vypaar.model.send_otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtpData {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("results")
    @Expose
    private Boolean results;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getResults() {
        return results;
    }

    public void setResults(Boolean results) {
        this.results = results;
    }

}
