package com.example.clpaas_frontend;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.content.ContentResolver;
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
import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private RetrofitService service;
    private ResponseData responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requirePerms();


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeServiceAndSendData();
            } else {
                Log.e("Permission Error", "READ_PHONE_STATE permission not granted");
            }
        }
    }
    private void initializeServiceAndSendData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            //String android_id = Settings.Secure.getString(ContentResolver contentResolver, Settings.Secure.ANDROID_ID);
            //android_id 가져오기
            String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.d("Android ID", android_id);
            service = RetrofitClient.getClient().create(RetrofitService.class);
            service.requestData(new RequestData(android_id, "피싱 텍스트")).enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if (response.isSuccessful()) {
                        Log.d("응답 데이터", response.body().toString());
                    }
                }
                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            /***
             *
             위에서 reponse 부분에 대한 처리를 해주면 됨
             * response.body() 를 통해 서버에서 받아온 데이터를 사용할 수 있음
             * 필요한 데이터를 가져다가 사용하면 됨
             * ex ) textView 를 이용해서 응답값을 받고싶다면 뽑아내서 나타내면됨
             */

        } else {
            Log.e("Permission Error", "READ_PHONE_STATE permission not granted");
        }

    }

    // 권한 없을 경우 요청하는 함수
    public void requirePerms(){
        String[] permissions = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE};
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS);
        if (permissionCheck == PackageManager.PERMISSION_DENIED){
            //ActivityCompat.requestPermissions(this, permissions, 1);
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }else {
            initializeServiceAndSendData();
        }
    }




}