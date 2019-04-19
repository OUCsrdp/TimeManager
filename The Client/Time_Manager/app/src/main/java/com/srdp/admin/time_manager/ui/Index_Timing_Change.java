package com.srdp.admin.time_manager.ui;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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
import com.srdp.admin.time_manager.util.AffairUtil;
import com.srdp.admin.time_manager.util.DensityUtil;
import com.srdp.admin.time_manager.util.HttpUtil;
import com.srdp.admin.time_manager.util.LabelUtil;
import com.srdp.admin.time_manager.util.TimeUtil;
import com.srdp.admin.time_manager.util.TokenUtil;
import com.srdp.admin.time_manager.util.UserUtil;
import com.srdp.admin.time_manager.widget.adapters.ExcellentDistributionListAdapter;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

import static com.srdp.admin.time_manager.util.AffairUtil.hasRecorded;
import static com.srdp.admin.time_manager.util.AffairUtil.writeStartTime;

public class Index_Timing_Change extends AppCompatActivity {
    private boolean isStop=false;
    private boolean isStart=false;
    private Handler mHandler;
    private TextView indexTimer;
    private long timer;
    private String date;//当前日期
    private String startTime;//开始时间
    private String endTime;//结束时间
    private int clickTimes=0;//表示导航栏点击次数
    private FrameLayout showedNavi;//表示隐藏部分的导航栏
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index__timing);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        //AffairUtil.deleteAffairInfor();
        //TokenUtil.initToken(this);
        //初始化token,测试用
        initData();
        //创建该活动时初始化日期信息
        initNavi();//初始化导航栏
        mHandler = new Handler();
        indexTimer =(TextView) findViewById(R.id.indexTimer);
        timer=0;
        FrameLayout stopTiming=(FrameLayout) findViewById(R.id.stopTiming);
        stopTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isStart) return;
                mHandler.removeCallbacks(TimerRunnable);
                isStop=true;
                isStart=false;
                timer=0;
                String timeStr = TimeUtil.getFormatTime(timer);
                indexTimer.setText(timeStr);
                endTime=TimeUtil.getNowTime();//获取当前时分作为结束时间
                //requestCreate();
                //把记录的当前事务时间信息删除]
                JSONObject affairStore=AffairUtil.getAffairTime();
                AffairUtil.deleteAffairInfor();
                //发起网络请求把时间的修改保存到数据库
                requestModifyTime(affairStore);
            }
        });
        ImageView setTiming=(ImageView) findViewById(R.id.setTiming);
        setTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStop=false;
                //如果没有在计时，点击+的时候跳转到创建计时页
                if(!isStart)
                {
                    Intent jump_to_create=new Intent(Index_Timing_Change.this,CreateTimingActivity.class);
                    startActivity(jump_to_create);
                }
            }
        });

    }

    //在activity获取焦点的时候判断是否初始化开始时间
    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this,"resume",Toast.LENGTH_SHORT).show();
        if(hasRecorded())//如果有记录开始时间，就把开始时间取出来计算经历时间，开启计时器
        {
            startTime=AffairUtil.readStartTime();
            timer=TimeUtil.getMs(TimeUtil.getNowTime())-TimeUtil.getMs(startTime);
            String timeStr = TimeUtil.getFormatTime(timer);
            indexTimer.setText(timeStr);
            isStart=true;
            isStop=false;
            countTimer();
            //开始计时
        }
        //没有记录开始时间表示并没有创建计时
        /*else //如果没有记录开始时间，历经时间为0，开启计时器
        {
            startTime = TimeUtil.getNowTime();
            timer=0;
        }*/
    }
    //在activity失去焦点时把开始时间保存到sharedPerfence中并且停止计时器工作,
    @Override
    protected void onPause() {
        super.onPause();
        if(isStart&&!hasRecorded())//若果没有记录过开始时间
        {
            writeStartTime(startTime);
        }
        //停止计时器工作
        mHandler.removeCallbacks(TimerRunnable);
    }
    //在activity销毁的时候移除handler的回调函数
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(TimerRunnable);
    }

    private static class LabelHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<Index_Timing_Change> mActivty;

        public LabelHandler(Index_Timing_Change activity) {
            mActivty = new WeakReference<Index_Timing_Change>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Index_Timing_Change activity = mActivty.get();
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
        private final WeakReference<Index_Timing_Change> mActivty;

        public TimingHandler(Index_Timing_Change activity) {
            mActivty = new WeakReference<Index_Timing_Change>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Index_Timing_Change activity = mActivty.get();
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
    /*private void requestCreate()
    {
        MyApplication application=(MyApplication)this.getApplicationContext();
        JSONObject affairjson=new JSONObject();
        if(application.getIsAffair()==1)
        {
            Affair affair=application.getTimingAffair();
            affair.setTimeStart(startTime);
            affair.setTimeEnd(endTime);
            affairjson=(JSONObject) JSON.toJSON(affair);
            affairjson.put("sign1",0);
            affairjson.put("sign2",1);
        }else{
            S_Affair saffair=application.getTimingSAffair();
            saffair.setTimeStart(startTime);
            saffair.setTimeEnd(endTime);
            affairjson=(JSONObject) JSON.toJSON(saffair);
            affairjson.put("sign1",1);
            affairjson.put("sign2",0);
        }
        affairjson.put("token", TokenUtil.getToken());
        affairjson.put("date",date);
        affairjson.put("username", UserUtil.getUser().getName());
        //username  for test
        AffairHttp affairHttp=new AffairHttp(affairjson);
        affairHttp.requestByPost(new TimingHandler(Index_Timing_Change.this));
    }*/
    private void requestModifyTime(JSONObject affairStore)
    {

        //Toast.makeText(this,startTime,Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,TimeUtil.getNowTime(),Toast.LENGTH_SHORT).show();
        //从本地文件中读出affair的基本信息
        JSONObject reqJson=affairStore;
        //reqJson.put("EndTime",endTime);
        //添加程序结束时间
        reqJson.put("StartTime",TimeUtil.delSecond(reqJson.getString("StartTime")));
        reqJson.put("EndTime",TimeUtil.delSecond(endTime));
        HttpUtil.request("AffairServlet?method=ChangeAffairTime","post",reqJson,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    JSONObject resJson= null;
                    resJson = JSON.parseObject(response.body().string());
                    String status=resJson.getString("status");
                    //根据服务器返回的状态处理响应
                    if(status.equals("unlogin")){
                        reLogin();
                    }else if(status.equals("fail")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                createFail();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                createSuccess();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call,@NonNull IOException e) {
                createFail();
            }
        });
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
    private void createSuccess(){Toast.makeText(this,"计时完成！",Toast.LENGTH_LONG).show();}
    private void createFail()
    {
        Toast.makeText(this,"计时失败！",Toast.LENGTH_LONG).show();
    }
    private void reLogin()
    {
        Intent relogin=new Intent(this,LoginActivity.class);
        startActivity(relogin);
    }
    //导航栏及其处理
    private void initNavi()
    {
        ImageView naviShow=(ImageView) findViewById(R.id.IndexNaviShow);
        showedNavi=(FrameLayout) findViewById(R.id.IndexShowedNavi);
        naviShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                clickTimes++;
                if(clickTimes%2==1)
                {
                    showWholeNavi();
                }
                else
                {
                    hideWholeNavi();
                }
            }
        });
        TextView navi1=(TextView) findViewById(R.id.IndexNaviMyAssign);
        navi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theIntent=new Intent(Index_Timing_Change.this,assign_table.class);
                startActivity(theIntent);

            }
        });
        TextView navi2=(TextView) findViewById(R.id.IndexNaviMyPlan);
        navi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theIntent=new Intent(Index_Timing_Change.this,Plan_Table.class);
                startActivity(theIntent);
            }
        });
        TextView navi3=(TextView) findViewById(R.id.IndexNaviMyTable);
        navi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theIntent=new Intent(Index_Timing_Change.this,ReportCalendarActivity.class);
                startActivity(theIntent);
            }
        });
        TextView navi4=(TextView) findViewById(R.id.IndexNaviMyAnalyse);
        navi4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theIntent=new Intent(Index_Timing_Change.this,PatternAnalysisPage5.class);
                startActivity(theIntent);
            }
        });
        TextView navi5=(TextView) findViewById(R.id.IndexNaviMySuggest);
        navi5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theIntent=new Intent(Index_Timing_Change.this,ExcellentDistributionListActivity.class);
                startActivity(theIntent);
            }
        });
        TextView navi6=(TextView) findViewById(R.id.IndexNaviMyCenter);
        navi6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theIntent=new Intent(Index_Timing_Change.this,Person_Center.class);
                startActivity(theIntent);
            }
        });

    }
    private void showWholeNavi()
    {

        ValueAnimator changeWid = ValueAnimator.ofInt(showedNavi.getLayoutParams().width, DensityUtil.dip2px(this,249));
        changeWid.setDuration(300);
        changeWid.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentValue = (Integer) animator.getAnimatedValue();
                showedNavi.getLayoutParams().width = currentValue;
                showedNavi.requestLayout();
            }
        });
        changeWid.start();
    }
    private void hideWholeNavi()
    {
        ValueAnimator changeWid = ValueAnimator.ofInt(showedNavi.getLayoutParams().width,0);
        changeWid.setDuration(300);
        changeWid.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentValue = (Integer) animator.getAnimatedValue();
                showedNavi.getLayoutParams().width = currentValue;
                showedNavi.requestLayout();
            }
        });
        changeWid.start();
    }
}
