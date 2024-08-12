package com.example.clpaas_frontend;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.content.ContentResolver;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clpaas_frontend.data.RequestData;
import com.example.clpaas_frontend.data.ResponseData;
import com.example.clpaas_frontend.data.RetrofitClient;
import com.example.clpaas_frontend.data.RetrofitService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;


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
    private String android_id; // Class-level variable
    private String message = "피싱 텍스트 test@naver.com 010-1234-5678 http://localhost:8080"; //피싱 텍스트
    // 콜백 인터페이스 정의
    interface AndroidIdCallback {
        void onIdReceived(String android_id);
    }
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
            getAndroidId(new AndroidIdCallback() {
                @Override
                public void onIdReceived(String android_id) {
                    // FID를 사용하여 작업 수행
                    Log.d("유저 ID", "Received Installation ID: " + android_id);
                    service = RetrofitClient.getClient().create(RetrofitService.class);
                    //request Data 객체 선언, getter,setter 메소드 사용
                    RequestData requestData = new RequestData(android_id, message);


                    // 메시지를 나중에 다연이가 따로 받아오는 값으로 변경해주면됨
                    service.requestData(requestData).enqueue(new Callback<ResponseData>()
                    {
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
    // FID를 반환하는 메소드
    private void getAndroidId(AndroidIdCallback callback) {
            FirebaseInstallations.getInstance().getId()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (task.isSuccessful()) {
                                android_id = task.getResult();
                                Log.d("Installations", "Installation ID: " + android_id);
                                callback.onIdReceived(android_id); // 콜백 호출

                            } else {
                                Log.e("Installations", "Unable to get Installation ID");
                                callback.onIdReceived(null); // 실패 시 null 반환
                            }
                        }
                    });
    }




}