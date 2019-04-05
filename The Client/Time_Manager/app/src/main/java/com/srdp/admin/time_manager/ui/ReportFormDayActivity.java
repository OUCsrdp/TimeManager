package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.TimeSharing;
import com.srdp.admin.time_manager.util.HttpUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

import static com.alibaba.fastjson.JSON.parseObject;


public class ReportFormDayActivity extends AppCompatActivity {

    private PieChart rep_day_piechart;//日程表
    private PieChart rep_plan_piechart;//时间分配表
    private TextView report_weekday;//星期x
    private TextView report_date;//日期

    private Button week_trans;//周报表日报表切换按钮

    private String weekday;//获取的星期x

    private int count;//饼图数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form_day);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        rep_day_piechart = (PieChart) findViewById(R.id.rep_day_piechart);
        PieData dayPieData = getPieData(6, 100,1);
        showChart(rep_day_piechart, dayPieData);

        rep_plan_piechart = (PieChart) findViewById(R.id.rep_plan_piechart);
        PieData planPieData = getPieData(6, 100,2);
        showChart(rep_plan_piechart, planPieData);

        report_weekday = (TextView) findViewById(R.id.report_weekday);
        report_date = (TextView) findViewById(R.id.report_date);

        week_trans = (Button) findViewById(R.id.week_trans);

        //切换到日报表
        week_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                week_trans.setBackgroundResource(R.drawable.week_logo);
                String today = report_date.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("date", today);
                intent.setClass(ReportFormDayActivity.this, ReportFormActivity.class);//从哪里跳到哪里
                ReportFormDayActivity.this.startActivity(intent);
            }
        });
    }

    private void showChart(PieChart pieChart, PieData pieData) {
        //pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(10f);  //半径
        pieChart.setTransparentCircleRadius(14f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        //pieChart.setDescription("测试饼状图");

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText(" ");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        //mLegend.setPosition(LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     *
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range, final int flag) {

        //数据显示操作
        final ArrayList<String> xValues = new ArrayList<>();  //xVals用来表示每个饼块上的内容
        final ArrayList<PieEntry> yValues = new ArrayList<>();  //yVals用来表示封装每个饼块的实际数据

        //从日历页面获取日期数据
        Intent intent=getIntent();
        String today=intent.getStringExtra("today");
        report_date.setText(today);

        //从后端获取数据
        JSONObject reportObject = new JSONObject();
        reportObject.put("date",today);

        HttpUtil.request("SheetServlet?method=GetDailySheet","post",reportObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    //获取服务器端响应数据
                    String jsonString = response.body().string();
                    Log.i("jsonString",jsonString);
                    //获得星期x
                    weekday = parseObject(jsonString).getString("weekday");
                    report_weekday.setText(weekday);
                    if(flag==2){
                        //时间分配表
                        JSONArray timesharing = parseObject(jsonString).getJSONArray("TimeSharing");
                        Log.i("timesharing",timesharing.toString());

                        for(int i=0;i<timesharing.size();i++){
                            JSONObject resJsonItem = timesharing.getJSONObject(i);
                            int labelid = resJsonItem.getIntValue("labelid");
                            float percent = resJsonItem.getFloatValue("percent");//所占时间比，以浮点数表示
                            String duration = resJsonItem.getString("duration");//该天所有该标签的事件总时间
                            xValues.add("Quarterly" +labelid);
                            yValues.add(new PieEntry(percent, duration));
                            //xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
                        }
                    }
                    else if(flag==1){
                        //日程表
                        JSONArray Schedule = parseObject(jsonString).getJSONArray("Schedule");
                        Log.i("Schedule",Schedule.toString());

                        for(int i=0;i<Schedule.size();i++){
                            JSONObject resJsonItem = Schedule.getJSONObject(i);
                            int labelid = resJsonItem.getIntValue("labelid");
                            float percent = resJsonItem.getFloatValue("percent");//所占时间比，以浮点数表示
                            String duration = resJsonItem.getString("duration");//该天所有该标签的事件总时间
                        }
                    }



                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                //createFail();
            }
        });


//        for (int i = 0; i < count; i++) {
//            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
//        }



        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
//        float quarterly1 = 13;
//        float quarterly2 = 21;
//        float quarterly3 = 11;
//        float quarterly4 = 14;
//        float quarterly5 = 15;
//        float quarterly6 = 21;
//        float quarterly7 = 23;
//
//        yValues.add(new PieEntry(quarterly1, 0));
//        yValues.add(new PieEntry(quarterly2, 1));
//        yValues.add(new PieEntry(quarterly3, 2));
//        yValues.add(new PieEntry(quarterly4, 3));
//        yValues.add(new PieEntry(quarterly5, 4));
//        yValues.add(new PieEntry(quarterly6, 5));
//        yValues.add(new PieEntry(quarterly7, 6));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, " "/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
        colors.add(Color.rgb(127, 235, 230));
        colors.add(Color.rgb(247, 35, 20));
        colors.add(Color.rgb(117, 85, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(pieDataSet);

        return pieData;
    }
}
