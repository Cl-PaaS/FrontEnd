package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;

public class ResponseData2 {

    // Getter methods
    public String getURL() {
        return URL;
    }

    public void setUrl(String URL) {
        this.URL= URL;
    }

    public boolean isPhishing() {
        return isPhishing;
    }

    public void setPhishing(boolean phishing) {
        isPhishing = phishing;
    }

    @SerializedName("URL")
    private String URL;

    @SerializedName("isPhishing")
    private boolean isPhishing;
}
