package com.example.clpaas_frontend.data;

import com.google.gson.annotations.SerializedName;

public class RequestData1 {
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

    @SerializedName("message")
    private String message;

    public RequestData1() {
        this.message = message;
    } // 이 값을 api에 따라 바꾸기

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
