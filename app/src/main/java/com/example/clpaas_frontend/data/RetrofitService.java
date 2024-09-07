package com.example.clpaas_frontend.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface RetrofitService {
    // 데이터 전달 및 값 받아오기
    // 나중에 여기를 수정해야함 ( api 경로)

    @POST("api/message")
    Call<ResponseData> requestDataFromApi1(@Body RequestData data);

    // 두 번째 API 호출
    @POST("api/message2") // 두 번째 API 엔드포인트
    Call<ResponseData> requestDataFromApi2(@Body RequestData data);

    // 세 번째 API 호출
    @POST("api/message3") // 세 번째 API 엔드포인트
    Call<ResponseData> requestDataFromApi3(@Body RequestData data);
}
