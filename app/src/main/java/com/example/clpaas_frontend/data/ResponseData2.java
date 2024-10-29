package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;

public class ResponseData2 {

    @SerializedName("isPhishing")
    private boolean isPhishing;

    public ResponseData2() {
    }

    public boolean isPhishing() {
        return isPhishing;
    }

    public void setPhishing(boolean isPhishing) {
        this.isPhishing = isPhishing;
    }
}
