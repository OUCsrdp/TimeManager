package com.example.lizliz.timemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.graphics.Color;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.formatter.PercentFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.github.mikephil.charting.components.Legend;

import java.util.ArrayList;


public class ReportFormActivity extends AppCompatActivity {

    private PieChart rep_piechart;
//    private TextView mT1;
//    private TextView mT2;
//    private TextView mT3;
//    private TextView mT4;
//    private TextView mT5;
//    private TextView mT6;
//    private TextView mT7;
//    private ArrayList<String> xContents;
//    private ArrayList arrayList;
//    private ArrayList<Entry> yContent;
//    private ArrayList<Integer> colors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form);

        rep_piechart = (PieChart) findViewById(R.id.rep_piechart);
        rep_piechart.setUsePercentValues(true);//设置value是否用显示百分数,默认为false
        //rep_piechart.setExtraOffsets(5, 5, 5, 5);//设置饼状图距离上下左右的偏移量
        rep_piechart.setDrawHoleEnabled(false);//实心圆
        rep_piechart.setHoleRadius(0);//实心圆
//        initView();
//        PieData mPieData = getPieData(4, 100);
//        showChart(rep_piechart, mPieData);

    }


//    private void initView(){
//        mT1 = (TextView) findViewById(R.id.time_mon);
//        mT2 = (TextView) findViewById(R.id.time_tus);
//        mT3 = (TextView) findViewById(R.id.time_wed);
//        mT4 = (TextView) findViewById(R.id.time_thus);
//        mT5 = (TextView) findViewById(R.id.time_fri);
//        mT6 = (TextView) findViewById(R.id.time_sat);
//        mT7 = (TextView) findViewById(R.id.time_sun);
//
//    }
//
//    private void showChart(PieChart pieChart, PieData pieData ){
//
//
//        rep_piechart.setUsePercentValues(true);//设置value是否用显示百分数,默认为false
//        rep_piechart.setExtraOffsets(5, 5, 5, 5);//设置饼状图距离上下左右的偏移量
//        rep_piechart.setDrawHoleEnabled(false);//实心圆
//        rep_piechart.setHoleRadius(0);//实心圆
//
//        pieChart.setData(pieData);
//
//        Legend mLegend = pieChart.getLegend();//设置比例图
//        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        mLegend.setForm(Legend.LegendForm.LINE);
//
//
//    }
//
//
//    private PieData getPieData(int count, float range){
//
//        initData();
//
//        PieDataSet  pieDataSet= new PieDataSet(yContent,null);
//        //设置饼状图之间的距离
//        pieDataSet.setSliceSpace(0f);
//        //设置饼状图之间的颜色
//        pieDataSet.setColors(colors);
//
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        float px = 5 * (metrics.densityDpi / 160f);
//        pieDataSet.setSelectionShift(px); // 选中态多出的长度
//        PieData pieData = new PieData(xContents, pieDataSet);
//
//        return pieData;
//
//    }
//
//    private void initData(){
//        // 饼图颜色
//        colors = new ArrayList<Integer>();
//
//        colors.add(Color.rgb(205,205,205));
//        colors.add(Color.rgb(114,188,223));
//        colors.add(Color.rgb(255,123,124));
//        colors.add(Color.rgb(57, 135, 200));
//        colors.add(Color.rgb(57, 135, 20));
//        colors.add(Color.rgb(77, 105, 20));
//
//        /**展示内容*/
//        xContents = new ArrayList<String>();
//        xContents.add("汽车");
//        xContents.add("美容");
//        xContents.add("小保养");
//        xContents.add("大保养");
//        xContents.add("喷漆");
//        xContents.add("机修");
//    }


    public void setData(TreeMap<String, Float> data) {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        int i = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            // entry的输出结果如key0=value0等
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            float value = (float) entry.getValue();
            xVals.add(key);
            yVals1.add(new Entry(value, i++));
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        // 设置饼图区块之间的距离
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // 添加颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        // dataSet.setSelectionShift(0f);

        PieData data1 = new PieData(xVals, dataSet);
        data1.setValueFormatter(new PercentFormatter());
        data1.setValueTextSize(10f);
        data1.setValueTextColor(Color.BLACK);
        rep_piechart.setData(data1);

        // undo all highlights
        rep_piechart.highlightValues(null);

        rep_piechart.invalidate();
    }

}
