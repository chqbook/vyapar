package com.chqbook.vypaar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chqbook.vypaar.R;
import com.chqbook.vypaar.SaveObject;
import com.chqbook.vypaar.model.Error;
import com.chqbook.vypaar.model.final_payment.FinalPaymentResponse;
import com.chqbook.vypaar.model.initialise.InitialiseResponse;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentConfirmation extends AppCompatActivity {
    TextView subheading;
    TextView redirecting;
    TextView successful;
    TextView promotionalText;
    ImageView image;
    ImageView promotionalLogo;
    Button home;
    int code;
    String codeStatus;
    private InitialiseResponse initialResponse;
    private String transactionID;
    boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);
        subheading = (TextView) findViewById(R.id.sub_heading);
        redirecting = (TextView) findViewById(R.id.redirecting);
        successful = (TextView) findViewById(R.id.successful);
        promotionalText = (TextView) findViewById(R.id.promotionalText);
        home = (Button) findViewById(R.id.home);
        image = (ImageView) findViewById(R.id.successImage);
        promotionalLogo = (ImageView) findViewById(R.id.promotionalLogo);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            FinalPaymentResponse response =
                    (FinalPaymentResponse) extras.getSerializable("data");
            if (response != null)
                transactionID = response.getData().getResults().getTransactionId();
            SaveObject.getInstance().setTransactionId(transactionID);
            initialResponse = (InitialiseResponse) extras.getSerializable("initial_data");
            String url1 = (initialResponse.getData().getResults().getLogos().getChqbookLogo());
            Picasso.get().load(url1).into(promotionalLogo);
            if (initialResponse.getData().getResults().getPromoData().getTitle() != null)
                promotionalText.setText(initialResponse.getData().getResults().getPromoData().getTitle());
            code = extras.getInt("code");
            SaveObject.getInstance().setCode(String.valueOf(code));
            Error error =
                    (Error) extras.getSerializable("error");
            if (error != null) {
                String url = (initialResponse.getData().getResults().getLogos().getFailureLogo());
                Picasso.get().load(url).into(image);
                subheading.setText(error.getErrors().get(0));
                successful.setText("Payment Failed");
                codeStatus = error.getErrorCode();
                SaveObject.getInstance().setCodeStatus(codeStatus);
                isSuccess = false;
                startCountDown(initialResponse.getData().getResults().getTimersMetadata().getFailureRedirect());
            } else {
                String url = (initialResponse.getData().getResults().getLogos().getSuccessLogo());
                Picasso.get().load(url).into(image);
                subheading.setText(response.getData().getMessage());
                successful.setText("Payment Successful");
                isSuccess = true;
                startCountDown(initialResponse.getData().getResults().getTimersMetadata().getSuccessRedirect());
            }

        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaveObject.getInstance().getContext(), OTPVerification.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                if (isSuccess)
                    intent.putExtra("data", "end");
                else
                    intent.putExtra("data", "failed");
                SaveObject.getInstance().getContext().startActivity(intent);
            }
        });
    }

    public void startCountDown(int time) {
        new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                redirecting.setText("Redirecting in " + new SimpleDateFormat("ss").format(new Date(millisUntilFinished)) + " seconds");
            }

            public void onFinish() {
                Intent intent = new Intent(PaymentConfirmation.this, OTPVerification.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                if (isSuccess)
                    intent.putExtra("data", "end");
                else
                    intent.putExtra("data", "failed");
                PaymentConfirmation.this.startActivity(intent);
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PaymentConfirmation.this, OTPVerification.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isSuccess)
            intent.putExtra("data", "end");
        else
            intent.putExtra("data", "failed");
        SaveObject.getInstance().getContext().startActivity(intent);
    }

}
