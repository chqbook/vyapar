package com.chqbook.vypaar.network;

import com.chqbook.vypaar.SaveObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final boolean isDebug= SaveObject.getInstance() != null && SaveObject.getInstance().isDebug();
    private static final String BASE_URL = isDebug?"https://uat.chqbook.com/api/":"https://www.chqbook.com/api/";
    private static RetrofitClient mInstance;
    private final Retrofit retrofit;


    private RetrofitClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
       // OkHttpClient.Builder builder = new OkHttpClient.Builder();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new AddCookieInterceptor())
                .addInterceptor(new ReceivedCookieInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}

