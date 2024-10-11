package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;

public class ResponseData3 {
    @SerializedName("isPhishing")
    private boolean isPhishing;

    public ResponseData3() {
        // 기본 생성자
    }

    public boolean isPhishing() {
        return isPhishing;
    }

    public void setPhishing(boolean isPhishing) {
        this.isPhishing = isPhishing;
    }
}
