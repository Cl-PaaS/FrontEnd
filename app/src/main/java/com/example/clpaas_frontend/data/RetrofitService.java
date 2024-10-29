package com.example.clpaas_frontend.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    // Spring api
    @POST("/check") // API 엔드포인트
    Call<ResponseData1> requestDataFromApi1(@Body RequestData1 data);

    // flask api
    @POST("/python/check")
    Call<ResponseData2> requestDataFromApi2(@Body RequestData2 data);

    // NestJS api
    @POST("/nest/validator")
    Call<ResponseData3> requestDataFromApi3(@Body RequestData3 data);
}
