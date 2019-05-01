package com.srdp.admin.time_manager.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.AnalysisXAxisFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL;
import static java.lang.Float.NaN;

/**
 * Created by admin on 2018/8/9.
 */

public class DrawAnalysisChartUtil {
    //设置分段柱状图的数据
    public static void drawChart(Activity activity,int pid,String type,Map<Float,float[]> Percents,Map<Integer,String> legendData,int[] colors)
    {

        BarChart chart=basicSet(activity,pid);
        //获取图例的容器
        LinearLayout wholeChart=(LinearLayout)activity.findViewById(pid);
        FrameLayout chartInfor=(FrameLayout) wholeChart.getChildAt(0);
        LinearLayout legendContain=(LinearLayout)chartInfor.getChildAt(0);
        //设置图例
        setLegend(activity,legendContain,legendData);
        //给柱状图的set赋值，赋颜色
        BarDataSet set=setBarSet(activity,chart,Percents,colors,type);
        //设置轴
        setAxis(chart,type);
        //更新数据
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
    public static void drawChart(Activity activity,int pid,String type,Map<Float,float[]> Percents,int[] colors)
    {
        BarChart chart=basicSet(activity,pid);
        //给柱状图的set赋值，赋颜色
        BarDataSet set=setBarSet(activity,chart,Percents,colors,type);
        //设置轴
        setAxis(chart,type);
        //更新数据
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
    private static void setLegend(Activity activity,LinearLayout legendContain,Map<Integer,String> legendData)
    {
        //把图例的布局加到图例的父布局里面
        LayoutInflater inflater = LayoutInflater.from(activity);
        //最后一个参数为true，表示默认把第一个参数布局加入到第二个参数布局里
        //清除所有图例
        legendContain.removeAllViews();
        for (Map.Entry<Integer,String> entry : legendData.entrySet()) {
            LinearLayout everyLegend=(LinearLayout) inflater.inflate(R.layout.legend,legendContain,false);
            //修改图例的颜色和文字
            View legendImage=(View)everyLegend.getChildAt(0);
            TextView legendText=(TextView) everyLegend.getChildAt(1);
            legendImage.setBackgroundColor(activity.getResources().getColor(entry.getKey()));
            legendText.setText(entry.getValue());
            legendContain.addView(everyLegend);
        }
    }
    //基础设置，被setData函数调用
    /*public static BarChart basicSet(Activity activity, BarChart chart)
    {
        //设置图表的背景颜色
        chart.setBackgroundColor(activity.getResources().getColor(R.color.blueBackground));
        //设置图表描述为空
        chart.setDescription(null);
        return chart;
    }*/
    public static BarChart basicSet(Activity activity,int pid)
    {
        LinearLayout wholeChart=(LinearLayout)activity.findViewById(pid);
        //获取初始设置完的图表
        BarChart chart=(BarChart)wholeChart.getChildAt(1);
        //设置图表的背景颜色
        //chart.setBackgroundColor(activity.getResources().getColor(R.color.blueBackground));
        //设置图表描述为空
        chart.setDescription(null);
        chart.setDoubleTapToZoomEnabled(false);
        //禁止拖拽
        chart.setDragEnabled(false);
        //X轴或Y轴禁止缩放
        chart.setScaleXEnabled(false);
        chart.setScaleYEnabled(false);
        chart.setScaleEnabled(false);
        //禁止所有事件
        chart.setTouchEnabled(false);
        return chart;
    }
    public static BarDataSet BasicsetBarSet(Activity activity,BarChart chart,List<BarEntry> BarEntries,int[] colors,String type)
    {
        BarDataSet set = new BarDataSet(BarEntries, "BarDataSet");
        //设置柱状图每一段的颜色
        set.setColors(colors,activity);
        //设置数据只显示在左边的y轴上（不显示在右边的y轴上）
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        //设置柱状图每条柱的属性
        BarData barData=null;
        if(type.equals("CompareBarChart")){
            //设置每条柱的宽度
            barData=new BarData(set);
            barData.setBarWidth(0.7f);
            //不在柱上画数据
            //barData.setDrawValues(false);
        }
        else if(type.equals("DensityBarChart")){
            /*set.setDrawValues(true);
            set.setValueTextColor(R.color.chartGrayLight);
            set.setValueTextSize(14f);*/
            barData=new BarData(set);
            //设置每条柱的宽度
            barData.setBarWidth(0.5f);
            barData.setValueTextColor(R.color.chartWhite);
            barData.setValueTextSize(14f);
            barData.setDrawValues(true);
        }
        chart.setData(barData);
        return set;
    }
    public static BarDataSet setBarSet(Activity activity,BarChart chart,Map<Float,float[]> Percents,int[] colors,String type)
    {
        List<BarEntry> BarEntries = new ArrayList<>();
        for (Map.Entry<Float,float[]> entry : Percents.entrySet()) {
                BarEntries.add(new BarEntry(entry.getKey(),entry.getValue()));
        }
        BarDataSet set=BasicsetBarSet(activity,chart,BarEntries,colors,type);
        return  set;
    }

    //设置x、y轴，被setData函数调用
    private static void setAxis(BarChart chart,String type)
    {
        //分别获取左右y轴，x轴
        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        XAxis xAxis=chart.getXAxis();
        //如果是与日程表比较的图表绘制左y轴，不然不绘制左y轴
        if(type.equals("CompareBarChart")) {
            //设置左y轴从0-100总共10个刻度
            leftAxis.setLabelCount(10, false);
            //设置左y轴在最高刻度上还有5个单位
            leftAxis.setSpaceTop(5f);
            //设置左y轴在最高刻度上有0个单位
            leftAxis.setSpaceBottom(0f);
            //设置y轴上字的颜色
            leftAxis.setTextColor(Color.WHITE);
            leftAxis.setAxisLineColor(Color.WHITE);
            leftAxis.setTextSize(12f);
            //设置y轴宽度
            leftAxis.setAxisLineWidth(2f);
            //设置y轴上网格线的宽度
            leftAxis.setGridLineWidth(1f);
            leftAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return ((int) (value * 100)) + "%";
                }
            });
        }
        else if(type.equals("DensityBarChart")) {
            leftAxis.setSpaceBottom(0f);
            leftAxis.setEnabled(false);
        }
        //不画右边的y轴
        rightAxis.setEnabled(false);

        //设置x轴不画网格线
        xAxis.setDrawGridLines(false);
        //设置x轴的位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14f);

        //如果是与日程表比较的图表设置x轴0-10 11刻度，不然设置x轴0-6 7个刻度
        if(type.equals("CompareBarChart")) {
            //设置x轴10个刻度
            xAxis.setLabelCount(10);
            String[] values = new String[]{"课业","自学","社团","娱乐","交通","运动","生活","吃饭","休息","睡觉","其他"};
            xAxis.setValueFormatter(new AnalysisXAxisFormatter(values));
            xAxis.setLabelRotationAngle(-90);
        }
        else if(type.equals("DensityBarChart")){
            xAxis.setLabelCount(6);
            String[] values = new String[]{"凌晨","早晨","上午","中午","下午","晚上","深夜"};
            xAxis.setValueFormatter(new AnalysisXAxisFormatter(values));
        }

        //设置x轴上字和轴的颜色
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);
        //设置x轴宽度
        xAxis.setAxisLineWidth(2f);

        //设置图例不可见
        Legend l = chart.getLegend();
        l.setEnabled(false);
    }
}
