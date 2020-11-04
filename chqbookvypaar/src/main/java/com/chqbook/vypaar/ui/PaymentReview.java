package com.chqbook.vypaar.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chqbook.vypaar.R;
import com.chqbook.vypaar.SaveObject;
import com.chqbook.vypaar.model.Error;
import com.chqbook.vypaar.model.final_payment.FinalPaymentResponse;
import com.chqbook.vypaar.model.initialise.InitialiseResponse;
import com.chqbook.vypaar.model.initiate_payment.InitiatePaymentResponse;
import com.chqbook.vypaar.model.initiate_payment.Result;
import com.chqbook.vypaar.network.RetrofitClient;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentReview extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button payNow;
    private TextView amount;
    private TextView amountTitle;
    private ImageView logo;
    private ProgressBar progress;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    InitiatePaymentResponse response;
    InitialiseResponse initialResponse;
    List<Result> data;
    boolean doubleBackToExitPressedOnce = false;
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_review);
        recyclerView = (RecyclerView) findViewById(R.id.paymentRecyclerView);
        payNow = (Button) findViewById(R.id.pay_now);
        amount = (TextView) findViewById(R.id.finalAmount);
        logo = (ImageView) findViewById(R.id.logoReview);
        amountTitle = (TextView) findViewById(R.id.finalAmountTitle);
        progress = (ProgressBar) findViewById(R.id.progressBar1);
        progress.setVisibility(View.GONE);
        payNow.setEnabled(true);
        intent = new Intent(SaveObject.getInstance().getContext(), PaymentConfirmation.class);
        bundle = new Bundle();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            response = (InitiatePaymentResponse) extras.getSerializable("data");
            initialResponse = (InitialiseResponse) extras.getSerializable("initial_data");
            bundle.putSerializable("initial_data", initialResponse);
            String url = (initialResponse.getData().getResults().getLogos().getPgLogo());
            Picasso.get().load(url).into(logo);
            amount.setText(response.getMetadata().getTxSummary().getTxTotalAmount());
            amountTitle.setText(response.getMetadata().getTxSummary().getTxTotalAmountTitle());
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            data = response.getData().getResults();
            mAdapter = new PaymentReviewAdapter((ArrayList<Result>) data, PaymentReview.this);
            recyclerView.setAdapter(mAdapter);
            if (initialResponse != null && initialResponse.getData().getResults().getButtonsMetadata().getReviewTxButton() != null)
                payNow.setText(initialResponse.getData().getResults().getButtonsMetadata().getReviewTxButton());
            else
                payNow.setText("PAY NOW");
        }
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                payNow.setEnabled(false);
                payNow.setText("");
                String id = "0";
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getTitle().equals("Transaction ID")) {
                        id = data.get(i).getValue();
                        Call<FinalPaymentResponse> call = RetrofitClient
                                .getInstance().getApi().paymentSuccess(SaveObject.getInstance().getApiKey(), id, SaveObject.getInstance().getUserId(),SaveObject.getInstance().getAccountProvider());

                        call.enqueue(new Callback<FinalPaymentResponse>() {
                            @Override
                            public void onResponse(Call<FinalPaymentResponse> call, Response<FinalPaymentResponse> response) {
                                intent.putExtra("code", response.code());
                                if (response.body() != null) {
                                    progress.setVisibility(View.GONE);
                                    FinalPaymentResponse paymentResponse = response.body();
                                    bundle.putSerializable("data", paymentResponse);
                                    intent.putExtras(bundle);
                                    PaymentReview.this.startActivity(intent);
                                } else {
                                    progress.setVisibility(View.GONE);
                                    try {
                                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                                        Error error1 = gson.fromJson(response.errorBody().string(), Error.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("error", error1);
                                        intent.putExtras(bundle);
                                        SaveObject.getInstance().getContext().startActivity(intent);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<FinalPaymentResponse> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                                payNow.setEnabled(true);
                                if (initialResponse != null && initialResponse.getData().getResults().getButtonsMetadata().getReviewTxButton() != null)
                                    payNow.setText(initialResponse.getData().getResults().getButtonsMetadata().getReviewTxButton());
                                else
                                    payNow.setText("PAY NOW");

                            }
                        });
                    }
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.GONE);
        payNow.setEnabled(true);
        if (initialResponse != null && initialResponse.getData().getResults().getButtonsMetadata().getReviewTxButton() != null)
            payNow.setText(initialResponse.getData().getResults().getButtonsMetadata().getReviewTxButton());
        else
            payNow.setText("PAY NOW");
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        new AlertDialog.Builder(PaymentReview.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Chqbook Vypaar")
                .setMessage("Are you sure you want to cancel the payment?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SaveObject.getInstance().getContext(), OTPVerification.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        SaveObject.getInstance().setCodeStatus("User Pressed Back");
                        SaveObject.getInstance().setCode("1");
                        intent.putExtra("data", "failed");
                        PaymentReview.this.startActivity(intent);

                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
