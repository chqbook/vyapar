package com.example.chqbookvypaar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chqbook.vypaar.ChqbookVypaarCallback;
import com.chqbook.vypaar.ChqbookVypaarClient;

import org.json.JSONException;
import org.json.JSONObject;

import static com.chqbook.vypaar.ChqbookVypaarKeys.ACCOUNT_NO;
import static com.chqbook.vypaar.ChqbookVypaarKeys.ACCOUNT_PROVIDER;
import static com.chqbook.vypaar.ChqbookVypaarKeys.AMOUNT;
import static com.chqbook.vypaar.ChqbookVypaarKeys.API_KEY;
import static com.chqbook.vypaar.ChqbookVypaarKeys.MOBILE_NO;
import static com.chqbook.vypaar.ChqbookVypaarKeys.PARTNER_TX_NO;
import static com.chqbook.vypaar.ChqbookVypaarKeys.STORE_CODE;

public class MainActivity extends AppCompatActivity {

    private ChqbookVypaarClient chqbookVypaar;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chqbookVypaar = ChqbookVypaarClient.getInstance();
        chqbookVypaar.init(this);
        JSONObject js = new JSONObject();
        try {
            js.put(AMOUNT, 10000);
            js.put(ACCOUNT_NO, "7748962888");
            js.put(API_KEY, "f1f5rfe2-c6a6-79ea-66d0-07162bc115783");
            js.put(MOBILE_NO, "7748962888");
            js.put(STORE_CODE, "10");
            js.put(PARTNER_TX_NO, "454455dd54dd54");
            js.put(ACCOUNT_PROVIDER, "SHOP_KIRANA");
            chqbookVypaar.initiatePayment(js, new ChqbookVypaarCallback() {
                @Override
                public void onSuccess(String code, String transactionId) {
                    Toast.makeText(MainActivity.this, code + "    " + transactionId, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailed(String code, String error) {
                    Toast.makeText(MainActivity.this, code + "    " + error, Toast.LENGTH_LONG).show();

                }
            });

        } catch (JSONException | NullPointerException e) {
            Log.e("exception", e.getMessage());
        }
        setContentView(R.layout.activity_main);
    }
}
