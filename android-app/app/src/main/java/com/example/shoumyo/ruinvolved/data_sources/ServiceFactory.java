package com.example.shoumyo.ruinvolved.data_sources;

import android.content.Context;

import com.example.shoumyo.ruinvolved.Config;
import com.example.shoumyo.ruinvolved.R;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class ServiceFactory {

    public static <Service> Service getService(Context context, Class<Service> serviceClass) {
        HeaderInterceptor interceptor = new HeaderInterceptor(context);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        // The Retrofit instance allows us to construct our own services
        // that will make network requests
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient);

        return retrofitBuilder.build().create(serviceClass);
    }

}
