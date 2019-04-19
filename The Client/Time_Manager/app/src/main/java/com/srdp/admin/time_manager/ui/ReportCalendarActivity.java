package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.Switch;


import com.srdp.admin.time_manager.R;

public class ReportCalendarActivity extends AppCompatActivity {

    private CalendarView calendar_rep;//日历

    private Switch weekday_trans_btn;//周报表日报表开关

    int isWeek = 1;//true为周报表 false为日报表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_calendar);


        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        weekday_trans_btn = (Switch) findViewById(R.id.weekday_trans_btn);
        //切换周报表和日报表
        weekday_trans_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    isWeek = 0;
                }
                else {
                    isWeek = 1;
                }
            }
        });
        calendar_rep = (CalendarView)findViewById(R.id.calendar_rep);
        //跳转日报表页或周报表页
        calendar_rep.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String today = year + "年" + month + "月" + dayOfMonth + "日";
                Intent intent = new Intent();
                intent.putExtra("date", today);
                if(isWeek==1){//去周报表
                    intent.setClass(ReportCalendarActivity.this, ReportFormActivity.class);//从哪里跳到哪里
                }else {//去日报表
                    intent.setClass(ReportCalendarActivity.this, ReportFormDayActivity.class);//从哪里跳到哪里
                }

                ReportCalendarActivity.this.startActivity(intent);
            }
        });
    }
}
