package com.example.shoumyo.ruinvolved.data_sources.services;

import com.example.shoumyo.ruinvolved.models.Category;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ClubService {

    @GET("/clubs/categories")
    Single<List<Category>> getAllCategories();

}
