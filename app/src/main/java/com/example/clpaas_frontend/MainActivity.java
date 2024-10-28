package com.example.clpaas_frontend;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clpaas_frontend.data.RequestData1;
import com.example.clpaas_frontend.data.RequestData2;
import com.example.clpaas_frontend.data.RequestData3;

import com.example.clpaas_frontend.data.ResponseData1;
import com.example.clpaas_frontend.data.ResponseData2;
import com.example.clpaas_frontend.data.ResponseData3;

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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private boolean[] phishingResults = new boolean[3]; // 결과를 저장할 배열 추가
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_READ_PHONE_STATE = 1;
    private RetrofitService service;
    private ResponseData1 responseData;
    private ResponseData2 responseData2;
    private String android_id; // Class-level variable
    private String message ; //피싱 텍스트

    // 콜백 인터페이스 정의
    interface AndroidIdCallback {
        void onIdReceived(String android_id);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 인텐트로 전달받은 message 확인
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("message_content")) {
            message = intent.getStringExtra("message_content");
            initializeServiceAndSendData(message);  // API 호출 메서드
        }

        TextView descriptionTextView = findViewById(R.id.mainDescriptionTextView);
        Button sendDataButton = findViewById(R.id.startButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainTitleTextImage), (v, insets) -> {
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

//        TextView descriptionTextView = findViewById(R.id.mainDescriptionTextView);

        String text = "해송님, 지금까지 \n10번 만큼 \n폰을 지켰어요!";
        SpannableString spannable = new SpannableString(text);

        // "땡땡" 부분을 굵게 설정
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // "12번" 부분을 굵게 설정
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 10, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        descriptionTextView.setText(spannable);
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
                    RequestData1 requestData1 = new RequestData1();
                    RequestData2 requestData2 = new RequestData2();
                    RequestData3 requestData3 = new RequestData3();
//                    requestData.setAndroidId(android_id); // FID 설정
                    requestData1.setMessage(message); // 메시지 설정
                    requestData2.setMessage(message); // 메시지 설정
                    requestData3.setMessage(message); // 메시지 설정

//                    service = RetrofitClient.getClient(message).create(RetrofitService.class);
//                    service = RetrofitClient.getApiServiceForSpring();
//                    service = RetrofitClient.getApiServiceForFlask();
//                    service = RetrofitClient.getApiServiceForNestJS();

                    // RetrofitService 인스턴스 생성 수정
                    RetrofitService service1 = RetrofitClient.getApiServiceForSpring();
                    RetrofitService service2 = RetrofitClient.getApiServiceForFlask();
                    RetrofitService service3 = RetrofitClient.getApiServiceForNestJS();

                    // 병렬로 API 호출
//                    Call<ResponseData1> call1 = service.requestDataFromApi1(requestData1);
//                    Call<ResponseData2> call2 = service.requestDataFromApi2(requestData2);
//                    Call<ResponseData3> call3 = service.requestDataFromApi3(requestData3);

                    Call<ResponseData1> call1 = service1.requestDataFromApi1(requestData1);
                    Call<ResponseData2> call2 = service2.requestDataFromApi2(requestData2);
                    Call<ResponseData3> call3 = service3.requestDataFromApi3(requestData3);

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
                    call2.enqueue(new Callback<ResponseData2>() {
                        @Override
                        public void onResponse(Call<ResponseData2> call, Response<ResponseData2> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                handleApiResult(response.body(), 2);
                            } else {
                                Log.d("API 2 응답 실패", "응답을 받지 못했습니다.");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData2> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
//
//                    // 세 번째 API 호출
                    call3.enqueue(new Callback<ResponseData3>() {
                        @Override
                        public void onResponse(Call<ResponseData3> call, Response<ResponseData3> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                handleApiResult(response.body(), 3);
                            } else {
                                Log.d("API 3 응답 실패", "응답을 받지 못했습니다.");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData3> call, Throwable t) {
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
    private void handleApiResult(Object responseData, int apiIndex) {
        boolean isPhishing = false;

        // 응답 객체 타입에 따라 처리
        if (responseData instanceof ResponseData1) {
            isPhishing = ((ResponseData1) responseData).isPhishing();
        } else if (responseData instanceof ResponseData2) {
            isPhishing = ((ResponseData2) responseData).isPhishing();
        } else if (responseData instanceof ResponseData3) {
            isPhishing = ((ResponseData3) responseData).isPhishing();
        }

        phishingResults[apiIndex - 1] = isPhishing; // 결과 저장
        Log.d("API Result", "API " + apiIndex + " - isPhishing: " + isPhishing);

        // 모든 API 응답 처리 완료 체크
        checkAllApisCompleted(apiIndex);

        runOnUiThread(() -> {
            // responseData가 null인 경우 "응답 없음" 메시지 출력
            String logMessage = (responseData != null)
                    ? "API " + apiIndex + " 데이터: 응답 성공"
                    : "API " + apiIndex + " 데이터: 응답 없음";
            Log.d("API Response", logMessage);
        });

    }

    private void checkAllApisCompleted(int apiIndex) {
        if (apiIndex == 3) {
            // 모든 API 호출이 완료된 후 결과를 처리
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("results", phishingResults); // 결과 배열 전달
            startActivity(intent);
        }
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