package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;

public class RequestData3 {
    @SerializedName("message")
    private String message;

    public RequestData3() {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
