package com.example.chqbookvypaar;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chqbook.vypaar.ChqbookClient;
import com.chqbook.vypaar.OnCallBack;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnCallBack {

    private ChqbookClient chqbook;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        chqbook = ChqbookClient.getInstance();
        chqbook.init(this);
        JSONObject js = new JSONObject();
        try {
            js.put("amount", 10000);
            js.put("app_id", "1234");
            js.put("account_no", "7208507225");
            js.put("store_code", "10");
            js.put("partner_tx_no", "454455dd54dd54");
            chqbook.initiatePayment(js);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPress() {
        Toast.makeText(context, getResources().getString(R.string.app_name), Toast.LENGTH_LONG).show();
    }
}
