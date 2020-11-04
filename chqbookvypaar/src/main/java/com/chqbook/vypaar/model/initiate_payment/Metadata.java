package com.chqbook.vypaar.model.initiate_payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Metadata implements Serializable {

    @SerializedName("tx_summary")
    @Expose
    private TxSummary txSummary;

    public String getConfirmationButtonText() {
        return confirmationButtonText;
    }

    public void setConfirmationButtonText(String confirmationButtonText) {
        this.confirmationButtonText = confirmationButtonText;
    }

    @SerializedName("confirmation_button_text")
    @Expose
    private String confirmationButtonText;

    public TxSummary getTxSummary() {
        return txSummary;
    }

    public void setTxSummary(TxSummary txSummary) {
        this.txSummary = txSummary;
    }

}
