package com.srdp.admin.time_manager.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.util.DrawAnalysisChartUtil;
import com.srdp.admin.time_manager.util.HttpUtil;
import com.srdp.admin.time_manager.widget.AnalysisChart.ChartButton;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.alibaba.fastjson.JSON.parseObject;

public class PatternAnalysisPage3Activity extends AppCompatActivity {

    private ChartButton p3_chartButton;
    private PieChart p3_PieChart;
    private TextView p3_unfinished;

    private boolean weekDay = true;

    private int[] colors=new int[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_analysis_page3);

        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        p3_chartButton = (ChartButton) findViewById(R.id.p3_chartButton);
        p3_PieChart = (PieChart) findViewById(R.id.p3_PieChart);
        p3_unfinished = (TextView) findViewById(R.id.p3_unfinished);


        getData();
        initChart(p3_PieChart);
        //weekday点击事件
        p3_chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weekDay = p3_chartButton.getWeekDay();
                getData();
                initChart(p3_PieChart);
            }
        });
    }

    //从后端获取未完成事件所占比例
    private void getData(){
        JSONObject reportObject = new JSONObject();
        reportObject.put("weekday",weekDay);
        HttpUtil.request("AnalysisServlet?method=GetUnfinishedPercent","post",reportObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    String jsonString = response.body().string();
                    Log.i("jsonString",jsonString);
                    String unfininshedPercent = parseObject(jsonString).getString("unfininshedPercent");
                    p3_unfinished.setText(unfininshedPercent);

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
    //饼状图
    private void initChart(PieChart pieChart){
        String text = p3_unfinished.getText().toString();
        float value=new Float(text.substring(0,text.indexOf("%")))/100;
        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(value,""));
        strings.add(new PieEntry(1-value,""));
        PieDataSet dataSet = new PieDataSet(strings,"");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.chartWhite));
        colors.add(getResources().getColor(R.color.blueBackground));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();

        //实心+去掉description
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setHoleRadius(0f);
        pieChart.setTransparentCircleRadius(0f);
    }
}
