package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;
public class ResponseData1 {
//    public String getOriginalUrl() {
//        return originalUrl;
//    }
//
//    public void setOriginalUrl(String originalUrl) {
//        this.originalUrl = originalUrl;
//    }
//
//    public void setPhishing(boolean phishing) {
//        isPhishing = phishing;
//    }

    public boolean isPhishing() {
        return isPhishing;
    }

    @SerializedName("originalUrl")
    private String originalUrl;

    @SerializedName("isPhishing")
    private boolean isPhishing;
}
