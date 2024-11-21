package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;

public class RequestData1 {
    @SerializedName("message")
    private String message;

    public RequestData1() {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
