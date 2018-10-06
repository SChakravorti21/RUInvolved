package com.example.shoumyo.ruinvolved.data_sources;

import android.content.Context;

import com.example.shoumyo.ruinvolved.Config;
import com.example.shoumyo.ruinvolved.data_sources.services.StudentService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Completable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentDataSource {

    private StudentService service;

    public StudentDataSource(Context context) {
        this.service = ServiceFactory.getService(context, StudentService.class);
    }
}
