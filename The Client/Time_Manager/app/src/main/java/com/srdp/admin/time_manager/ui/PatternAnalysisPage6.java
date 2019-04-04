package com.srdp.admin.time_manager.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.util.DrawAnalysisChartUtil;

import java.util.HashMap;
import java.util.Map;

public class PatternAnalysisPage6 extends AppCompatActivity {
    private BarChart chart;
    private Map<Float,float[]> Percents=new HashMap<Float, float[]>();
    private int[] colors=new int[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_analysis_page6);
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
    private void drawChart()
    {
        DrawAnalysisChartUtil.drawChart(this,R.id.DensityChartMain,"DensityBarChart",Percents,colors);
    }
}
