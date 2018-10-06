package com.example.shoumyo.ruinvolved.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    @SerializedName("category_id")
    public int categoryId;

    @SerializedName("category_name")
    public String categoryName;

    @SerializedName("clubs")
    List<Integer> clubIds;

}
