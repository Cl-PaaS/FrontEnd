package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;

public class RequestData2 {

    // URL 필드를 JSON 객체와 매핑
    @SerializedName("URL")
    private String URL;

    public RequestData2(String URL) {
        this.URL = URL;
    }

    // URL 필드의 Getter 및 Setter
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}


