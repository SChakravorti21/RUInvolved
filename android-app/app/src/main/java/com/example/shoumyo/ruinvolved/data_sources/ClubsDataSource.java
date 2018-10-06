package com.example.shoumyo.ruinvolved.data_sources;

import android.content.Context;

import com.example.shoumyo.ruinvolved.data_sources.services.ClubService;
import com.example.shoumyo.ruinvolved.data_sources.services.StudentService;
import com.example.shoumyo.ruinvolved.models.Category;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class ClubsDataSource {

    private ClubService service;

    public ClubsDataSource(Context context) {
        this.service = ServiceFactory.getService(context, ClubService.class);
    }

    public Single<List<String>> getAllCategories() {
        return service.getAllCategories()
                .toObservable()
                .flatMapIterable(categories -> categories)
                .map(category -> category.categoryName)
                .toList();
    }

}
