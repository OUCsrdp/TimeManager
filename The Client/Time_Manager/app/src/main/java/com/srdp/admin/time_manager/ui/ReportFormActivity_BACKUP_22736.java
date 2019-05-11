package com.srdp.admin.time_manager.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.srdp.admin.time_manager.util.LabelUtil;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;

import okhttp3.Call;
import okhttp3.Response;

import static com.alibaba.fastjson.JSON.parseObject;


public class ReportFormActivity extends AppCompatActivity {


    private LabelUtil labelUtil;

    private String[] weekday={"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};

    private Context context = this;

    private LinearLayout label_wrap;
    private ImageView label_img;
    private TextView label_name;


    private TableLayout rep_line_table;//折线图表格+饼图时间分配表表格
    private TableLayout rep_pie_table;//饼图日程表表格
    private TableLayout pie_table;

    private PieChart rep_piechart;//饼状图时间分配表
    private PieChart rep_week_piechart;//饼状图日程表
    private LineChart rep_linechart;//切线图

    private TextView rep_week;//第x周
    private TextView rep_date;//日期
    private TextView plan_time_all;//分配表+折线图总时间
    private TextView time_all;//日程表总时间

    private Button pic_trans;//饼状图折线图切换按钮
    private Button week_trans;//周报表日报表切换按钮

    private String week;//获取的第x周
    private int flag_pic = 1;//切换标志
    private String jsonString="";
    private int labelId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        Log.i("info","成功进入周报表页！");

        label_wrap = (LinearLayout)findViewById(R.id.label_wrap);
        label_img = (ImageView) findViewById(R.id.label_img);
        label_name = (TextView)findViewById(R.id.label_name);

        rep_line_table = (TableLayout)findViewById(R.id.rep_line_table);
        rep_line_table.setStretchAllColumns(true);
        rep_pie_table = (TableLayout)findViewById(R.id.rep_pie_table);
        rep_pie_table.setStretchAllColumns(true);
        pie_table = (TableLayout)findViewById(R.id.pie_table);
        rep_piechart = (PieChart) findViewById(R.id.rep_piechart);
        rep_week_piechart = (PieChart)findViewById(R.id.rep_week_piechart);
        rep_linechart = (LineChart) findViewById(R.id.rep_lingchart);
        rep_week = (TextView) findViewById(R.id.rep_week);
        rep_date = (TextView) findViewById(R.id.rep_date);
        plan_time_all = (TextView) findViewById(R.id.plan_time_all);
        time_all = (TextView) findViewById(R.id.time_all);
        pic_trans = (Button) findViewById(R.id.pic_trans);
        week_trans = (Button) findViewById(R.id.week_trans);

        getPieData(6,100);
        //PieData planPieData = getPieData(6, 100);
        //showChart(rep_piechart, planPieData);
        //PieData weekPieData = getPieData(6, 100);
        //showChart(rep_week_piechart,weekPieData);

        //切换图表事件
        pic_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag_pic == 1){//转换到折线图
                    flag_pic = 0;
                    label_wrap.setVisibility(View.VISIBLE);
                    rep_linechart.setVisibility(View.VISIBLE);
                    rep_piechart.setVisibility(View.GONE);
                    rep_week_piechart.setVisibility(View.GONE);
                    rep_pie_table.setVisibility(View.GONE);
                    pie_table.setVisibility(View.GONE);
                    pic_trans.setBackgroundResource(R.drawable.line_chart_logo);
                    //initLineChart(rep_linechart);
                    getLineData();
                }
                else {//转换到饼状图
                    flag_pic = 1;
                    label_wrap.setVisibility(View.GONE);
                    rep_piechart.setVisibility(View.VISIBLE);
                    rep_week_piechart.setVisibility(View.VISIBLE);
                    rep_pie_table.setVisibility(View.VISIBLE);
                    pie_table.setVisibility(View.VISIBLE);
                    rep_linechart.setVisibility(View.VISIBLE);
                    pic_trans.setBackgroundResource(R.drawable.pie_chart_logo);
                    getPieData(6,100);
//                    PieData planPieData = getPieData(6, 100,2);
//                    showChart(rep_piechart, planPieData);
//                    PieData weekPieData = getPieData(6, 100,1);
//                    showChart(rep_week_piechart,weekPieData);
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


        //折线图时切换标签
        label_wrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击按钮弹框
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.label_choose, null);
                dialog.setView(dialogView);
                dialog.show();
                Window window = dialog.getWindow();
                LinearLayout label_learning = (LinearLayout)window.findViewById(R.id.label_learning);
                LinearLayout label_self_learning = (LinearLayout)window.findViewById(R.id.label_self_learning);
                LinearLayout label_club = (LinearLayout)window.findViewById(R.id.label_club);
                LinearLayout label_entertainment = (LinearLayout)window.findViewById(R.id.label_entertainment);
                LinearLayout label_transport = (LinearLayout)window.findViewById(R.id.label_transport);
                LinearLayout label_eat = (LinearLayout)window.findViewById(R.id.label_eat);
                LinearLayout label_rest = (LinearLayout)window.findViewById(R.id.label_rest);
                LinearLayout label_sleep = (LinearLayout)window.findViewById(R.id.label_sleep);
                LinearLayout label_life = (LinearLayout)window.findViewById(R.id.label_life);
                LinearLayout label_sports = (LinearLayout)window.findViewById(R.id.label_sports);
                LinearLayout label_other = (LinearLayout)window.findViewById(R.id.label_other);
                label_learning.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("学习");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_learning));
                        dialog.dismiss();
                        labelId = 1;
                        getLineData();
                    }
                });
                label_self_learning.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("自学");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_self_learning));
                        dialog.dismiss();
                        labelId = 2;
                        getLineData();
                    }
                });
                label_club.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("社团");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_club));
                        dialog.dismiss();
                        labelId = 3;
                        getLineData();
                    }
                });
                label_entertainment.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("娱乐");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_entertainment));
                        dialog.dismiss();
                        labelId = 4;
                        getLineData();
                    }
                });
                label_transport.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("交通");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_transport));
                        dialog.dismiss();
                        labelId = 5;
                        getLineData();
                    }
                });
                label_eat.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("吃饭");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_eat));
                        dialog.dismiss();
                        labelId = 6;
                        getLineData();
                    }
                });
                label_rest.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("休息");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_rest));
                        dialog.dismiss();
                        labelId = 7;
                        getLineData();
                    }
                });
                label_sleep.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("睡觉");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_sleep));
                        dialog.dismiss();
                        labelId = 8;
                        getLineData();
                    }
                });
                label_life.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("生活");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_life));
                        dialog.dismiss();
                        labelId = 9;
                        getLineData();
                    }
                });
                label_sports.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("运动");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_sports));
                        dialog.dismiss();
                        labelId = 10;
                        getLineData();
                    }
                });
                label_other.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        label_name.setText("其他");
                        label_img.setImageDrawable(getResources().getDrawable(R.drawable.label_other));
                        dialog.dismiss();
                        labelId = 11;
                        getLineData();
                    }
                });



            }



        });
    }




    /*
     *  折线图的实现
     */

    private void appendLineTable(String jsonString,ArrayList<Entry> lineData){

        JSONObject resJson=JSONObject.parseObject(jsonString);
        //获得第几周
        week = resJson.getString("week");
        rep_week.setText(week);
        //获取duration
        JSONArray durationArray = resJson.getJSONArray("durationArray");
        Log.i("durationArray",durationArray.toString());
        int timeAll = 0;

        rep_line_table.removeAllViewsInLayout();//清空表格
        for(int i=0;i<durationArray.size();i++){
            String durationString = durationArray.getString(i);
            timeAll += Integer.parseInt(durationString.substring(0,2))*60 + Integer.parseInt(durationString.substring(3));
            float duration = Float.parseFloat(durationString.substring(0,2))*60 + Float.parseFloat(durationString.substring(3));
            lineData.add(new Entry(i+1, duration));
            /*表格部分*/
            TableRow tableRow = new TableRow(this);
            if(i%2==0) tableRow.setBackgroundResource(R.color.tableBackgroundWhite);
            else tableRow.setBackgroundResource(R.color.tableBackgroundPink);
            tableRow.setPadding(5,5,5,5);
            //周几
            TextView day = new TextView(this);
            day.setGravity(Gravity.CENTER);
            day.setTextColor(Color.BLACK);
            day.setText(weekday[i]);
            tableRow.addView(day);
            //时长
            TextView time = new TextView(this);
            time.setGravity(Gravity.CENTER);
            time.setTextColor(Color.BLACK);
            time.setText(durationString.substring(0,2)+"时"+durationString.substring(3,5)+"分");
            tableRow.addView(time);
            rep_line_table.addView(tableRow);
        }
        plan_time_all.setText(timeAll/60+"时"+timeAll%60+"分");
        initLineChart(rep_linechart,lineData);
    }

    private void initLineChart(LineChart lineChart,ArrayList<Entry> lineData){
        //设置显示属性
        Description description =new Description();
        description.setText("");
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


        Log.i("linedata",lineData.toString());


        //LineDataSet每一个对象就是一条连接线
        LineDataSet set;
        set = new LineDataSet(lineData,"时长");
        set.setColor(Color.RED);
        set.setCircleColor(Color.RED);
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

    private void getLineData(){
        //绑定数据
        final ArrayList<Entry> lineData = new ArrayList<>();
        //从日历页面获取日期数据
        Intent intent=getIntent();
        String today=intent.getStringExtra("date");
        rep_date.setText(today);
        Log.i("labelId",labelId+"");


        //从后端获取数据
        JSONObject reportObject = new JSONObject();
        reportObject.put("date",today);
        reportObject.put("labelId",labelId);

        HttpUtil.request("SheetServlet?method=GetWeeklyChange","post",reportObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    //获取服务器端响应数据
                    jsonString = response.body().string();
                    Log.i("jsonString",jsonString);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {appendLineTable(jsonString,lineData);
                        }
                    });

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                //createFail();
            }
        });


