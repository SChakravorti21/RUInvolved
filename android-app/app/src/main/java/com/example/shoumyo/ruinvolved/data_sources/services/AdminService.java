package com.example.shoumyo.ruinvolved.data_sources.services;

import com.example.shoumyo.ruinvolved.models.Geolocation;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AdminService {

    @POST("/club/update-location")
    Observable<Geolocation> updateClubLocation(@Body Geolocation geolocation);

}
