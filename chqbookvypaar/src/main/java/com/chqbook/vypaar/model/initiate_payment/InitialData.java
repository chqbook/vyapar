package com.chqbook.vypaar.model.initiate_payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InitialData implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