//        /*
//         * 数据测试
//         */
//        String jsonString="{\n" +
//                "\n" +
//                "\"status\":\"success\",\n" +
//                "\n" +
//                "\"week\": \"第五周\",\n" +
//                "\n" +
//                "\"durationArray\":[\"01:20\", \"01:20\", \"01:20\", \"01:20\", \"03:20\", \"03:20\", \"03:20\"]\n" +
//                "\n" +
//                "}";
//        Log.i("jsonString",jsonString);
//        appendLineTable(jsonString,lineData);
    }



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
        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(true);
        pieChart.setDescription(null);
    }

    private void appendPieTable(String jsonString, int flag){

        final ArrayList<String> xValues = new ArrayList<>();  //xVals用来表示每个饼块上的内容
        final ArrayList<PieEntry> yValues = new ArrayList<>();  //yVals用来表示封装每个饼块的实际数据
        final ArrayList<Integer> colors = new ArrayList<Integer>();

        JSONObject resJson=JSONObject.parseObject(jsonString);
        //获得第几周
        week = resJson.getString("week");
        rep_week.setText(week);
        if(flag==2){
            //时间分配表
            rep_line_table.removeAllViewsInLayout();//清空时间分配表格
            JSONArray timesharing = resJson.getJSONArray("TimeSharing");
            Log.i("timesharing",timesharing.toString());
            int timeAll = 0;

            for(int i=0;i<timesharing.size();i++){
                JSONObject resJsonItem = timesharing.getJSONObject(i);
                int labelid = resJsonItem.getIntValue("labelid");
                float percent = resJsonItem.getFloatValue("percent");//所占时间比，以浮点数表示
                String duration = resJsonItem.getString("duration");//该天所有该标签的事件总时间
                float satisfaction = resJsonItem.getFloatValue("satisfaction");//该天所有该标签的事件平均满意程度
                timeAll += Integer.parseInt(duration.substring(0,2))*60 + Integer.parseInt(duration.substring(3));
                //图表部分
                xValues.add("Quarterly" +labelid);
                yValues.add(new PieEntry(percent,LabelUtil.getLabel(labelid).getName()));
                colors.add(getResources().getColor(labelUtil.getLabel(labelid).getColor()));
                /*表格部分*/
                //行
                TableRow tableRow = new TableRow(this);
                if(i%2==0) tableRow.setBackgroundResource(R.color.tableBackgroundWhite);
                else tableRow.setBackgroundResource(R.color.tableBackgroundPink);
                tableRow.setPadding(5,5,5,5);
                //标签名
                TextView label_name = new TextView(this);
                label_name.setGravity(Gravity.CENTER);
                label_name.setPadding(10,0,10,0);
                label_name.setText(labelUtil.getLabel(labelid).getName());
                Log.i("name",labelUtil.getLabel(labelid).getName());
                tableRow.addView(label_name);
                //标签图标
                ImageView label_icon = new ImageView(ReportFormActivity.this);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), labelUtil.getLabel(labelid).getImage());
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap,80,80,true);
                label_icon.setImageBitmap(resizedBitmap);
                tableRow.addView(label_icon);
                //linearlayout
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                //时长
                TextView time = new TextView(this);
                time.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp2.setMargins(10, 5, 0, 5);
                time.setLayoutParams(lp2);
                time.setText(duration.substring(0,2)+"时"+duration.substring(3,5)+"分");
                linearLayout.addView(time);
                //
                LinearLayout linearLayout3 = new LinearLayout(this);
                linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(250, 50));
                linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
                //满意度LinearLayout
                LinearLayout linearLayout2 = new LinearLayout(this);
                linearLayout2.setGravity(Gravity.CENTER_VERTICAL);
                linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(200, 20));
                linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                if(i%2==0) linearLayout2.setBackgroundResource(R.drawable.satisfy_bg);
                else linearLayout2.setBackgroundResource(R.drawable.satisfy_bg1);
                //笑脸
                TextView smile = new TextView(this);
                smile.setGravity(Gravity.TOP);
                LinearLayout.LayoutParams lps=new LinearLayout.LayoutParams(50,50);
                smile.setLayoutParams(lps);
                smile.setText("✿");
                linearLayout3.addView(smile);
                //满意度
                TextView satisfy = new TextView(this);
                int width = (int)satisfaction*200/5;
                Log.i("width",width+"");
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width,20);
                satisfy.setLayoutParams(lp);
                if(i%2==0) satisfy.setBackgroundResource(R.drawable.satisfy_show);
                else satisfy.setBackgroundResource(R.drawable.satisfy_show1);
                linearLayout2.addView(satisfy);
                linearLayout3.addView(linearLayout2);
                linearLayout.addView(linearLayout3);
                tableRow.addView(linearLayout);
                rep_line_table.addView(tableRow);
            }
            plan_time_all.setText(timeAll/60+"时"+timeAll%60+"分");
        }
        else if(flag==1){
            //日程表
            rep_pie_table.removeAllViewsInLayout();//清空日程表格
            JSONArray Schedule = resJson.getJSONArray("Schedule");
            Log.i("Schedule",Schedule.toString());
            int timeAll =0;

            for(int i=0;i<Schedule.size();i++){
                JSONObject resJsonItem = Schedule.getJSONObject(i);
                int labelid = resJsonItem.getIntValue("labelid");
                float percent = resJsonItem.getFloatValue("percent");//所占时间比，以浮点数表示
                String duration = resJsonItem.getString("duration");//该天所有该标签的事件总时间
                timeAll += Integer.parseInt(duration.substring(0,2))*60 + Integer.parseInt(duration.substring(3));
                //图表部分
                xValues.add("Quarterly" +labelid);
                yValues.add(new PieEntry(percent,LabelUtil.getLabel(labelid).getName()));
                colors.add(getResources().getColor(labelUtil.getLabel(labelid).getColor()));
                //*表格部分*/
                //行
                TableRow tableRow = new TableRow(this);
                if(i%2==0) tableRow.setBackgroundResource(R.color.tableBackgroundWhite);
                else tableRow.setBackgroundResource(R.color.tableBackgroundPink);
                tableRow.setPadding(5,5,5,5);
                //标签名
                TextView label_name = new TextView(this);
                label_name.setGravity(Gravity.CENTER);
                label_name.setPadding(10,0,10,0);
                label_name.setText(labelUtil.getLabel(labelid).getName());
                Log.i("name",labelUtil.getLabel(labelid).getName());
                tableRow.addView(label_name);
                //标签图标
                ImageView label_icon = new ImageView(ReportFormActivity.this);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), labelUtil.getLabel(labelid).getImage());
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap,80,80,true);
                label_icon.setImageBitmap(resizedBitmap);
                tableRow.addView(label_icon);
                //linearlayout
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                //时长
                TextView time = new TextView(this);
                time.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp2.setMargins(10, 5, 0, 5);
                time.setLayoutParams(lp2);
                time.setText(duration.substring(0,2)+"时"+duration.substring(3,5)+"分");
                linearLayout.addView(time);
                tableRow.addView(linearLayout);
                rep_pie_table.addView(tableRow);
            }
            time_all.setText(timeAll/60+"时"+timeAll%60+"分");
        }
        PieData pieData=setPieData(xValues,yValues,colors);
        if(flag==1)
            showChart(rep_week_piechart,pieData);
        else if(flag==2)
            showChart(rep_piechart,pieData);
    }



    private PieData setPieData(ArrayList<String> xValues,ArrayList<PieEntry> yValues,ArrayList<Integer> colors){
        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, " "/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离



        // 饼图颜色
//        colors.add(Color.rgb(205, 205, 205));
//        colors.add(Color.rgb(114, 188, 223));
//        colors.add(Color.rgb(255, 123, 124));
//        colors.add(Color.rgb(57, 135, 200));
//        colors.add(Color.rgb(127, 235, 230));
//        colors.add(Color.rgb(247, 35, 20));
//        colors.add(Color.rgb(117, 85, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(pieDataSet);
        return  pieData;
    }


    /**
     *
     * @param count 分成几部分
     * @param range
     */
    private void getPieData(int count, float range) {




        //从日历页面获取日期数据
        Intent intent=getIntent();
        String today=intent.getStringExtra("date");
        Log.i("reportToday",today);
        rep_date.setText(today);

        //从后端获取数据
        JSONObject reportObject = new JSONObject();
        reportObject.put("date",today);
        Log.i("reportObject",reportObject.toString());

        HttpUtil.request("SheetServlet?method=GetWeeklySheet","post",reportObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    //获取服务器端响应数据
                    jsonString = response.body().string();
                    Log.i("jsonString",jsonString);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //appendDailySheet(jsonString);
                            appendPieTable(jsonString,1);
                            appendPieTable(jsonString,2);
                        }
                    });

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                //createFail();
            }
        });

        /*
         * 数据测试
         */
