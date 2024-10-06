package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;

public class RequestData {
    /**
     * SerializedName 으로 JSON 객체와 해당 변수를 매칭
     * @SerializedName 괄호 안에는 해당 JSON 객체의 변수 명 적기
     * 이때, POST 매핑으로 받아올 값은, 굳이 annotation 을 붙이지 않고, JSON 객체의 변수명과 일치하는 변수만 선언하면 됨
     */

    /**
     *
     * - url
     * - 피싱 텍스트
     * - 사용자 식별자
     *
     */

    @SerializedName("userID")
    private String userID;
    @SerializedName("text")
    private String text;

    public RequestData() {
        this.userID = userID;
        this.text = text;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMessage(String message) {
        this.text = message;
    }

    public String getMessage() {
        return text;
    }

    public void setAndroidId(String androidId) {
    }
}
