package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;
public class ResponseData {

    /*
     * - 사용자 식별자
     * - 피싱이냐 아니냐
     * */
    @SerializedName("status")
    private String status;

    @SerializedName("userId")
    private String userID;

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSomeData() {
        return someData;
    }

    public void setSomeData(String someData) {
        this.someData = someData;
    }
}
