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

        // Intent로부터 결과 데이터를 가져옴
        boolean[] results = getIntent().getBooleanArrayExtra("results");
        int phishingRiskLevel = calculateRiskLevel(results);

        String riskMessage = getRiskMessage(phishingRiskLevel);
        resultTextView.setText(riskMessage);
    }

    private int calculateRiskLevel(boolean[] results) {
        int trueCount = 0;
        for (boolean result : results) {
            if (result) {
                trueCount++;
            }
        }
        return trueCount; // True 개수를 반환
    }

    private String getRiskMessage(int riskLevel) {
        switch (riskLevel) {
            case 0: return "피싱위험: 없음";
            case 1: return "피싱위험: 보통";
            case 2: return "피싱위험: 위험";
            case 3: return "피싱위험: 매우위험";
            default: return "피싱위험: 알 수 없음";
        }
    }
}
