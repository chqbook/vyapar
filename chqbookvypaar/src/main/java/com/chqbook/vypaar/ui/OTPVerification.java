package com.chqbook.vypaar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chqbook.vypaar.ChqbookVypaarKeys;
import com.chqbook.vypaar.R;
import com.chqbook.vypaar.SaveObject;
import com.chqbook.vypaar.model.Error;
import com.chqbook.vypaar.model.initialise.InitialiseResponse;
import com.chqbook.vypaar.model.initiate_payment.InitiatePaymentResponse;
import com.chqbook.vypaar.model.send_otp.SendOTPResponse;
import com.chqbook.vypaar.model.verify_otp.VerifyOTPResponse;
import com.chqbook.vypaar.network.RetrofitClient;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerification extends AppCompatActivity {
    private TextView resend;
    private EditText otp;
    private TextView otpText;
    private TextView cancel;
    private Button proceed;
    private ImageView logo;
    private ProgressBar progress;
    private ProgressBar progressInitial;
    SaveObject saveObject = SaveObject.getInstance();
    SendOTPResponse loginResponse;
    private InitialiseResponse initializeResponse;
    private int timer = 30;

    public OTPVerification() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);
        setUpView();
    }

    private void setUpView() {
        resend = (TextView) findViewById(R.id.resendText);
        otpText = (TextView) findViewById(R.id.OTP);
        cancel = (TextView) findViewById(R.id.cancel);
        otp = (EditText) findViewById(R.id.otp_text);
        proceed = (Button) findViewById(R.id.proceed);
        progress = (ProgressBar) findViewById(R.id.progressBar1);
        progressInitial = (ProgressBar) findViewById(R.id.progress);
        logo = (ImageView) findViewById(R.id.logo);
        progress.setVisibility(View.GONE);
        progressInitial.setVisibility(View.VISIBLE);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callVerifyOtp();
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callOtpApi();
                startCountDown(timer);
                proceed.setEnabled(true);
            }
        });
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("data") != null && intent.getStringExtra("data").equals("end")) {
            onCancelPressed(true);
        } else if (intent != null && intent.getStringExtra("data") != null && intent.getStringExtra("data").equals("failed")) {
            onCancelPressed(false);
        } else {
            calInitialData();
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveObject.getInstance().getCallback().onFailed("1", "User pressed back");
                finish();
            }
        });

    }

    public void onCancelPressed(boolean isSuccess) {
        if (SaveObject.getInstance() != null) {
            if (isSuccess) {
                SaveObject.getInstance().getCallback().onSuccess(SaveObject.getInstance().getCode(), SaveObject.getInstance().getTransactionId());
                finish();
            } else {
                SaveObject.getInstance().getCallback().onFailed(SaveObject.getInstance().getCode(), SaveObject.getInstance().getCodeStatus());
                finish();
            }
        }

    }


    private void callVerifyOtp() {
        proceed.setEnabled(false);
        proceed.setText("");
        progress.setVisibility(View.VISIBLE);

        Call<VerifyOTPResponse> call = RetrofitClient
                .getInstance().getApi().verifyOtp(SaveObject.getInstance().getApiKey(), SaveObject.getInstance().getAccountNo(), otp.getText().toString(),SaveObject.getInstance().getAccountProvider());

        call.enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Call<VerifyOTPResponse> call, Response<VerifyOTPResponse> response) {
                VerifyOTPResponse loginResponse = response.body();
                if (response.body() != null) {
                    Log.d("response", loginResponse.toString());
                    //   setCookieDetails(response.headers().values("set-cookie"));
                    progress.setVisibility(View.GONE);
                    proceed.setText(R.string.verify_button);
                    proceed.setEnabled(true);
                    saveObject.setUserId(response.body().getData().getResults().getId().toString());
                    callInitiatePayment();
                } else {
                    try {
                        progress.setVisibility(View.GONE);
                        proceed.setText(R.string.verify_button);
                        proceed.setEnabled(true);
                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                        Error error1 = gson.fromJson(response.errorBody().string(), Error.class);
                        Toast.makeText(OTPVerification.this, error1.getErrors().get(0), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                proceed.setText(R.string.verify_button);
                proceed.setEnabled(true);

            }
        });
    }

    public void callOtpApi() {
        progress.setVisibility(View.VISIBLE);
        proceed.setText("");
        Call<SendOTPResponse> call = RetrofitClient
                .getInstance().getApi().sendOtp(SaveObject.getInstance().getApiKey(), saveObject.getAccountNo(), SaveObject.getInstance().getAccountProvider(), SaveObject.getInstance().getMobile());

        call.enqueue(new Callback<SendOTPResponse>() {
            @Override
            public void onResponse(Call<SendOTPResponse> call, Response<SendOTPResponse> response) {
                loginResponse = response.body();
                if (response.body() != null) {
                    response.headers().values("lvl_session");
                    otpText.setText(loginResponse.getData().getMessage());
                    progress.setVisibility(View.GONE);
                    proceed.setText(R.string.verify_button);
                } else {
                    try {
                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                        Error error1 = gson.fromJson(response.errorBody().string(), Error.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("error", error1);
                        Intent intent = new Intent(OTPVerification.this, PaymentConfirmation.class);
                        intent.putExtra("code", response.code());
                        bundle.putSerializable("initial_data", initializeResponse);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtras(bundle);
                        OTPVerification.this.startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SendOTPResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                proceed.setText(R.string.verify_button);

            }
        });
    }

    public void callInitiatePayment() {
        proceed.setText("");
        proceed.setEnabled(false);
        progress.setVisibility(View.VISIBLE);
        Call<InitiatePaymentResponse> call = RetrofitClient
                .getInstance().getApi().initiatePayment(SaveObject.getInstance().getApiKey(), String.valueOf(saveObject.getAmount()), saveObject.getAccountNo(), saveObject.getUserId(), saveObject.getStoreId(), saveObject.getPartnerTxNo(),SaveObject.getInstance().getAccountProvider());

        call.enqueue(new Callback<InitiatePaymentResponse>() {
            @Override
            public void onResponse(Call<InitiatePaymentResponse> call, Response<InitiatePaymentResponse> response) {
                progress.setVisibility(View.GONE);
                proceed.setText(R.string.verify_button);
                proceed.setEnabled(true);
                if (response.body() != null) {
                    InitiatePaymentResponse paymentResponse = response.body();
                    Intent intent = new Intent(SaveObject.getInstance().getContext(), PaymentReview.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", paymentResponse);
                    bundle.putSerializable("initial_data", initializeResponse);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);
                    saveObject.getContext().startActivity(intent);
                } else {
                    try {
                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                        Error error1 = gson.fromJson(response.errorBody().string(), Error.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("error", error1);
                        Intent intent = new Intent(SaveObject.getInstance().getContext(), PaymentConfirmation.class);
                        intent.putExtra("code", response.code());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        bundle.putSerializable("initial_data", initializeResponse);
                        intent.putExtras(bundle);
                        SaveObject.getInstance().getContext().startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<InitiatePaymentResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                proceed.setText(R.string.verify_button);
                proceed.setEnabled(true);

            }
        });
    }

    public void calInitialData() {
        progress.setVisibility(View.VISIBLE);
        proceed.setText("");
        Call<InitialiseResponse> call = RetrofitClient
                .getInstance().getApi().getInitialData(SaveObject.getInstance().getApiKey(),SaveObject.getInstance().getAccountProvider());

        call.enqueue(new Callback<InitialiseResponse>() {
            @Override
            public void onResponse(Call<InitialiseResponse> call, Response<InitialiseResponse> response) {
                progressInitial.setVisibility(View.GONE);
                if (response.body() != null) {
                    initializeResponse = response.body();
                    callOtpApi();
                    if (response.body() != null && response.body().getData().getResults().getTimersMetadata() != null) {
                        timer = response.body().getData().getResults().getTimersMetadata().getResendOtp();
                        startCountDown(response.body().getData().getResults().getTimersMetadata().getResendOtp());
                    } else
                        startCountDown(30);
                    proceed.setText(response.body().getData().getResults().getButtonsMetadata().getOtpButton());
                    String url = (response.body().getData().getResults().getLogos().getPgLogo());
                    Picasso.get().load(url).into(logo);
                } else {
                    try {
                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                        Error error1 = gson.fromJson(response.errorBody().string(), Error.class);
                        SaveObject.getInstance().getCallback().onFailed(String.valueOf(response.code()), error1.getErrors().get(0));
                        finish();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<InitialiseResponse> call, Throwable t) {
                progressInitial.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                proceed.setText(R.string.verify_button);
            }
        });
    }


    public void startCountDown(int time) {
        new CountDownTimer(time * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                resend.setText(" Resend in " + new SimpleDateFormat("ss").format(new Date(millisUntilFinished)) + " seconds");
                resend.setTextColor(getResources().getColor(R.color.colorTextLight));

            }

            public void onFinish() {
                resend.setText(" Resend");
                resend.setTextColor(getResources().getColor(R.color.colorTextDark));
                proceed.setEnabled(true);
            }
        }.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.GONE);
        proceed.setEnabled(true);
        proceed.setText(R.string.verify_button);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SaveObject.getInstance().getCallback().onFailed("1", "User pressed back");
        fileList();
    }

}

