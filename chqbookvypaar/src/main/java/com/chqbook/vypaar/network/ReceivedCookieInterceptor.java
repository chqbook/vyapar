package com.chqbook.vypaar.network;

import com.chqbook.vypaar.CookieData;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            CookieData.getInstance().setCookie(cookies);
           // Methods.setCookies(MyApplication.getAppContext(), cookies);
        }
        return originalResponse;
    }
}
