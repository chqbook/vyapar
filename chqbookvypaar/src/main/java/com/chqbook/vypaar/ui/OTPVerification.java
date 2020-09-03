package com.chqbook.vypaar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chqbook.vypaar.CookieData;
import com.chqbook.vypaar.model.Error;
import com.chqbook.vypaar.model.initiate_payment.InitiatePaymentResponse;
import com.chqbook.vypaar.OnCallBack;
import com.chqbook.vypaar.R;
import com.chqbook.vypaar.network.RetrofitClient;
import com.chqbook.vypaar.SaveObject;
import com.chqbook.vypaar.model.send_otp.SendOTPResponse;
import com.chqbook.vypaar.model.verify_otp.VerifyOTPResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerification extends AppCompatActivity {
    private TextView resend;
    private EditText otp;
    private TextView otpText;
    private TextView cancel;
    private Button proceed;
    private ProgressBar progress;
    SaveObject saveObject=SaveObject.getInstance();

    OnCallBack mCallBack = (OnCallBack) saveObject.getContext();

    public OTPVerification() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);
        setUpView();
        callOtpApi();
    }

    private void setUpView() {
        resend = (TextView) findViewById(R.id.resendText);
        otpText = (TextView) findViewById(R.id.OTP);
        cancel = (TextView) findViewById(R.id.cancel);
        otp = (EditText) findViewById(R.id.otp_text);
        proceed = (Button) findViewById(R.id.proceed);
        progress = (ProgressBar) findViewById(R.id.progressBar1);
        progress.setVisibility(View.GONE);
        startCountDown();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callverifyOtp();
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountDown();
                callOtpApi();
                proceed.setEnabled(false);
            }
        });
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("data") != null && intent.getStringExtra("data").equals("end")) {
            onCancelPressed();
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelPressed();
            }
        });

    }

    public void onCancelPressed() {
        mCallBack.onBackPress();
        finish();
    }

    private void callverifyOtp() {
        proceed.setEnabled(false);
        proceed.setText("");
        progress.setVisibility(View.VISIBLE);


        Call<VerifyOTPResponse> call = RetrofitClient
                .getInstance().getApi().verifyOtp("7208507225", otp.getText().toString());

        call.enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Call<VerifyOTPResponse> call, Response<VerifyOTPResponse> response) {
                VerifyOTPResponse loginResponse = response.body();
                if (response.body() != null) {
                    Log.d("response", loginResponse.toString());
                    setCookieDetails(response.headers().values("set-cookie"));
                    progress.setVisibility(View.GONE);
                    proceed.setText("VERIFY AND PROCEED");
                    proceed.setEnabled(true);
                    saveObject.setUserId(response.body().getData().getResults().getId().toString());
                    callInitiatePayment();
                } else {
                    try {
                        progress.setVisibility(View.GONE);
                        proceed.setText("VERIFY AND PROCEED");
                        proceed.setEnabled(true);
                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                        Error error1 = gson.fromJson(response.errorBody().string(), Error.class);
                        Toast.makeText(saveObject.getContext(), error1.getErrors().get(0), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                proceed.setText("VERIFY AND PROCEED");
                proceed.setEnabled(true);

            }
        });
    }

    public void callOtpApi() {
        progress.setVisibility(View.VISIBLE);
        proceed.setText("");
        Call<SendOTPResponse> call = RetrofitClient
                .getInstance().getApi().sendOtp(saveObject.getAccountNo(), "metro");

        call.enqueue(new Callback<SendOTPResponse>() {
            @Override
            public void onResponse(Call<SendOTPResponse> call, Response<SendOTPResponse> response) {
                SendOTPResponse loginResponse = response.body();
                if (response.body() != null) {
                    response.headers().values("lvl_session");
                    otpText.setText(loginResponse.getData().getMessage());
                    progress.setVisibility(View.GONE);
                    proceed.setText("VERIFY AND PROCEED");
                } else {
                    try {
                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                        Error error1 = gson.fromJson(response.errorBody().string(), Error.class);
                        Toast.makeText(saveObject.getContext(), error1.getErrors().get(0), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SendOTPResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                proceed.setText("VERIFY AND PROCEED");

            }
        });
    }

    public void callInitiatePayment() {
        proceed.setText("");
        proceed.setEnabled(false);
        progress.setVisibility(View.VISIBLE);
        Call<InitiatePaymentResponse> call = RetrofitClient
                .getInstance().getApi().initiatePayment(String.valueOf(saveObject.getAmount()), saveObject.getAccountNo(), saveObject.getUserId(), saveObject.getStoreId(), saveObject.getPartnerTxNo());

        call.enqueue(new Callback<InitiatePaymentResponse>() {
            @Override
            public void onResponse(Call<InitiatePaymentResponse> call, Response<InitiatePaymentResponse> response) {
                progress.setVisibility(View.GONE);
                proceed.setText("VERIFY AND PROCEED");
                proceed.setEnabled(true);
                if (response.body() != null) {
                    InitiatePaymentResponse paymentResponse = response.body();
                    Intent intent = new Intent(saveObject.getContext(), PaymentReview.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", paymentResponse);
                    intent.putExtras(bundle);
                    saveObject.getContext().startActivity(intent);
                } else {
                    try {
                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                        Error error1 = gson.fromJson(response.errorBody().string(), Error.class);
                        Toast.makeText(saveObject.getContext(), error1.getErrors().get(0), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<InitiatePaymentResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                proceed.setText("VERIFY AND PROCEED");
                proceed.setEnabled(true);

            }
        });
    }

    public void setCookieDetails(List<String> cookies) {
        HashMap<String, String> cookiesMap = new HashMap<>();
        //  ArrayList<String> cookieTokens = new ArrayList<>();
        if (cookies != null) {
            if (!cookies.isEmpty()) {
                for (String s : cookies) {
                    String[] split = s.split("=", 2);
                    cookiesMap.put(split[0], split[1]);
                }
            }
        } else {
            return;
        }

        CookieData.getInstance().setCl_pg_txs(cookiesMap.get("lvl_session"));
        CookieData.getInstance().setLvl_session(cookiesMap.get("cl-pg-txs"));
        CookieData.getInstance().setCookie(cookies);
    }

    public void startCountDown() {
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                resend.setText("Resend in " + new SimpleDateFormat("ss").format(new Date(millisUntilFinished)) + " seconds");
                resend.setTextColor(getResources().getColor(R.color.black_50));

            }

            public void onFinish() {
                resend.setText("Resend");
                resend.setTextColor(getResources().getColor(R.color.black));
                proceed.setEnabled(true);
            }
        }.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.GONE);
        proceed.setEnabled(true);
        proceed.setText("VERIFY AND PROCEED");
    }

}


