package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
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
import com.srdp.admin.time_manager.model.moudle.Label;
import com.srdp.admin.time_manager.model.moudle.TimeSharing;
import com.srdp.admin.time_manager.util.HttpUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.util.LabelUtil;

import okhttp3.Call;
import okhttp3.Response;

import static com.alibaba.fastjson.JSON.parseObject;


public class ReportFormDayActivity extends AppCompatActivity {

    private TableLayout day_table;//日程表表格
    private TableLayout day_plan_table;//时间分配表表格

    private PieChart rep_day_piechart;//日程表
    private PieChart rep_plan_piechart;//时间分配表
    private TextView report_weekday;//星期x
    private TextView report_date;//日期
    private TextView day_time;//日程表总时间
    private TextView day_plan_time;//时间分配表总时间

    private Button week_trans;//周报表日报表切换按钮

    private String weekday;//获取的星期x

    private int count;//饼图数量

    private LabelUtil labelUtil;
    private int flag;
    private String jsonString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form_day);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        Log.i("info","成功进入日报表页！");


        day_plan_table = (TableLayout)findViewById(R.id.day_plan_table);
        day_plan_table.setStretchAllColumns(true);
        day_table = (TableLayout)findViewById(R.id.day_table);
        day_table.setStretchAllColumns(true);
        report_weekday = (TextView) findViewById(R.id.report_weekday);
        report_date = (TextView) findViewById(R.id.report_date);
        day_time = (TextView) findViewById(R.id.day_time);
        day_plan_time = (TextView) findViewById(R.id.day_plan_time);

        week_trans = (Button) findViewById(R.id.week_trans);



        rep_day_piechart = (PieChart) findViewById(R.id.rep_day_piechart);
        //flag=1;
        //appendDailySheet(jsonString,1);
        //getPieData(6, 100);
        rep_plan_piechart = (PieChart) findViewById(R.id.rep_plan_piechart);
        //flag=2;
        //appendDailySheet(jsonString,2);
        //getPieData(6, 100);
        getPieData(6, 100);

        //切换到周报表
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

        pieChart.setDrawCenterText(false);
        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(true);
        pieChart.setDescription(null);
    }


    /*
     * 颜色转换成16进制
     */
    String Color_16_NoAlpha(int color) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("#");
        stringBuffer.append(Integer.toHexString(Color.red(color)));
        stringBuffer.append(Integer.toHexString(Color.green(color)));
        stringBuffer.append(Integer.toHexString(Color.blue(color)));
        return stringBuffer.toString();
    }

    /**
     *
     */
    private void appendDailySheet(String jsonString,int flag)
    {
        //数据显示操作
        ArrayList<String> xValues = new ArrayList<>();  //xVals用来表示每个饼块上的内容
        ArrayList<PieEntry> yValues = new ArrayList<>();  //yVals用来表示封装每个饼块的实际数据
        ArrayList<Integer> colors = new ArrayList<Integer>();
        //获得星期x
        JSONObject resJson=JSONObject.parseObject(jsonString);
        weekday = resJson.getString("weekday");
        Log.i("weekday",weekday);
        report_weekday.setText(weekday.toString());
        if(flag==2){
            //时间分配表
            day_plan_table.removeAllViewsInLayout();//清空时间分配表格
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
                yValues.add(new PieEntry(percent, labelUtil.getLabel(labelid).getName()));
                colors.add(getResources().getColor(labelUtil.getLabel(labelid).getColor()));
                Log.i("color", labelid+","+Color_16_NoAlpha(getResources().getColor(labelUtil.getLabel(labelid).getColor())));
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
                ImageView label_icon = new ImageView(ReportFormDayActivity.this);
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
                day_plan_table.addView(tableRow);
            }
            day_plan_time.setText(timeAll/60+"时"+timeAll%60+"分");
        }
        else if(flag==1){
            //日程表
            day_table.removeAllViewsInLayout();//清空日程表格
            JSONArray Schedule = resJson.getJSONArray("Schedule");
            Log.i("Schedule",Schedule.toString());
            int timeAll = 0;

            for(int i=0;i<Schedule.size();i++){
                JSONObject resJsonItem = Schedule.getJSONObject(i);
                int labelid = resJsonItem.getIntValue("labelid");
                float percent = resJsonItem.getFloatValue("percent");//所占时间比，以浮点数表示
                String duration = resJsonItem.getString("duration");//该天所有该标签的事件总时间
                timeAll += Integer.parseInt(duration.substring(0,2))*60 + Integer.parseInt(duration.substring(3));
                //图表部分
                xValues.add("Quarterly" +labelid);
                yValues.add(new PieEntry(percent,labelUtil.getLabel(labelid).getName()));
                colors.add(getResources().getColor(labelUtil.getLabel(labelid).getColor()));
//                /*表格部分*/
//                //行
//                TableRow tableRow = new TableRow(this);
//                if(i%2==0) tableRow.setBackgroundResource(R.color.tableBackgroundWhite);
//                else tableRow.setBackgroundResource(R.color.tableBackgroundPink);
//                tableRow.setPadding(5,5,5,5);
//                //标签名
//                TextView label_name = new TextView(this);
//                label_name.setGravity(Gravity.CENTER);
//                label_name.setPadding(10,0,10,0);
//                label_name.setText(labelUtil.getLabel(labelid).getName());
//                Log.i("name",labelUtil.getLabel(labelid).getName());
//                tableRow.addView(label_name);
//                //标签图标
//                ImageView label_icon = new ImageView(ReportFormDayActivity.this);
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), labelUtil.getLabel(labelid).getImage());
//                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap,80,80,true);
//                label_icon.setImageBitmap(resizedBitmap);
//                tableRow.addView(label_icon);
//                //linearlayout
//                LinearLayout linearLayout = new LinearLayout(this);
//                linearLayout.setGravity(Gravity.CENTER);
//                linearLayout.setOrientation(LinearLayout.VERTICAL);
//                //时长
//                TextView time = new TextView(ReportFormDayActivity.this);
//                time.setText(duration.substring(0,2)+"时"+duration.substring(3,5)+"分");
//                Log.i("time",duration.substring(0,2)+"时"+duration.substring(3,5)+"分");
//                time.setGravity(Gravity.CENTER);
//                time.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//                time.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                linearLayout.addView(time);
//                tableRow.addView(linearLayout);
//                day_table.addView(tableRow);
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
                    ImageView label_icon = new ImageView(ReportFormDayActivity.this);
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
                    day_table.addView(tableRow);
            }
            day_time.setText(timeAll/60+"时"+timeAll%60+"分");
        }
        PieData pieData=setPieData(xValues,yValues,colors);
        if(flag==1)
            showChart(rep_day_piechart,pieData);
        else if(flag==2)
            showChart(rep_plan_piechart,pieData);
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
    private void getPieData(int count, float range) {


        //从日历页面获取日期数据
        Intent intent=getIntent();
        String today=intent.getStringExtra("date");
        report_date.setText(today);

        //从后端获取数据
        JSONObject reportObject = new JSONObject();
        reportObject.put("date",today);

        HttpUtil.request("SheetServlet?method=GetDailySheet","post",reportObject,new okhttp3.Callback(){
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
                            appendDailySheet(jsonString,1);
                            appendDailySheet(jsonString,2);
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
        /*final String jsonString="{\"status\":\"success\",\n" +
                "\n" +
                " \"weekday\": \"星期六\",\n" +
                "\n" +
                " \"TimeSharing\": [{\n" +
                "\n" +
                "                        \"labelid\": \"1\",\n" +
                "\n" +
                "                        \"duration\": \"03:33\",\n" +
                "\n" +
                "                        \"percent\":0.5,\n" +
                "\n" +
                "                        \"satisfaction\": 3.8\n" +
                "\n" +
                "               }, {\n" +
                "\n" +
                "                        \"labelid\": \"2\",\n" +
                "\n" +
                "                        \"duration\": \"03:29\",\n" +
                "\n" +
                "                        \"percent\":0.5,\n" +
                "\n" +
                "                        \"satisfaction\": 4.8\n" +
                "\n" +
                "               }],\n" +
                "\n" +
                "   \"Schedule\": [{\n" +
                "\n" +
                "                        \"labelid\": \"1\",\n" +
                "\n" +
                "                        \"duration\": \"03:50\",\n" +
                "\n" +
                "                        \"percent\":0.5,\n" +
                "\n" +
                "               }, {\n" +
                "\n" +
                "                        \"labelid\": \"2\",\n" +
                "\n" +
                "                        \"duration\": \"01:29\",\n" +
                "\n" +
                "                        \"percent\":0.5,\n" +
                "\n" +
                "               }]}";
        Log.i("jsonString",jsonString);
        appendDailySheet(jsonString,1);
        appendDailySheet(jsonString,2);*/


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

    }
}