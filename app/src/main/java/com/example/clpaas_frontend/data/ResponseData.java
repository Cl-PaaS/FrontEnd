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

    @SerializedName("someData") // JSON에서 this 필드에 매핑될 키 이름이 someData인 경우
    private String someData;

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

    public String getSomeData() { return someData; }

    public void setSomeData(String someData) {
        this.someData = someData;
    }
}