//        String jsonString="{\"status\":\"success\",\n" +
//                "\n" +
//                " \"week\": \"第八周\",\n" +
//                "\n" +
//                " \"TimeSharing\": [{\n" +
//                "\n" +
//                "                        \"labelid\": \"1\",\n" +
//                "\n" +
//                "                        \"duration\": \"03:33\",\n" +
//                "\n" +
//                "                        \"percent\":0.5,\n" +
//                "\n" +
//                "                        \"satisfaction\": 3.8\n" +
//                "\n" +
//                "               }, {\n" +
//                "\n" +
//                "                        \"labelid\": \"2\",\n" +
//                "\n" +
//                "                        \"duration\": \"03:29\",\n" +
//                "\n" +
//                "                        \"percent\":0.5,\n" +
//                "\n" +
//                "                        \"satisfaction\": 4.8\n" +
//                "\n" +
//                "               }],\n" +
//                "\n" +
//                "   \"Schedule\": [{\n" +
//                "\n" +
//                "                        \"labelid\": \"1\",\n" +
//                "\n" +
//                "                        \"duration\": \"03:50\",\n" +
//                "\n" +
//                "                        \"percent\":0.5,\n" +
//                "\n" +
//                "               }, {\n" +
//                "\n" +
//                "                        \"labelid\": \"2\",\n" +
//                "\n" +
//                "                        \"duration\": \"01:29\",\n" +
//                "\n" +
//                "                        \"percent\":0.5,\n" +
//                "\n" +
//                "               }]}";
//        Log.i("jsonString",jsonString);
//        appendPieTable(jsonString,flag,xValues,yValues,colors);



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

//        //y轴的集合
//        PieDataSet pieDataSet = new PieDataSet(yValues, " "/*显示在比例图上*/);
//        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
//
//
//        // 饼图颜色
////        colors.add(Color.rgb(205, 205, 205));
////        colors.add(Color.rgb(114, 188, 223));
////        colors.add(Color.rgb(255, 123, 124));
////        colors.add(Color.rgb(57, 135, 200));
////        colors.add(Color.rgb(127, 235, 230));
////        colors.add(Color.rgb(247, 35, 20));
////        colors.add(Color.rgb(117, 85, 200));
//
//        pieDataSet.setColors(colors);
//
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        float px = 5 * (metrics.densityDpi / 160f);
//        pieDataSet.setSelectionShift(px); // 选中态多出的长度
//
//        PieData pieData = new PieData(pieDataSet);
//
//        return pieData;
    }


}