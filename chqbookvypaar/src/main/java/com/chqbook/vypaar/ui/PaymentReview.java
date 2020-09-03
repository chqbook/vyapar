package com.chqbook.vypaar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chqbook.vypaar.model.Error;
import com.chqbook.vypaar.model.final_payment.FinalPaymentResponse;
import com.chqbook.vypaar.model.initiate_payment.InitiatePaymentResponse;
import com.chqbook.vypaar.R;
import com.chqbook.vypaar.network.RetrofitClient;
import com.chqbook.vypaar.SaveObject;
import com.chqbook.vypaar.model.initiate_payment.Result;
import com.google.gson.Gson;

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
    private ProgressBar progress;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Result> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_review);
        recyclerView = (RecyclerView) findViewById(R.id.paymentRecyclerView);
        payNow = (Button) findViewById(R.id.pay_now);
        amount = (TextView) findViewById(R.id.finalAmount);
        amountTitle = (TextView) findViewById(R.id.finalAmountTitle);
        progress = (ProgressBar) findViewById(R.id.progressBar1);
        progress.setVisibility(View.GONE);
        payNow.setEnabled(true);
        payNow.setText("PAY NOW");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            InitiatePaymentResponse response =
                    (InitiatePaymentResponse) extras.getSerializable("data");
            amount.setText(response.getMetadata().getTxSummary().getTxTotalAmount());
            amountTitle.setText(response.getMetadata().getTxSummary().getTxTotalAmountTitle());
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            data = response.getData().getResults();
            mAdapter = new PaymentReviewAdapter((ArrayList<Result>) data);
            recyclerView.setAdapter(mAdapter);
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
                                .getInstance().getApi().paymentSuccess(id,  SaveObject.getInstance().getUserId());

                        call.enqueue(new Callback<FinalPaymentResponse>() {
                            @Override
                            public void onResponse(Call<FinalPaymentResponse> call, Response<FinalPaymentResponse> response) {
                                if (response.body() != null) {
                                    progress.setVisibility(View.GONE);
                                    FinalPaymentResponse paymentResponse = response.body();
                                    Intent intent = new Intent(SaveObject.getInstance().getContext(), PaymentConfirmation.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("data", paymentResponse);
                                    intent.putExtras(bundle);
                                    SaveObject.getInstance().getContext().startActivity(intent);
                                } else {
                                    progress.setVisibility(View.GONE);
                                    try {
                                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                                        Error error1 = gson.fromJson(response.errorBody().string(), Error.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("error", error1);
                                        Intent intent = new Intent(SaveObject.getInstance().getContext(), PaymentConfirmation.class);
                                        intent.putExtras(bundle);
                                        SaveObject.getInstance().getContext().startActivity(intent);
                                        Toast.makeText(SaveObject.getInstance().getContext(), error1.getErrors().get(0), Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<FinalPaymentResponse> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                                payNow.setEnabled(true);
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
        payNow.setText("PAY NOW");
    }
}
