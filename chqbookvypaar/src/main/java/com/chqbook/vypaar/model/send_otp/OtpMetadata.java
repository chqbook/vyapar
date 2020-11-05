package com.chqbook.vypaar.model.send_otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpMetadata {
    @SerializedName("resend_time")
    @Expose
    private Integer resendTime;

    public Integer getResendTime() {
        return resendTime;
    }

    public void setResendTime(Integer resendTime) {
        this.resendTime = resendTime;
    }
}
