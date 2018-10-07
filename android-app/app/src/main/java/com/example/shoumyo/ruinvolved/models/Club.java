package com.example.shoumyo.ruinvolved.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Club implements Serializable {

    @SerializedName("Id")
    public int id;

    @SerializedName("Name")
    public String name;

    @SerializedName("ShortName")
    @Expose
    private String shortName;

    @SerializedName("ProfilePicture")
    @Expose
    private String profilePicture;

    @SerializedName("Description")
    public String description;

    @SerializedName("Summary")
    public String summary;

    @SerializedName("Status")
    public String status;

    @SerializedName("CategoryNames")
    public List<String> categoryNames;

    @SerializedName("geolocation")
    public Geolocation location;

    public String getProfilePicture() {
        return "https://se-infra-imageserver2.azureedge.net/clink/images/"
                + profilePicture;
    }

    public String getShortName() {
        if(shortName != null && !shortName.isEmpty())
            return shortName;

        return name;
    }
}
