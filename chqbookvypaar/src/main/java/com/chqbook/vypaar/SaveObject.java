package com.chqbook.vypaar;

import android.content.Context;

public class SaveObject {
    private static final SaveObject ourInstance = new SaveObject();
    private Context context;
    private String userId;
    private String storeId;
    private String partnerTxNo;
    private String accountNo;
    private String amount;
    private String code;
    private String codeStatus;
    private String transactionId;
    private String mobile;
    private String apiKey;
    private ChqbookVypaarCallback callback;
    private String accountProvider;
    private boolean debug;

    private SaveObject() {
    }

    public static SaveObject getInstance() {
        return ourInstance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ChqbookVypaarCallback getCallback() {
        return callback;
    }

    public void setCallback(ChqbookVypaarCallback callback) {
        this.callback = callback;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAccountProvider() {
        return accountProvider;
    }

    public void setAccountProvider(String accountProvider) {
        this.accountProvider = accountProvider;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
