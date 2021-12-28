package com.chqbook.vypaar;

import android.content.Context;
import android.content.Intent;

import com.chqbook.vypaar.ui.OTPVerification;

import org.json.JSONException;
import org.json.JSONObject;

import static com.chqbook.vypaar.ChqbookVypaarKeys.ACCOUNT_NO;
import static com.chqbook.vypaar.ChqbookVypaarKeys.ACCOUNT_PROVIDER;
import static com.chqbook.vypaar.ChqbookVypaarKeys.AMOUNT;
import static com.chqbook.vypaar.ChqbookVypaarKeys.API_KEY;
import static com.chqbook.vypaar.ChqbookVypaarKeys.DEBUG;
import static com.chqbook.vypaar.ChqbookVypaarKeys.MOBILE_NO;
import static com.chqbook.vypaar.ChqbookVypaarKeys.PARTNER_TX_NO;
import static com.chqbook.vypaar.ChqbookVypaarKeys.STORE_CODE;

public class ChqbookVypaarClient {
    private SaveObject saveObject;

    private ChqbookVypaarClient() {
        saveObject = SaveObject.getInstance();
    }

    private static class ChqbookClientHelper {
        private static final ChqbookVypaarClient INSTANCE = new ChqbookVypaarClient();
    }

    public static ChqbookVypaarClient getInstance() {
        return ChqbookClientHelper.INSTANCE;
    }

    public void init(Context context) {
        if (context != null)
            saveObject.setContext(context.getApplicationContext());
    }

    public void initiatePayment(JSONObject jsonObject, ChqbookVypaarCallback callback) throws NullPointerException {
        try {
            Context context = saveObject.getContext();
            saveObject.setCallback(callback);
            validate(jsonObject);
            if (jsonObject.has(AMOUNT))
                saveObject.setAmount(jsonObject.getString(AMOUNT));
            if (jsonObject.has(STORE_CODE))
                saveObject.setStoreId(jsonObject.getString(STORE_CODE));
            if (jsonObject.has(PARTNER_TX_NO))
                saveObject.setPartnerTxNo(jsonObject.getString(PARTNER_TX_NO));
            if (jsonObject.has(ACCOUNT_NO))
                saveObject.setAccountNo(jsonObject.getString(ACCOUNT_NO));
            if (jsonObject.has(API_KEY))
                saveObject.setApiKey(jsonObject.getString(API_KEY));
            if (jsonObject.has(MOBILE_NO))
                saveObject.setMobile(jsonObject.getString(MOBILE_NO));
            if (jsonObject.has(ACCOUNT_PROVIDER))
                saveObject.setAccountProvider(jsonObject.getString(ACCOUNT_PROVIDER));
            if (jsonObject.has(DEBUG))
                saveObject.setDebug(jsonObject.getBoolean(DEBUG));
            Intent intent = new Intent(context, OTPVerification.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void validate(JSONObject jsonObject) throws NullPointerException, JSONException {
        if (saveObject.getContext() == null) {
            throw (new NullPointerException("Init should be called first"));
        } else if (saveObject.getCallback() == null) {
            throw (new NullPointerException("Callback should not be null"));
        } else if (!jsonObject.has(AMOUNT)) {
            throw (new NullPointerException("Amount should not be null"));
        } else if (!jsonObject.has(PARTNER_TX_NO)) {
            throw (new NullPointerException("PARTNER_TX_NO should not be null"));
        } else if (!jsonObject.has(ACCOUNT_NO)) {
            throw (new NullPointerException("ACCOUNT_NO should not be null"));
        } else if (!jsonObject.has(ACCOUNT_PROVIDER)) {
            throw (new NullPointerException("ACCOUNT_PROVIDER should not be null"));
        }

    }
}



