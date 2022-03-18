package com.example.peapplication.ui.home;

import com.example.peapplication.ListItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatestListings {
    @SerializedName("List")
    private List<ListItem> list;

    public LatestListings() {}

    public List<ListItem> getList() {
        return list;
    }

}
