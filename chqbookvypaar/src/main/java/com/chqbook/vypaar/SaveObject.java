package com.chqbook.vypaar;

import android.content.Context;

public class SaveObject {
    private Context context;
    private String appKey;
    private String userId;
    private String storeId;
    private String partnerTxNo;
    private String accountNo;
    private String amount;

    private static final SaveObject ourInstance = new SaveObject();

    public static SaveObject getInstance() {
        return ourInstance;
    }

    private SaveObject() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPartnerTxNo() {
        return partnerTxNo;
    }

    public void setPartnerTxNo(String partnerTxNo) {
        this.partnerTxNo = partnerTxNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
