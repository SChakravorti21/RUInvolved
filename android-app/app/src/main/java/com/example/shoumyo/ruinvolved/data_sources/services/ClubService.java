package com.example.shoumyo.ruinvolved.data_sources.services;

import com.example.shoumyo.ruinvolved.models.Category;
import com.example.shoumyo.ruinvolved.models.Club;
import com.example.shoumyo.ruinvolved.models.Geolocation;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClubService {

    @GET("/club/")
    Single<Club> getClubForId(@Query("id") String clubId);

    @GET("/clubs/")
    Single<List<Club>> getClubsForCategories(@Query("categories") String categories);

    @GET("/clubs/categories")
    Single<List<Category>> getAllCategories();

    @GET("/clubs")
    Single<List<Club>> getClubsForIds(@Query("favorite_clubs") String queryString);

    @GET("/club/get-location")
    Single<Geolocation> getClubLocation(@Query("id") int id);

}
