package com.srdp.admin.time_manager.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.util.DrawAnalysisChartUtil;

import java.util.HashMap;
import java.util.Map;


public class PatternAnalysisPage4 extends AppCompatActivity {
    private BarChart chart;
    private Map<Float,float[]> Percents=new HashMap<Float, float[]>();
    private Map<Integer,String> legendDdata=new HashMap<Integer, String>();
    private int[] colors=new int[11];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_analysis_page4);
        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        initData();
        drawChart();
    }
    private void initData()
    {
        for(int i=0;i<11;i++)
        {
            float[] every=new float[1];
            if(i%3==0)
            {
                every[0]=30;
            }
            else if(i%3==1)
            {
                every[0]=70;
            }
            else
            {
                every[0]=40;
            }
            Percents.put((float)i,every);
        }
        legendDdata.put(R.color.chartWhite,"节约比例");
        legendDdata.put(R.color.chartGray,"超时比例");
        for(int i=0;i<11;i++)
        {
            if(i%2==0)
                colors[i]=R.color.chartWhite;
            else
                colors[i]=R.color.chartGray;
        }
    }
    private void drawChart()
    {
        DrawAnalysisChartUtil.drawChart(this,R.id.BarChartMain,"CompareBarChart",Percents,legendDdata,colors);
    }
}
