package com.srdp.admin.time_manager.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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


public class PatternAnalysisPage4 extends AppCompatActivity {
    private BarChart chart;
    private Map<Float,float[]> Percents=new HashMap<Float, float[]>();
    private Map<Integer,String> legendDdata=new HashMap<Integer, String>();
    private int[] colors=new int[11];
    private boolean weekday=true;
    private int savingColor=R.color.lightOrange;
    private int outColor=R.color.darkOrange;
    //手势监测器
    private GestureDetector mGestureDetector;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_analysis_page4);
        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
       /* initData();
        drawChart();*/
       //进入页面时请求数据并绘制统计图
        getDataAndDraw();
        //切换休息日工作日时重新绘图
        ChartButton chartButton=findViewById(R.id.P4ChartButtion);
        chartButton.setonClick(new ChartButton.TransforWeekday() {
            @Override
            public void onTransforWeekday(boolean isWeekday) {
                weekday=isWeekday;
                //切换工作日休息日时重新绘制统计图
                getDataAndDraw();
            }
        });
        //初始化手势监测器
        mGestureDetector=new GestureDetectorUtil(this,PatternAnalysisPage5.class).getDetector();
    }
    //请求服务器端数据并且显示图表
    private void getDataAndDraw()
    {
        //设置图例
        legendDdata.clear();
        legendDdata.put(savingColor,"节约比例");
        legendDdata.put(outColor,"超时比例");
        //请求获得统计图数据
        JSONObject reqJson=new JSONObject();
        reqJson.put("weekday",weekday);
        reqJson.put("type","SimpleAnalysis");
        HttpUtil.request("AnalysisServlet?method=GetChart","post",reqJson,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    String ress=response.body().string();
                    JSONObject resJson = JSONObject.parseObject(ress);
                    JSONArray chartInfor=resJson.getJSONArray("chartInfor");
                    for(int i=0;i<chartInfor.size();i++) {
                        //设置每个柱体的高度
                        float[] every = new float[1];
                        JSONObject nowData = chartInfor.getJSONObject(i);
                        every[0] = nowData.getFloatValue("percents") / 100;
                        Percents.put((float) i, every);
                        //设置超时和节约时间的柱体颜色
                        String overtimeType = nowData.getString("type");
                        if (overtimeType.equals("timeSaving"))
                            colors[i] = savingColor;
                        else if (overtimeType.equals("timeOut"))
                            colors[i] = outColor;
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
    //调用函数绘制统计图
    private void drawChart()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DrawAnalysisChartUtil.drawChart(PatternAnalysisPage4.this,R.id.BarChartMain,"CompareBarChart",Percents,legendDdata,colors);
            }
        });

    }
    //测试静态统计图的初始化
    private void initData()
    {
        for(int i=0;i<11;i++)
        {
            float[] every=new float[1];
            if(i%3==0)
            {
                every[0]=(float) 0.3;
            }
            else if(i%3==1)
            {
                every[0]=(float)0.7;
            }
            else
            {
                every[0]=(float)0.4;
            }
            Percents.put((float)i,every);
        }
        legendDdata.put(R.color.lightOrange,"节约比例");
        legendDdata.put(R.color.darkOrange,"超时比例");
        for(int i=0;i<11;i++)
        {
            if(i%2==0)
                colors[i]=R.color.lightOrange;
            else
                colors[i]=R.color.darkOrange;
        }
    }
}
