package com.example.shoumyo.ruinvolved.data_sources.services;

import com.example.shoumyo.ruinvolved.models.Geolocation;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AdminService {

    @POST("/club/update-location")
    Observable<Geolocation> updateClubLocation(@Body Geolocation geolocation);
}
