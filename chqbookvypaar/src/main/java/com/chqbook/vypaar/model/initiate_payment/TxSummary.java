package com.chqbook.vypaar.model.initiate_payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TxSummary implements Serializable {

    @SerializedName("tx_total_amount_title")
    @Expose
    private String txTotalAmountTitle;
    @SerializedName("tx_total_amount")
    @Expose
    private String txTotalAmount;

    public String getTxTotalAmountTitle() {
        return txTotalAmountTitle;
    }

    public void setTxTotalAmountTitle(String txTotalAmountTitle) {
        this.txTotalAmountTitle = txTotalAmountTitle;
    }

    public String getTxTotalAmount() {
        return txTotalAmount;
    }

    public void setTxTotalAmount(String txTotalAmount) {
        this.txTotalAmount = txTotalAmount;
    }

}
