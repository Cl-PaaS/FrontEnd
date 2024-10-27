package com.example.clpaas_frontend;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Intent로부터 결과 데이터를 가져옴
        boolean[] results = getIntent().getBooleanArrayExtra("results");
        int phishingRiskLevel = calculateRiskLevel(results);

        // 위험 수준에 따라 다른 레이아웃을 설정
        switch (phishingRiskLevel) {
            case 3:
                setContentView(R.layout.activity_result_danger); // 매우위험
                break;
            case 2:
                setContentView(R.layout.activity_result_mid); // 위험
                break;
            case 1:
            case 0:
                setContentView(R.layout.activity_result_safe); // 보통 또는 좋음
                break;
            default:
                setContentView(R.layout.activity_result_mid); // 기본값으로 안전 화면 설정
                break;
        }
    }

    // True 개수를 반환하는 메서드
    private int calculateRiskLevel(boolean[] results) {
        int trueCount = 0;
        for (boolean result : results) {
            if (result) {
                trueCount++;
            }
        }
        return trueCount;
    }
}
