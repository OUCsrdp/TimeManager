package com.srdp.admin.time_manager.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

import static com.alibaba.fastjson.JSON.parseObject;


public class PatternAnalysisPage1Activity extends AppCompatActivity {

    private TextView p1_day_count;//天数
    private Button p1_btn_next;//下一页按钮
    private String resText;//例如367天

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_analysis_page1);
        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        p1_btn_next = (Button) findViewById(R.id.p1_btn_next);
        p1_day_count = (TextView) findViewById(R.id.p1_day_count);

        //从后端获取天数填写到text中
        JSONObject reportObject = new JSONObject();
        HttpUtil.request("AnalysisServlet?method=GetDays","post",reportObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    String jsonString = response.body().string();
                    Log.i("jsonString",jsonString);
                    int dayCount = parseObject(jsonString).getIntValue("days");
                    resText = dayCount + "天";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            p1_day_count.setText(resText);
                        }
                    });

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                //createFail();
            }
        });

        //跳转到下一页
        p1_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PatternAnalysisPage1Activity.this, PatternAnalysisPage2Activity.class);//从哪里跳到哪里
                startActivity(intent);
                overridePendingTransition(R.transition.in_from_bottom, R.transition.out_to_top);
                //ActivityOptionsCompat options6 = ActivityOptionsCompat.makeSceneTransitionAnimation(PatternAnalysisPage1Activity.this);
                //ActivityCompat.startActivity(PatternAnalysisPage1Activity.this,intent, options6.toBundle());
                //PatternAnalysisPage1Activity.this.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(PatternAnalysisPage1Activity.this).toBundle());
            }
        });
    }
    public void slideToNext(){

    }
}
