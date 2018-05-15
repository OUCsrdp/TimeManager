package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.http.AffairHttp;
import com.srdp.admin.time_manager.model.moudle.Affair;
import com.srdp.admin.time_manager.model.moudle.MyApplication;
import com.srdp.admin.time_manager.model.moudle.S_Affair;
import com.srdp.admin.time_manager.util.LabelUtil;
import com.srdp.admin.time_manager.util.TimeUtil;
import com.srdp.admin.time_manager.util.TokenUtil;
import com.srdp.admin.time_manager.util.UserUtil;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

public class Index_Timing extends AppCompatActivity {
    private boolean isStop=false;
    private boolean isStart=false;
    private Handler mHandler;
    private TextView indexTimer;
    private long timer;
    private String date;//当前日期
    private String startTime;//开始时间
    private String endTime;//结束时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index__timing);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TokenUtil.initToken(this);
        //初始化token,测试用
        initData();
        //创建该活动时初始化日期信息
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
                endTime=TimeUtil.getHourandmin();//获取当前时分作为结束时间
                requestCreate();
            }
        });
        ImageView setTiming=(ImageView) findViewById(R.id.setTiming);
        setTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStop=false;
                //如果没有在计时，点击+的时候跳转到开始计时页
                if(!isStart)
                {
                    Intent jump_to_create=new Intent(Index_Timing.this,CreateTimingActivity.class);
                    startActivity(jump_to_create);
                }
            }
        });

    }

    //在activity获取焦点的时候判断是否初始化开始时间
    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getStringExtra("startTime")!=null){
            startTime=getIntent().getStringExtra("startTime");
            timer=0;
            isStart=true;
            countTimer();
        }//如果页面是从创建计时页过来且传了startTime，重新设置startTime
    }
    //在activity销毁的时候移除handler的回调函数
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(TimerRunnable);
    }

    private static class LabelHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<Index_Timing> mActivty;

        public LabelHandler(Index_Timing activity) {
            mActivty = new WeakReference<Index_Timing>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Index_Timing activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        LabelUtil.setLabels((JSONObject)msg.obj);break;
                    case 2:
                }
            }
        }
    }
    private static class TimingHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<Index_Timing> mActivty;

        public TimingHandler(Index_Timing activity) {
            mActivty = new WeakReference<Index_Timing>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Index_Timing activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        Toast.makeText(activity,"完成计时！",Toast.LENGTH_SHORT).show();break;
                    case 0:
                        activity.reLogin();break;
                }
            }
        }
    }
    //计时相关函数
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

    //在按结束按钮的时候发送请求，创建或修改事件
    private void requestCreate()
    {
        MyApplication application=(MyApplication)this.getApplicationContext();
        JSONObject affairjson=new JSONObject();
        if(application.getIsAffair()==1)
        {
            Affair affair=application.getTimingAffair();
            affair.setTimeStart(startTime);
            affair.setTimeEnd(endTime);
            affairjson=(JSONObject) JSON.toJSON(affair);
            affairjson.put("sign1",1);
            affairjson.put("sign2",1);
        }else{
            S_Affair saffair=application.getTimingSAffair();
            saffair.setTimeStart(startTime);
            saffair.setTimeEnd(endTime);
            affairjson=(JSONObject) JSON.toJSON(saffair);
            affairjson.put("sign1",0);
            affairjson.put("sign2",0);
        }
        affairjson.put("token", TokenUtil.getToken());
        affairjson.put("date",date);
        affairjson.put("username", UserUtil.getUser().getName());
        AffairHttp affairHttp=new AffairHttp(affairjson);
        affairHttp.requestByPost(new TimingHandler(Index_Timing.this));
    }
    private void initData()//初始化计时的日期
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        date=year+"年"+month+"月"+day+"日";
    }
    private void reLogin()
    {
        Intent relogin=new Intent(this,LoginActivity.class);
        startActivity(relogin);
    }

}
