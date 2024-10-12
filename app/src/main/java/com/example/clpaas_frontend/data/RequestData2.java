package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;

public class RequestData2 {

    // URL 필드를 JSON 객체와 매핑
    @SerializedName("message")
    private String message;

    public RequestData2() {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


