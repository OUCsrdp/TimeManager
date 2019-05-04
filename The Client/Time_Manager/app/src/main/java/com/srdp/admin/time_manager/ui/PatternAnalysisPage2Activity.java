package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.util.GestureDetectorUtil;
import com.srdp.admin.time_manager.util.HttpUtil;
import com.srdp.admin.time_manager.widget.AnalysisChart.ChartButton;
import com.srdp.admin.time_manager.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

import static com.alibaba.fastjson.JSON.parseObject;

public class PatternAnalysisPage2Activity extends AppCompatActivity {

    private TextView p2_time_text;
    private ChartButton p2_chartButton;
    private boolean weekDay = true;
    private String delayedTime;
    //手势检测器
    private GestureDetector mGestureDetector;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_analysis_page2);

        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        p2_time_text = (TextView) findViewById(R.id.p2_time_text);
        p2_chartButton = (ChartButton) findViewById(R.id.p2_chartButton);

        getData();
        //weekday点击事件
        p2_chartButton.setonClick(new ChartButton.TransforWeekday() {
            @Override
            public void onTransforWeekday(boolean isWeekday) {
                weekDay=isWeekday;
                //切换工作日休息日时重新绘制统计图
                getData();
            }
        });
        //初始化手势监测器
        mGestureDetector=new GestureDetectorUtil(this,PatternAnalysisPage3Activity.class).getDetector();
    }
    //从后端获取平均推迟时间
    private void getData(){
        JSONObject reportObject = new JSONObject();
        reportObject.put("weekday",weekDay);
        HttpUtil.request("AnalysisServlet?method=GetDelayedTime","post",reportObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    String jsonString = response.body().string();
                    Log.i("jsonString",jsonString);
                    delayedTime = parseObject(jsonString).getString("delayedtime");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            p2_time_text.setText(delayedTime);
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
    }
}
