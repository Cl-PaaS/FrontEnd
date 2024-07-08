package com.example.clpaas_frontend.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private final static String API_URL="http://localhost:8080/";

    public static RetrofitService getApiService(){
        return getClient().create(RetrofitService.class);
    }

    public static Retrofit getClient(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /**
     *     (2)  RetrofitService 인터페이스와 동일한 package인 network에 RetrofitClient.class 추가
     *       1)  API 호출 시 사용하게 되는 첫 번째 클래스
     *         -  Retrofit 객체를 생성하고, RetrofitService 클래스를 이후 MainActivity.class 에서 회원가입 버튼 클릭을 통한 API 호출 시 연결
     *       2)  사용되는 메서드
     *         -  baseUrl() :  서비스에서 사용할 루트를 설정하는 메서드 -> 이전에 할당받은 public dns
     *         -  addConverterFactory() : JSON을 분석할 수 있는 객체를 추가하는 메서드
 0    * */
}
