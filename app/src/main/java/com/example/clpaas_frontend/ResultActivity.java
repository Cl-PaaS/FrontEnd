// ResultActivity.java
package com.example.clpaas_frontend;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultTextView = findViewById(R.id.resultTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView); // 안전 점수를 출력할 TextView

        // Intent로부터 결과 데이터를 가져옴
        boolean[] results = getIntent().getBooleanArrayExtra("results");
        int phishingRiskLevel = calculateRiskLevel(results);

        String riskMessage = getRiskMessage(phishingRiskLevel);
        int safetyScore = getSafetyScore(phishingRiskLevel); // 안전 점수 계산

        // 결과와 점수 설정
        resultTextView.setText(riskMessage);
        scoreTextView.setText(safetyScore + "%"); // 안전 점수 출력
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

    // 피싱 위험 메시지를 반환하는 메서드
    private String getRiskMessage(int riskLevel) {
        switch (riskLevel) {
            case 0: return "피싱위험: 좋음";
            case 1: return "피싱위험: 보통";
            case 2: return "피싱위험: 위험";
            case 3: return "피싱위험: 매우위험";
            default: return "피싱위험: 알 수 없음";
        }
    }

    // 위험 수준에 따른 안전 점수를 반환하는 메서드
    private int getSafetyScore(int riskLevel) {
        switch (riskLevel) {
            case 0: return 100; // 위험 없음 -> 안전 점수 100%
            case 1: return 80;  // 보통 -> 안전 점수 80%
            case 2: return 50;  // 위험 -> 안전 점수 50%
            case 3: return 0;   // 매우위험 -> 안전 점수 0%
            default: return -1; // 알 수 없음 -> 안전 점수 ?
        }
    }
}
