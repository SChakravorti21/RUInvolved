package com.example.shoumyo.ruinvolved.data_sources.services;

import com.example.shoumyo.ruinvolved.models.Category;
import com.example.shoumyo.ruinvolved.models.Club;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClubService {

    @GET("/clubs/")
    Single<List<Club>> getClubsForCategories(@Query("categories") String categories);

    @GET("/clubs/categories")
    Single<List<Category>> getAllCategories();

}
