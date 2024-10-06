package com.example.clpaas_frontend.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // API 호출 시 사용할 RetrofitService 인터페이스
//    public static RetrofitService getApiService() {
//        return getClient().create(RetrofitService.class);
//    }

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
    // 첫 번째 API 호출을 위한 서비스 생성
    public static RetrofitService getApiServiceForFirst() {
        return getClient("http://backend.210-104-77-246.nip.io/check").create(RetrofitService.class);
    }

    // 두 번째 API 호출을 위한 서비스 생성
    public static RetrofitService getApiServiceForSecond() {
        return getClient("http://api2.example.com/").create(RetrofitService.class);
    }

    // 세 번째 API 호출을 위한 서비스 생성
    public static RetrofitService getApiServiceForThird() {
        return getClient("http://api3.example.com/").create(RetrofitService.class);
    }
}


/**
 *     (2)  RetrofitService 인터페이스와 동일한 package인 network에 RetrofitClient.class 추가
 *       1)  API 호출 시 사용하게 되는 첫 번째 클래스
 *         -  Retrofit 객체를 생성하고, RetrofitService 클래스를 이후 MainActivity.class 에서 회원가입 버튼 클릭을 통한 API 호출 시 연결
 *       2)  사용되는 메서드
 *         -  baseUrl() :  서비스에서 사용할 루트를 설정하는 메서드 -> 이전에 할당받은 public dns
 *         -  addConverterFactory() : JSON을 분석할 수 있는 객체를 추가하는 메서드
0    * */

