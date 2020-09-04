package com.chqbook.vypaar.network;

import com.chqbook.vypaar.model.final_payment.FinalPaymentResponse;
import com.chqbook.vypaar.model.initiate_payment.InitiatePaymentResponse;
import com.chqbook.vypaar.model.send_otp.SendOTPResponse;
import com.chqbook.vypaar.model.verify_otp.VerifyOTPResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

        @FormUrlEncoded
        @POST("cl/pg/tx/otp")
        Call<SendOTPResponse> sendOtp(
                @Field("accountNo") String accountNo,
                @Field("accountProvider") String provider
        );

        @FormUrlEncoded
        @POST("cl/pg/tx/login")
        Call<VerifyOTPResponse> verifyOtp(
                @Field("mobile") String mobile,
                @Field("otp") String otp
        );

        @FormUrlEncoded
        @POST("cl/pg/tx/initiate")
        Call<InitiatePaymentResponse> initiatePayment(
                @Field("amount") String amount,
                @Field("accountNo") String accountNo,
                @Field("userId") String userId,
                @Field("storeCode") String storeCode,
                @Field("partnerTxRef") String partnerTxRef
        );

    @FormUrlEncoded
    @POST("cl/pg/tx/approve")
    Call<FinalPaymentResponse> paymentSuccess(
            @Field("transactionId") String transactionId ,
            @Field("userId") String userId
    );
}
