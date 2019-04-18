package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.srdp.admin.time_manager.R;
import com.github.mikephil.charting.components.Legend;
import com.srdp.admin.time_manager.util.HttpUtil;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;

import okhttp3.Call;
import okhttp3.Response;

import static com.alibaba.fastjson.JSON.parseObject;


public class ReportFormActivity extends AppCompatActivity {

    private String[] weekday={"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};

    private TableLayout rep_line_table;//折线图表格

    private PieChart rep_piechart;//饼状图时间分配表
    private PieChart rep_week_piechart;//饼状图日程表
    private LineChart rep_linechart;//切线图

    private TextView rep_week;//第x周
    private TextView rep_date;//日期

    private Button pic_trans;//饼状图折线图切换按钮
    private Button week_trans;//周报表日报表切换按钮

    private String week;//获取的第x周
    private int flag_pic = 1;//切换标志

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        rep_line_table = (TableLayout)findViewById(R.id.rep_line_table);
        rep_line_table.setStretchAllColumns(true);
        rep_piechart = (PieChart) findViewById(R.id.rep_piechart);
        rep_week_piechart = (PieChart)findViewById(R.id.rep_week_piechart);
        rep_linechart = (LineChart) findViewById(R.id.rep_lingchart);
        rep_week = (TextView) findViewById(R.id.rep_week);
        rep_date = (TextView) findViewById(R.id.rep_date);
        pic_trans = (Button) findViewById(R.id.pic_trans);
        week_trans = (Button) findViewById(R.id.week_trans);

        //切换图表事件
        pic_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag_pic == 1){//转换到切线图
                    flag_pic = 0;
                    rep_linechart.setVisibility(View.VISIBLE);
                    rep_piechart.setVisibility(View.GONE);
                    pic_trans.setBackgroundResource(R.drawable.line_chart_logo);
                    initLineChart(rep_linechart);
                }
                else {//转换到饼状图
                    flag_pic = 1;
                    rep_piechart.setVisibility(View.VISIBLE);
                    rep_linechart.setVisibility(View.GONE);
                    pic_trans.setBackgroundResource(R.drawable.pie_chart_logo);
                    PieData mPieData = getPieData(6, 100);
                    showChart(rep_piechart, mPieData);
                }
            }
        });

        //切换到日报表
        week_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                week_trans.setBackgroundResource(R.drawable.day_logo);
                String today = rep_date.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("date", today);
                intent.setClass(ReportFormActivity.this, ReportFormDayActivity.class);//从哪里跳到哪里
                ReportFormActivity.this.startActivity(intent);
            }
        });
    }

    /*
     *  折线图的实现
     */
    private void initLineChart(LineChart lineChart){
        //设置显示属性
        Description description =new Description();
        //description.setText("测试图表");
        description.setTextColor(Color.RED);
        description.setTextSize(20);
        lineChart.setDescription(description);//设置图表描述信息
        lineChart.setNoDataText("暂无数据");//没有数据时显示的文字
        lineChart.setNoDataTextColor(Color.RED);//没有数据时显示文字的颜色
        lineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        lineChart.setDrawBorders(false);//禁止绘制图表边框的线
        //lineChart.setBorderColor(); //设置 chart 边框线的颜色。
        //lineChart.setBorderWidth(); //设置 chart 边界线的宽度，单位 dp。
        //lineChart.setLogEnabled(true);//打印日志
        //lineChart.notifyDataSetChanged();//刷新数据
        //lineChart.invalidate();//重绘

        //绑定数据
        final ArrayList<Entry> lineData = new ArrayList<>();

        //从日历页面获取日期数据
        Intent intent=getIntent();
        String today=intent.getStringExtra("today");
        rep_date.setText(today);
        //TODO 获取标签
        int labelId = 1;

        //从后端获取数据
        JSONObject reportObject = new JSONObject();
        reportObject.put("date",today);
        reportObject.put("labelId",labelId);

        HttpUtil.request("SheetServlet?method=GetWeeklyChange","post",reportObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    //获取服务器端响应数据
                    String jsonString = response.body().string();
                    Log.i("jsonString",jsonString);
                    //获得第几周
                    week = parseObject(jsonString).getString("week");
                    rep_week.setText(week);
                    //获取duration
                    JSONArray durationArray = parseObject(jsonString).getJSONArray("durationArray");
                    Log.i("durationArray",durationArray.toString());

                    rep_line_table.removeAllViewsInLayout();//清空表格
                    for(int i=0;i<durationArray.size();i++){
                        String durationString = durationArray.getString(i);
                        float duration = Float.parseFloat(durationString.substring(0,1))*60 + Float.parseFloat(durationString.substring(3,4));
                        lineData.add(new Entry(i+1, duration));
                        /*表格部分*/
                        TableRow tableRow = new TableRow(rep_line_table.getContext());
                        if(i%2==0) tableRow.setBackgroundResource(R.color.tableBackgroundWhite);
                        else tableRow.setBackgroundResource(R.color.tableBackgroundPink);
                        tableRow.setPadding(5,5,5,5);
                        //周几
                        TextView day = new TextView(tableRow.getContext());
                        ViewGroup.LayoutParams text_params = day.getLayoutParams();
                        text_params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        text_params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                        day.setGravity(Gravity.CENTER);
                        day.setTextColor(Color.BLACK);
                        day.setText(weekday[i]);
                        //时长
                        TextView time = new TextView(tableRow.getContext());
                        ViewGroup.LayoutParams time_params = day.getLayoutParams();
                        time_params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        time_params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                        time.setGravity(Gravity.CENTER);
                        time.setTextColor(Color.BLACK);
                        time.setText(durationString.substring(0,1)+"时"+durationString.substring(3,4)+"分");
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

        //LineDataSet每一个对象就是一条连接线
        LineDataSet set;
        set = new LineDataSet(lineData,"时长");
        set.setColor(Color.BLACK);
        set.setCircleColor(Color.BLACK);
        set.setLineWidth(1f);//设置线宽
        set.setCircleRadius(3f);//设置焦点圆心的大小
        set.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
        set.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
        set.setHighlightEnabled(true);//是否禁用点击高亮线
        set.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色
        set.setValueTextSize(9f);//设置显示值的文字大小
        set.setDrawFilled(false);//设置禁用范围背景填充

        //保存LineDataSet集合
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set); // add the datasets
        //创建LineData对象 属于LineChart折线图的数据集合
        LineData data = new LineData(dataSets);
        // 添加到图表中
        lineChart.setData(data);
        //绘制图表
        lineChart.invalidate();

    }
    /*private void initLineChart(final List<Integer> list){
        //显示边界
        rep_linechart.setDrawBorders(false);
        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
        {
            entries.add(new Entry(i, (float) list.get(i)));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        //线颜色
        lineDataSet.setColor(Color.parseColor("#F15A4A"));
        //线宽度
        lineDataSet.setLineWidth(1.6f);
        //不显示圆点
        lineDataSet.setDrawCircles(false);
        //线条平滑
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        //设置折线图填充
//        lineDataSet.setDrawFilled(true);
        LineData data = new LineData(lineDataSet);
        //无数据时显示的文字
        rep_linechart.setNoDataText("暂无数据");
        //折线图不显示数值
        data.setDrawValues(false);
        //得到X轴
        XAxis xAxis = rep_linechart.getXAxis();
        //设置X轴的位置（默认在上方)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置X轴坐标之间的最小间隔
        xAxis.setGranularity(1f);
        //设置X轴的刻度数量，第二个参数为true,将会画出明确数量（带有小数点），但是可能值导致不均匀，默认（6，false）
        xAxis.setLabelCount(list.size() / 6, false);
        //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) list.size());
        //不显示网格线
        xAxis.setDrawGridLines(false);
        // 标签倾斜
        xAxis.setLabelRotationAngle(45);
        //设置X轴值为字符串
        xAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {
                int IValue = (int) value;
                CharSequence format = DateFormat.format("MM/dd",
                        System.currentTimeMillis()-(long)(list.size()-IValue)*24*60*60*1000);
                return format.toString();
            }
        });
        //得到Y轴
        YAxis yAxis = rep_linechart.getAxisLeft();
        YAxis rightYAxis = rep_linechart.getAxisRight();
        //设置Y轴是否显示
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        //设置y轴坐标之间的最小间隔
        //不显示网格线
        yAxis.setDrawGridLines(false);
        //设置Y轴坐标之间的最小间隔
        yAxis.setGranularity(1);
        //设置y轴的刻度数量
        //+2：最大值n就有n+1个刻度，在加上y轴多一个单位长度，为了好看，so+2
        yAxis.setLabelCount(Collections.max(list) + 2, false);
        //设置从Y轴值
        yAxis.setAxisMinimum(0f);
        //+1:y轴多一个单位长度，为了好看
        yAxis.setAxisMaximum(Collections.max(list) + 1);

        //y轴
        yAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {
                int IValue = (int) value;
                return String.valueOf(IValue);
            }
        });
        //图例：得到Lengend
        Legend legend = rep_linechart.getLegend();
        //隐藏Lengend
        legend.setEnabled(false);
        //隐藏描述
        Description description = new Description();
        description.setEnabled(false);
        rep_linechart.setDescription(description);
        //折线图点的标记
        //MyMarkerView mv = new MyMarkerView(this);
        //rep_linechart.setMarker(mv);
        //设置数据
        rep_linechart.setData(data);
        //图标刷新
        rep_linechart.invalidate();
    }*/




    /*
     *  饼状图的实现
     */
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
        // mChart.setOnChartValueSelectedListener(this);
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
    private PieData getPieData(int count, float range) {

        final ArrayList<String> xValues = new ArrayList<>();  //xVals用来表示每个饼块上的内容
        final ArrayList<PieEntry> yValues = new ArrayList<>();  //yVals用来表示封装每个饼块的实际数据

        //从日历页面获取日期数据
        Intent intent=getIntent();
        String today=intent.getStringExtra("today");

        //从后端获取数据
        JSONObject reportObject = new JSONObject();
        reportObject.put("date",today);

        HttpUtil.request("SheetServlet?method=GetWeeklySheet","post",reportObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    //获取服务器端响应数据
                    String jsonString = response.body().string();
                    Log.i("jsonString",jsonString);
                    //获得第几周
                    week = parseObject(jsonString).getString("week");
                    rep_week.setText(week);
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
