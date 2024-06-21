package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;

public class ResponseData {

    /*
     * - 사용자 식별자
     * - 피싱이냐 아니냐
     * */
    @SerializedName("status")
    private boolean status;

    @SerializedName("userId")
    private String userID;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
