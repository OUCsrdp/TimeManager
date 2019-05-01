package com.srdp.admin.time_manager.ui;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.srdp.admin.time_manager.util.HttpUtil;
import com.srdp.admin.time_manager.widget.AnalysisChart.ChartButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL;
import static java.lang.Float.NaN;

public class PatternAnalysisPage5 extends AppCompatActivity {
    private BarChart chart;
    private Map<Float,float[]> Percents=new HashMap<Float, float[]>();
    private int[] colors={R.color.darkOrange,R.color.orange,R.color.lightOrange};
    private Map<Integer,String> legendDdata=new HashMap<Integer, String>();
    private boolean weekday=true;
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
        //设置统计图颜色
        //final int[] colors={R.color.darkOrange,R.color.orange,R.color.lightOrange};
        //进入时绘制统计图
        getDataAndDraw();
        //使用接口回调在修改weekday的时候重新请求 重新绘制
        ChartButton chartButton=findViewById(R.id.P5ChartButtion);
        chartButton.setonClick(new ChartButton.TransforWeekday() {
            @Override
            public void onTransforWeekday(boolean isWeekday) {
                weekday=isWeekday;
                if(!weekday)
                Toast.makeText(PatternAnalysisPage5.this,"今天休息哟！",Toast.LENGTH_LONG).show();
                //切换工作日休息日时重新绘制统计图
                getDataAndDraw();
            }
        });
        /*initData();
        testDrawUtil();*/
        //testDraw();
    }
    private void getDataAndDraw()
    {
        //设置图例
        legendDdata.put(colors[0],"少于预定时间的比例");
        legendDdata.put(colors[1],"等于预定时间的比例");
        legendDdata.put(colors[2],"多于预定时间的比例");
        //请求获得统计图数据
        JSONObject reqJson=new JSONObject();
        reqJson.put("weekday",weekday);
        reqJson.put("type","detailedAnalysis");
        HttpUtil.request("AnalysisServlet?method=GetChart","post",reqJson,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    String ress=response.body().string();
                    JSONObject resJson = JSONObject.parseObject(ress);
                    JSONArray chartInfor=resJson.getJSONArray("chartInfor");
                    for(int i=0;i<chartInfor.size();i++)
                    {
                        //设置每个柱体的高度
                        float[] every=new float[3];
                        JSONObject nowData=chartInfor.getJSONObject(i);
                        JSONArray percents=nowData.getJSONArray("percents");
                        for(int j =0;j<3;j++)
                            every[j]=percents.getFloatValue(j)/100;
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
    private void drawChart()
    {
        //Log.i("A5Percents1",Percents.get(0).toString());
       //Log.i("A5Percents2",Percents.get(1).toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DrawAnalysisChartUtil.drawChart(PatternAnalysisPage5.this,R.id.StackBarChartMain,"CompareBarChart",Percents,legendDdata,colors);
            }
        });
        //DrawAnalysisChartUtil.drawChart(this,R.id.StackBarChartMain,"CompareBarChart",Percents,legendDdata,colors);
    }
    /*private void testDrawUtil()
    {
        int[] colors={R.color.darkOrange,R.color.orange,R.color.lightOrange};
        DrawAnalysisChartUtil.drawChart(this,R.id.StackBarChartMain,"CompareBarChart",Percents,legendDdata,colors);
    }*/
    /*private void initData()
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
    }*/
    /*private void testDraw()
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
    }*/
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
