package com.srdp.admin.time_manager.ui;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.Label;
import com.srdp.admin.time_manager.util.DrawAnalysisChartUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL;
import static java.lang.Float.NaN;

public class PatternAnalysisPage5 extends AppCompatActivity {
    private BarChart chart;
    private Map<Float,float[]> Percents=new HashMap<Float, float[]>();
    private Map<Integer,String> legendDdata=new HashMap<Integer, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_analysis_page5);
        chart=findViewById(R.id.StackBarChart);
        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        initData();
        testDrawUtil();
        //testDraw();
    }
    private void testDrawUtil()
    {
        int[] colors={R.color.darkOrange,R.color.orange,R.color.lightOrange};
        DrawAnalysisChartUtil.drawChart(this,R.id.StackBarChartMain,"CompareBarChart",Percents,legendDdata,colors);
    }
    private void initData()
    {
        for(int i=0;i<11;i++)
        {
            float[] every=new float[3];
            if(i%2==0)
        {
            every[0]=(float) 0.1;
            every[1]=(float)0.4;
            every[2]=(float)0.5;
        }
        else
        {
            every[0]=(float)0.3;
            every[1]=(float)0.3;
            every[2]=(float)0.4;
        }
            Percents.put((float)i,every);
        }
        legendDdata.put(R.color.darkOrange,"少于预定时间的比例");
        legendDdata.put(R.color.orange,"等于预定时间的比例");
        legendDdata.put(R.color.lightOrange,"多于预定时间的比例");
    }
    private void testDraw()
    {
        chart.setBackgroundColor(getResources().getColor(R.color.blueBackground));
        chart.setDescription(null);
        //chart.setGridBackgroundColor(getResources().getColor(R.color.blueBackground));
        List<BarEntry> BarEntries = new ArrayList<>();
        for (Map.Entry<Float,float[]> entry : Percents.entrySet()) {
            BarEntries.add(new BarEntry(entry.getKey(),entry.getValue()));
        }
        BarDataSet set = new BarDataSet(BarEntries, "BarDataSet");
        set.setColors(new int[]{R.color.chartGray,R.color.chartGrayLight,R.color.chartWhite},this);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        //setAxis();

        BarData barData=new BarData(set);
        barData.setBarWidth(0.7f);
        barData.setDrawValues(false);
        chart.setData(barData);
        setAxis();
        chart.invalidate();
    }
    private void setAxis()
        {
        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        XAxis xAxis=chart.getXAxis();

        leftAxis.setLabelCount(10, false);
        leftAxis.setSpaceTop(5f);
        leftAxis.setSpaceBottom(0f);


        //不画右边的y轴
        rightAxis.setEnabled(false);

        //左边y轴画0线
        //leftAxis.setDrawZeroLine(true);


        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(10);

        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextSize(12f);

        leftAxis.setAxisLineWidth(2f);
        xAxis.setAxisLineWidth(2f);

        leftAxis.setGridLineWidth(1f);

        //设置图例
        Legend l = chart.getLegend();

            List<LegendEntry> le=new ArrayList<LegendEntry>();
            le.add(new LegendEntry("少于预定时间的比例", Legend.LegendForm.SQUARE,10f,NaN,null,getResources().getColor(R.color.chartWhite)));
            le.add(new LegendEntry("等于预定时间的比例", Legend.LegendForm.SQUARE,10f,NaN,null,getResources().getColor(R.color.chartGrayLight)));
            le.add(new LegendEntry("多于预定时间的比例", Legend.LegendForm.SQUARE,10f,NaN,null,getResources().getColor(R.color.chartGray)));
            l.setCustom(le);

            l.setTextSize(12f);
            l.setTextColor(Color.WHITE);
            //l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
            l.setOrientation(VERTICAL);
            //设置图例位置坐上
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setDrawInside(true);

        }
}
