package com.chqbook.vypaar.model.initialise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InitialiseData implements Serializable {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("results")
    @Expose
    private InitilaiseResults results;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InitilaiseResults getResults() {
        return results;
    }

    public void setResults(InitilaiseResults results) {
        this.results = results;
    }
}
