package com.chqbook.vypaar.model.initialise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Logos implements Serializable {

    @SerializedName("pg_logo")
    @Expose
    private String pgLogo;
    @SerializedName("chqbook_logo")
    @Expose
    private String chqbookLogo;
    @SerializedName("success_logo")
    @Expose
    private String successLogo;
    @SerializedName("failure_logo")
    @Expose
    private String failureLogo;

    public String getPgLogo() {
        return pgLogo;
    }

    public void setPgLogo(String pgLogo) {
        this.pgLogo = pgLogo;
    }

    public String getChqbookLogo() {
        return chqbookLogo;
    }

    public void setChqbookLogo(String chqbookLogo) {
        this.chqbookLogo = chqbookLogo;
    }

    public String getSuccessLogo() {
        return successLogo;
    }

    public void setSuccessLogo(String successLogo) {
        this.successLogo = successLogo;
    }

    public String getFailureLogo() {
        return failureLogo;
    }

    public void setFailureLogo(String failureLogo) {
        this.failureLogo = failureLogo;
    }

}
