package com.srdp.admin.time_manager.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.BarChart;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.util.DrawAnalysisChartUtil;
import com.srdp.admin.time_manager.util.GestureDetectorUtil;
import com.srdp.admin.time_manager.util.HttpUtil;
import com.srdp.admin.time_manager.widget.AnalysisChart.ChartButton;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class PatternAnalysisPage6 extends AppCompatActivity {
    private BarChart chart;
    private Map<Float,float[]> Percents=new HashMap<Float, float[]>();
    private int[] colors=new int[1];
    private boolean weekday=true;
    private double density=0.0;
    //手势监测器
    private GestureDetector mGestureDetector;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_analysis_page6);
        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        //初始化颜色
        colors[0]=R.color.orange;
        /*initData();
        drawChart();*/
        //进入页面时绘制统计图
        getDataAndDraw();
        ChartButton chartButton=findViewById(R.id.P6ChartButtion);
        chartButton.setonClick(new ChartButton.TransforWeekday() {
            @Override
            public void onTransforWeekday(boolean isWeekday) {
                weekday=isWeekday;
                //切换工作日休息日时重新绘制统计图
                getDataAndDraw();
            }
        });
        //初始化手势监测器
        mGestureDetector=new GestureDetectorUtil(this,Index_Timing_Change.class).getDetector();
    }
    private void getDataAndDraw()
    {
        //请求获得统计图数据
        final JSONObject reqJson=new JSONObject();
        reqJson.put("weekday",weekday);
        reqJson.put("type","densityAnalysis");
        HttpUtil.request("AnalysisServlet?method=GetChart","post",reqJson,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    String ress=response.body().string();
                    JSONObject resJson = JSONObject.parseObject(ress);
                    JSONArray chartInfor=resJson.getJSONArray("chartInfor");
                    density=resJson.getDoubleValue("density");
                    for(int i=0;i<chartInfor.size();i++)
                    {
                        //设置每个柱体的高度
                        float[] every=new float[1];
                        JSONObject nowData=chartInfor.getJSONObject(i);
                        every[0]=nowData.getFloatValue("percents");
                        Percents.put((float)i,every);
                    }
                    drawChart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call,@NonNull IOException e) {
            }
        });
    }
    private void drawChart(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView allDensityText=(TextView)findViewById(R.id.AnalysisPage6Density);
                allDensityText.setText("平均每小时完成的事件数为"+density);
                DrawAnalysisChartUtil.drawChart(PatternAnalysisPage6.this,R.id.DensityChartMain,"DensityBarChart",Percents,colors);
            }
        });
    }
    private void initData()
    {
        for(int i=0;i<7;i++)
        {
            float[] every=new float[1];
            if(i%3==0)
                every[0]=7;
            else if(i%3==1)
                every[0]=3;
            else
                every[0]=12;
            Percents.put((float)i,every);
        }
        colors[0]=R.color.orange;
    }
    private void drawChartTest()
    {
        DrawAnalysisChartUtil.drawChart(this,R.id.DensityChartMain,"DensityBarChart",Percents,colors);
    }
}
