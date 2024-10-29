package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;
public class ResponseData1 {

    public boolean isPhishing() {
        return isPhishing;
    }

    @SerializedName("originalUrl")
    private String originalUrl;

    @SerializedName("isPhishing")
    private boolean isPhishing;
}
