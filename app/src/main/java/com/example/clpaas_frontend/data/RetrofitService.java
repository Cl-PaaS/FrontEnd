package com.example.clpaas_frontend.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    // 데이터 전달 및 값 받아오기
    // 나중에 여기를 수정해야함 ( api 경로)

    // Spring api
    @POST("/check") // API 엔드포인트
    Call<ResponseData1> requestDataFromApi1(@Body RequestData1 data);

    // flask api
    @GET("/check-url")
    Call<ResponseData2> requestDataFromApi2(@Query("url") String url);

    // NestJS api
    @POST("/nest/validator")
    Call<ResponseData3> requestDataFromApi3(@Body RequestData3 data);
}
