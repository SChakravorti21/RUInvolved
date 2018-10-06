package com.example.shoumyo.ruinvolved.data_sources;

import android.content.Context;

import com.example.shoumyo.ruinvolved.utils.SharedPrefsUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private WeakReference<Context> context;

    public HeaderInterceptor(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(this.context.get() == null)
            return chain.proceed(chain.request());

        Request original = chain.request();

        // String authToken = SharedPrefsUtils.getAuthToken(this.context.get());
        Request request = original.newBuilder()
                // .addHeader("Authorization", authToken)
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
