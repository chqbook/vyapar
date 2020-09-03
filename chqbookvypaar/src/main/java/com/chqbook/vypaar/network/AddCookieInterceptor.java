package com.chqbook.vypaar.network;

import com.chqbook.vypaar.CookieData;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if(CookieData.getInstance().getCl_pg_txs()!=null && CookieData.getInstance().getLvl_session()!=null ){
        builder.addHeader("cl_pg_txs", CookieData.getInstance().getCl_pg_txs());
        builder.addHeader("lvl_session", CookieData.getInstance().getLvl_session());}
        // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        return chain.proceed(builder.build());
    }
}