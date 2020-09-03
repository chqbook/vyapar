package com.chqbook.vypaar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chqbook.vypaar.model.Error;
import com.chqbook.vypaar.model.final_payment.FinalPaymentResponse;
import com.chqbook.vypaar.OnCallBack;
import com.chqbook.vypaar.R;
import com.chqbook.vypaar.SaveObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentConfirmation extends AppCompatActivity {
    TextView subheading;
    TextView redirecting;
    TextView successful;
    ImageView image;
    Button home;

    public OnCallBack mCallBack = (OnCallBack) SaveObject.getInstance().getContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);
        subheading = (TextView) findViewById(R.id.sub_heading);
        redirecting = (TextView) findViewById(R.id.redirecting);
        successful = (TextView) findViewById(R.id.successful);
        home = (Button) findViewById(R.id.home);
        image = (ImageView) findViewById(R.id.successImage);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            FinalPaymentResponse response =
                    (FinalPaymentResponse) extras.getSerializable("data");
            Error error =
                    (Error) extras.getSerializable("error");
            if (error != null) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.failed));
                subheading.setText(error.getErrors().get(0));
                successful.setText("Payment Failed");
            } else {
                image.setImageDrawable(getResources().getDrawable(R.drawable.success));
                subheading.setText(response.getData().getMessage());
                successful.setText("Payment Successful");
            }
            startCountDown();
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaveObject.getInstance().getContext(), OTPVerification.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data", "end");
                SaveObject.getInstance().getContext().startActivity(intent);
            }
        });
    }

    public void startCountDown() {
        new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                redirecting.setText("Redirecting in " + new SimpleDateFormat("ss").format(new Date(millisUntilFinished)) + " seconds");
            }

            public void onFinish() {
                Intent intent = new Intent(SaveObject.getInstance().getContext(), OTPVerification.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data", "end");
                SaveObject.getInstance().getContext().startActivity(intent);
            }
        }.start();

    }
}
