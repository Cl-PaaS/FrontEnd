package com.example.clpaas_frontend;

import android.os.Bundle;
import android.provider.Settings;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clpaas_frontend.data.RequestData;
import com.example.clpaas_frontend.data.ResponseData;
import com.example.clpaas_frontend.data.RetrofitClient;
import com.example.clpaas_frontend.data.RetrofitService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //demo data

    private RetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        String android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        service = RetrofitClient.getClient().create(RetrofitService.class);

        // 문자 데이터 확인 cc.다연
        /**
         * ex ) 특정 버튼이나 작업을 거친 후에
         * 1. 데이터를 requestData 객체에 담는다.
         * 2. sendData(requestData) 메소드를 호출하여 데이터를 백엔드로 전송
         * 3. 백엔드에서 받은 데이터를 처리
         * */
    }

    //데이터 백엔드 전송
    private ResponseData sendData(RequestData requestData) {
        Call<ResponseData> call = service.requestData(requestData);
        //response 를 리턴하는 코드 생성
        try {
            Response<ResponseData> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        service.requestData(requestData).enqueue(new Callback<ResponseData>() {
//            @Override
//            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
//                if (response.isSuccessful()) {
//                    ResponseData result = response.body();
//                    return response;
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseData> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

        return null;

    }


}