package com.example.shoumyo.ruinvolved.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Geolocation implements Serializable {

    @SerializedName("club_id")
    public int clubId;

    @SerializedName("lat")
    public double latitude;

    @SerializedName("lng")
    public double longitude;
}
