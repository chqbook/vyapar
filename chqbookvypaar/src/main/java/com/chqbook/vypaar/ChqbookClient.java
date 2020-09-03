package com.chqbook.vypaar;

import android.content.Context;
import android.content.Intent;

import com.chqbook.vypaar.ui.OTPVerification;

import org.json.JSONException;
import org.json.JSONObject;

public class ChqbookClient {
    private ChqbookClient() {
    }

    private static class ChqbookClientHelper {
        private static final ChqbookClient INSTANCE = new ChqbookClient();
    }

    public static ChqbookClient getInstance() {
        return ChqbookClientHelper.INSTANCE;
    }

    public void init(Context context) {
        SaveObject.getInstance().setContext(context);
    }

    public void initiatePayment(JSONObject jsonObject) {
        try {
            Context context = SaveObject.getInstance().getContext();
            SaveObject.getInstance().setAmount(jsonObject.getString("amount"));
            SaveObject.getInstance().setStoreId(jsonObject.getString("store_code"));
            SaveObject.getInstance().setPartnerTxNo(jsonObject.getString("partner_tx_no"));
            SaveObject.getInstance().setAccountNo(jsonObject.getString("account_no"));
            Intent intent = new Intent(context, OTPVerification.class);
            context.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
