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
import retorfit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

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
        service = RetrofitClient.getClient().create(RetrofitService.class);

        // 문자 데이터 확인 cc.다연
        /**
         * ex ) 특정 버튼이나 작업을 거친 후에
         * 1. 데이터를 requestData 객체에 담는다.
         * 2. sendData(requestData) 메소드를 호출하여 데이터를 백엔드로 전송
         * 3. 백엔드에서 받은 데이터를 처리
         * */
    }

    // 권한 없을 경우 요청하는 함수
    public void requirePerms(){
        String[] permissions = {Manifest.permission.RECEIVE_SMS};
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS);
        if (permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }
}