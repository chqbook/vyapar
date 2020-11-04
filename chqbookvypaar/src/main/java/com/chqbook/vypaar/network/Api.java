package com.chqbook.vypaar.network;

import com.chqbook.vypaar.CookieData;
import com.chqbook.vypaar.model.final_payment.FinalPaymentResponse;
import com.chqbook.vypaar.model.initialise.InitialiseResponse;
import com.chqbook.vypaar.model.initiate_payment.InitiatePaymentResponse;
import com.chqbook.vypaar.model.send_otp.SendOTPResponse;
import com.chqbook.vypaar.model.verify_otp.VerifyOTPResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

        @FormUrlEncoded
        @POST("cl/pg/tx/otp")
        Call<SendOTPResponse> sendOtp(
                @Header("api-key") String apiKey,
                @Field("accountNo") String accountNo,
                @Field("accountProvider") String provider,
                @Field("mobile") String mobile
        );

        @FormUrlEncoded
        @POST("cl/pg/tx/login")
        Call<VerifyOTPResponse> verifyOtp(
                @Header("api-key") String apiKey,
                @Field("mobile") String mobile,
                @Field("otp") String otp,
                @Field("accountProvider") String accountProvider
        );

        @FormUrlEncoded
        @POST("cl/pg/tx/initiate")
        Call<InitiatePaymentResponse> initiatePayment(
                @Header("api-key") String apiKey,
                @Field("amount") String amount,
                @Field("accountNo") String accountNo,
                @Field("userId") String userId,
                @Field("storeCode") String storeCode,
                @Field("partnerTxRef") String partnerTxRef,
                @Field("accountProvider") String accountProvider
        );

    @FormUrlEncoded
    @POST("cl/pg/tx/approve")
    Call<FinalPaymentResponse> paymentSuccess(
            @Header("api-key") String apiKey,
            @Field("transactionId") String transactionId ,
            @Field("userId") String userId,
            @Field("accountProvider") String accountProvider
    );
    @GET("cl/pg/init")
    Call<InitialiseResponse> getInitialData(
            @Header("api-key") String apiKey,
            @Header("accountProvider") String accountProvider
    );
}
