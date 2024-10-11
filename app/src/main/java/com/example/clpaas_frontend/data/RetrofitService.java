package com.example.clpaas_frontend.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    // 데이터 전달 및 값 받아오기
    // 나중에 여기를 수정해야함 ( api 경로)

    //윤호BE
//    @GET("clpaas-python/check-url")
//    Call<ResponseData1> requestDataFromApi1(@Query("url") String url);

    //유빈BE
    @POST("/check")
    Call<ResponseData1> requestDataFromApi1(@Query("URL") String url);

//    @POST("api/message1")
//    Call<ResponseData> requestDataFromApi1(@Body RequestData data);
//
//    // 두 번째 API 호출
//    @POST("api/message2") // 두 번째 API 엔드포인트
//    Call<ResponseData> requestDataFromApi2(@Body RequestData data);

    // 세 번째 API 호출
//    @POST("api/message3") // 세 번째 API 엔드포인트
//    Call<ResponseData1> requestDataFromApi3(@Body RequestData1 data);
}
