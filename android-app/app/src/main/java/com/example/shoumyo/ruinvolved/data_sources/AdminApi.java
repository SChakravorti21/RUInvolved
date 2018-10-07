package com.example.shoumyo.ruinvolved.data_sources;

import android.content.Context;

import com.example.shoumyo.ruinvolved.data_sources.services.AdminService;
import com.example.shoumyo.ruinvolved.models.Geolocation;

import io.reactivex.Observable;

public class AdminApi {

    private AdminService service;

    public AdminApi(Context context) {
        this.service = ServiceFactory.getService(context, AdminService.class);
    }

    public Observable<Geolocation> updatedClubLocation(Geolocation location) {
        return service.updateClubLocation(location);
    }
}
