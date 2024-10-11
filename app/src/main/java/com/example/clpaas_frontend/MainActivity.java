package com.example.clpaas_frontend;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clpaas_frontend.data.RequestData1;
import com.example.clpaas_frontend.data.ResponseData1;
import com.example.clpaas_frontend.data.RetrofitClient;
import com.example.clpaas_frontend.data.RetrofitService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class MainActivity extends AppCompatActivity {
    private boolean[] phishingResults = new boolean[3]; // 결과를 저장할 배열 추가
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_READ_PHONE_STATE = 1;
    private RetrofitService service;
    private ResponseData1 responseData;
    private String android_id; // Class-level variable
    private String message; //피싱 텍스트

    // 콜백 인터페이스 정의
    interface AndroidIdCallback {
        void onIdReceived(String android_id);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View textView = findViewById(R.id.textView);
        Button sendDataButton = findViewById(R.id.sendDataButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sendDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trigger the API calls here when the button is clicked
                requirePerms();  // Ensure permissions are checked
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeServiceAndSendData(message);
            } else {
                Log.e("Permission Error", "READ_PHONE_STATE permission not granted");
            }
        }
    }

    private void initializeServiceAndSendData(String message) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            getAndroidId(new AndroidIdCallback() {
                @Override
                public void onIdReceived(String android_id) {
                    // FID를 사용하여 작업 수행
                    Log.d("유저 ID", "Received Installation ID: " + android_id);

                    // RequestData 객체 선언 및 FID와 message 변수를 사용하여 데이터 설정
                    RequestData1 requestData = new RequestData1();
//                    requestData.setAndroidId(android_id); // FID 설정
                    requestData.setMessage(message); // 메시지 설정

//                    service = RetrofitClient.getClient(message).create(RetrofitService.class);
                    service = RetrofitClient.getApiServiceForFirst();
                    //RetrofitService service = RetrofitClient.getApiServiceForThird(); // 여기서 필요한 API에 맞게 설정
                    // RetrofitService service = RetrofitClient.getApiService();

                    // 병렬로 API 호출
                    Call<ResponseData1> call1 = service.requestDataFromApi1(requestData.getMessage()); //api에서 받는 요청할 때 보내는 http body값
//                    Call<ResponseData2> call2 = service.requestDataFromApi2(requestData);
//                    Call<ResponseData3> call3 = service.requestDataFromApi3(requestData);

                    // 첫 번째 API 호출
                    call1.enqueue(new Callback<ResponseData1>() {
                        @Override
                        public void onResponse(Call<ResponseData1> call, Response<ResponseData1> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                handleApiResult(response.body(), 1);
                            } else {
                                Log.d("API 1 응답 실패", "응답을 받지 못했습니다.");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData1> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                    // 두 번째 API 호출
//                    call2.enqueue(new Callback<ResponseData1>() {
//                        @Override
//                        public void onResponse(Call<ResponseData1> call, Response<ResponseData1> response) {
//                            if (response.isSuccessful() && response.body() != null) {
//                                handleApiResult(response.body(), 2);
//                            } else {
//                                Log.d("API 2 응답 실패", "응답을 받지 못했습니다.");
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseData1> call, Throwable t) {
//                            t.printStackTrace();
//                        }
//                    });
//
//                    // 세 번째 API 호출
//                    call3.enqueue(new Callback<ResponseData1>() {
//                        @Override
//                        public void onResponse(Call<ResponseData1> call, Response<ResponseData1> response) {
//                            if (response.isSuccessful() && response.body() != null) {
//                                handleApiResult(response.body(), 3);
//                            } else {
//                                Log.d("API 3 응답 실패", "응답을 받지 못했습니다.");
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseData1> call, Throwable t) {
//                            t.printStackTrace();
//                        }
//                    });
                }
            });
        } else {
            Log.e("Permission Error", "READ_PHONE_STATE permission not granted");
        }
    }

    // API 결과 처리 메서드
    private void handleApiResult(ResponseData1 responseData, int apiIndex) {

        boolean isPhishing = responseData.isPhishing();
        // null인지 확인하고 싶으면 나중에 if로 추가하기
        phishingResults[apiIndex - 1] = isPhishing; // 결과 저장

        Log.d("API Result", "API " + apiIndex + " - isPhishing: " + isPhishing);

        // 추가 로직: 모든 API 응답을 받은 후 처리할 로직을 여기에 추가
        // 모든 API 호출이 완료된 후 결과를 처리
        if (apiIndex == 3) {
            // ResultActivity로 결과 전달
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("results", phishingResults); // 결과 배열 전달
            startActivity(intent);
        }

        runOnUiThread(() -> {
            String logMessage = "API " + apiIndex + " 데이터: " + (responseData.isPhishing() ? responseData.isPhishing() : "응답 없음");
            Log.d("API Response", logMessage);
        });
    }

    // 권한 없을 경우 요청하는 함수
    public void requirePerms(){
        String[] permissions = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE};
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS);
        int readPhoneStatePermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck == PackageManager.PERMISSION_DENIED ||
                readPhoneStatePermissionCheck == PackageManager.PERMISSION_DENIED) {
                //ActivityCompat.requestPermissions(this, permissions, 1);
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }else {
            initializeServiceAndSendData(message);
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