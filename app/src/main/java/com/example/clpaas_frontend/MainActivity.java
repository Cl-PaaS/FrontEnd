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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_READ_PHONE_STATE = 1;
    private RetrofitService service;
    private ResponseData responseData;
    private String android_id; // Class-level variable
    private String message = "피싱 텍스트 test@naver.com 010-1234-5678 http://10.0.2.2:8080"; //피싱 텍스트

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

                    // RequestData 객체 선언 및 FID와 message 변수를 사용하여 데이터 설정
                    RequestData requestData = new RequestData();
                    requestData.setAndroidId(android_id); // FID 설정
                    requestData.setMessage(message); // 메시지 설정

                    service = RetrofitClient.getClient().create(RetrofitService.class);

                    // 병렬로 API 호출
                    Call<ResponseData> call1 = service.requestDataFromApi1(requestData);
                    Call<ResponseData> call2 = service.requestDataFromApi2(requestData);
                    Call<ResponseData> call3 = service.requestDataFromApi3(requestData);

                    // 첫 번째 API 호출
                    call1.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                handleApiResult(response.body(), 1);
                            } else {
                                Log.e("API 1 응답 실패", "응답을 받지 못했습니다.");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                    // 두 번째 API 호출
                    call2.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                handleApiResult(response.body(), 2);
                            } else {
                                Log.e("API 2 응답 실패", "응답을 받지 못했습니다.");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                    // 세 번째 API 호출
                    call3.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                handleApiResult(response.body(), 3);
                            } else {
                                Log.e("API 3 응답 실패", "응답을 받지 못했습니다.");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            });
        } else {
            Log.e("Permission Error", "READ_PHONE_STATE permission not granted");
        }
    }

    // API 결과 처리 메서드
    private void handleApiResult(ResponseData responseData, int apiIndex) {
        // 각 API에서 받은 데이터를 저장 또는 처리
        String apiResult = "API " + apiIndex + " 데이터: " + responseData.getSomeData();

        // 화면 업데이트는 메인 스레드에서 이루어져야 하므로 runOnUiThread 사용
        runOnUiThread(() -> {
            TextView textView = findViewById(R.id.textView);
            String currentText = textView.getText().toString();
            textView.setText(currentText + "\n" + apiResult);
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