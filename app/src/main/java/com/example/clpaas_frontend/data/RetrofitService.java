package com.example.clpaas_frontend.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface RetrofitService {
    // 데이터 전달 및 값 받아오기
    @POST("api/message")
    Call<ResponseData> requestData(@Body RequestData data);
<<<<<<< HEAD
}
=======
    // 나중에 여기를 수정해야함 ( api 경로)

}
>>>>>>> origin/feat/#1
