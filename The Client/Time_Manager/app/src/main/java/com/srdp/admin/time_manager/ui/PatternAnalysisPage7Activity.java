package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.srdp.admin.time_manager.R;

public class PatternAnalysisPage7Activity extends AppCompatActivity {

    private Button p7_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_analysis_page7);

        p7_btn = (Button) findViewById(R.id.p7_btn);
        p7_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PatternAnalysisPage7Activity.this, PatternAnalysisPage1Activity.class);//从哪里跳到哪里
                PatternAnalysisPage7Activity.this.startActivity(intent);
            }
        });
    }
}
