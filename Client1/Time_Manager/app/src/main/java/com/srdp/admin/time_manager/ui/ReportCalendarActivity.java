package com.example.lizliz.timemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class ReportCalendarActivity extends AppCompatActivity {

    private CalendarView calendar_rep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_calendar);

        calendar_rep = (CalendarView)findViewById(R.id.calendar_rep);

    }
}
