package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;
public class ResponseData1 {

    /*
     * - 사용자 식별자
     * - 피싱이냐 아니냐
     * */

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public boolean isPhishing() { return isPhishing; }

    public void setPhishing(boolean phishing) {
        isPhishing = phishing;
    }

    @SerializedName("originalUrl")
    private String originalUrl;

    @SerializedName("isPhishing")
    private boolean isPhishing;


}
