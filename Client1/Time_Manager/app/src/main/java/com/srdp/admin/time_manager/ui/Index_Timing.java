package com.srdp.admin.time_manager.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.util.TimeUtil;

public class Index_Timing extends AppCompatActivity {
    private boolean isStop=false;
    private boolean isStart=false;
    private Handler mHandler;
    private TextView indexTimer;
    private long timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index__timing);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        mHandler = new Handler();
        indexTimer =(TextView) findViewById(R.id.indexTimer);
        timer=0;
        FrameLayout stopTiming=(FrameLayout) findViewById(R.id.stopTiming);
        stopTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(TimerRunnable);
                isStop=true;
                isStart=false;
                timer=0;
                String timeStr = TimeUtil.getFormatTime(timer);
                indexTimer.setText(timeStr);
            }
        });
        ImageView setTiming=(ImageView) findViewById(R.id.setTiming);
        setTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStop=false;
                if(!isStart)
                    countTimer();
                isStart=true;
            }
        });

    }
    private Runnable TimerRunnable = new Runnable() {

        @Override
        public void run() {
            if(!isStop){
                timer += 1000;
                String timeStr = TimeUtil.getFormatTime(timer);
                indexTimer.setText(timeStr);
            }
            countTimer();
        }
    };

    private void countTimer(){
        mHandler.postDelayed(TimerRunnable, 1000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(TimerRunnable);
}

}
