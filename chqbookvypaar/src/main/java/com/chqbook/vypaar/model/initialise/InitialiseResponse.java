package com.chqbook.vypaar.model.initialise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InitialiseResponse implements Serializable {
    @SerializedName("metadata")
    @Expose
    private List<Object> metadata = null;
    @SerializedName("data")
    @Expose
    private InitialiseData data;

    public List<Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<Object> metadata) {
        this.metadata = metadata;
    }

    public InitialiseData getData() {
        return data;
    }

    public void setData(InitialiseData data) {
        this.data = data;
    }
}
