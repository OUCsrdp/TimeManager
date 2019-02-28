package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;



import com.srdp.admin.time_manager.R;

public class ReportCalendarActivity extends AppCompatActivity {

    private CalendarView calendar_rep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_calendar);

        calendar_rep = (CalendarView)findViewById(R.id.calendar_rep);
        //跳转日报表页
        calendar_rep.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String today = year + "年" + month + "月" + dayOfMonth + "日";
                Intent intent = new Intent();
                intent.putExtra("date", today);
                intent.setClass(ReportCalendarActivity.this, ReportFormDayActivity.class);//从哪里跳到哪里
                ReportCalendarActivity.this.startActivity(intent);
            }
        });
    }
}
