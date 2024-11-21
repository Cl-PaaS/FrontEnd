package com.example.clpaas_frontend.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Retrofit 클라이언트 설정
    public static Retrofit getClient(String baseUrl) {
        // Gson 변환기
        Gson gson = new GsonBuilder().setLenient().create();

        // OkHttpClient 설정: 타임아웃을 30초로 설정 (필요에 따라 변경 가능)
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // 연결 타임아웃
                .readTimeout(30, TimeUnit.SECONDS)     // 읽기 타임아웃
                .writeTimeout(30, TimeUnit.SECONDS)    // 쓰기 타임아웃
                .build();

        // Retrofit 빌더
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)                 // OkHttpClient 적용
                .addConverterFactory(GsonConverterFactory.create(gson)) // JSON을 변환하기 위한 GSON 사용
                .build();
    }

    // API 호출을 위한 서비스 생성
    public static RetrofitService getApiServiceForSpring() {
        String springApiBaseUrl ="http://backend.210-104-77-246.nip.io";
        return getClient(springApiBaseUrl).create(RetrofitService.class);
    }

    public static RetrofitService getApiServiceForFlask() {
        String flaskApiBaseUrl ="http://backend.210-104-77-246.nip.io";
        return getClient(flaskApiBaseUrl).create(RetrofitService.class);
    }

    // 세 번째 API 호출을 위한 서비스 생성
    public static RetrofitService getApiServiceForNestJS() {
        String nestJsBaseUrl = "http://backend.210-104-77-246.nip.io";
        return getClient(nestJsBaseUrl).create(RetrofitService.class);
    }

}