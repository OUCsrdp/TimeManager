package com.srdp.admin.time_manager.ui;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.http.TimeSharingHttp;
import com.srdp.admin.time_manager.model.moudle.Affair;
import com.srdp.admin.time_manager.model.moudle.Label;
import com.srdp.admin.time_manager.model.moudle.S_Affair;
import com.srdp.admin.time_manager.model.moudle.TimeSharing;
import com.srdp.admin.time_manager.util.DensityUtil;
import com.srdp.admin.time_manager.util.LabelUtil;
import com.srdp.admin.time_manager.util.TimeUtil;
import com.srdp.admin.time_manager.util.TokenUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class assign_table extends AppCompatActivity {
    private boolean isAnimated = false;
    private LinearLayout container;
    private TimeSharingHttp timeSharingHttp;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private JSONObject assignJson;//返回的新的时间分配表的jsonObject
    private TimeSharing nowTable;
    private List<Affair> affair=new ArrayList<Affair>();
    private List<S_Affair> saffair=new ArrayList<S_Affair>();
    private String[] weekday={"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_table);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        //隐藏默认actionbar

        initData();
        //初始化一些数据oke
        TokenUtil.initToken(this);
        //token for test
        request();
        //进入页面请求今日时间分配数据
        ImageView leftButton = (ImageView) findViewById(R.id.AssignLeft);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAnimated)
                {
                    changeAssign(v);
                    calendar.add(Calendar.DATE, -1);
                    year=calendar.get(Calendar.YEAR);
                    month=calendar.get(Calendar.MONTH)+1;
                    day=calendar.get(Calendar.DAY_OF_MONTH);
                    request();
                }
            }
        });
        ImageView rightButton = (ImageView) findViewById(R.id.AssignRight);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAnimated)
                {
                    changeAssign(v);
                    calendar.add(Calendar.DATE, 1);
                    year=calendar.get(Calendar.YEAR);
                    month=calendar.get(Calendar.MONTH)+1;
                    day=calendar.get(Calendar.DAY_OF_MONTH);
                    request();
                }
            }
        });
        //左右切换时间分配表
        ImageView newAssign = (ImageView) findViewById(R.id.newAssign);
        newAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(assign_table.this,Timesharing_edit.class);
                intent.putExtra("type","create");
                intent.putExtra("idTS",nowTable.getId());
                startActivity(intent);
            }
        });
        //创建新的时间分配事件时跳转到该timesharing_edit并且把时间分配表的id传过去
    }

    // Handler
    private static class TimeSharingHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<assign_table> mActivty;

        public TimeSharingHandler(assign_table activity) {
            mActivty = new WeakReference<assign_table>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            assign_table activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        //activity.getAssign((JSONObject) msg.obj);
                        activity.setJson((JSONObject)msg.obj);
                        Log.i("tshandler","1stepsuccess");
                        break;
                        //activity.getAssign((JSONObject) msg.obj);
                    case 0:
                        Log.i("tshandler","fail");
                        activity.reLogin();
                }
            }
        }
    }
    //静态类handler，用于将网络请求线程里的数据调入主线程，并且使用当前活动的引用防止内存泄漏
    private void reLogin()
    {
//        Intent intent =new Intent(assign_table.this,.class);
//        startActivity(intent);
    }
    //新建时间分配事件
    private void setJson(JSONObject j){
        assignJson=j;
        affair.clear();
        saffair.clear();
        //进入页面获得消息后清空数组
        try {
            nowTable=new TimeSharing(j.getIntValue("id"),j.getIntValue("idUser"),j.getString("date"),j.getIntValue("weekday"));
            //将现有的时间分配表对象赋值
            JSONArray aarray=j.getJSONArray("affair");
            for(int i=0;i<aarray.size();i++)
            {
                JSONObject jaffiar=aarray.getJSONObject(i);
                affair.add(new Affair(jaffiar.getIntValue("id"),jaffiar.getIntValue("idTS"),jaffiar.getIntValue("idLabel"),
                        jaffiar.getIntValue("satisfaction"),jaffiar.getString("name"),jaffiar.getString("tips"),
                        jaffiar.getString("timeStart"),jaffiar.getString("timeEnd"),jaffiar.getString("timeEndPlan")));
            }
            JSONArray asarray=j.getJSONArray("saffair");
            for(int i=0;i<asarray.size();i++)
            {
                JSONObject jaffiar=asarray.getJSONObject(i);
                saffair.add(new S_Affair(jaffiar.getIntValue("id"),jaffiar.getIntValue("idTS"),jaffiar.getIntValue("idS"),
                        jaffiar.getIntValue("idLabel"),jaffiar.getIntValue("satisfaction"),jaffiar.getString("isImportant"),
                        jaffiar.getString("name"),jaffiar.getString("tips"),
                        jaffiar.getString("timeStart"),jaffiar.getString("timeEnd"),jaffiar.getString("timeStartPlan"),
                        jaffiar.getString("timeEndPlan"),jaffiar.getString("timeStartAlarm"),jaffiar.getString("timeEndAlarm")));
            }
            //将affair和s_affair的list赋值
            appendAssign();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("tsaffairname","fail");
        }
    }
    private void initData() {
        calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
    }
    //初始化当前日期数据
    private void request()
    {
        //timeSharingHttp.requestByGet(new TimeSharingHandler(this),"1244");
        TimeSharingHttp assignHttp=new TimeSharingHttp(year,month,day);
        assignHttp.requestByGet(new TimeSharingHandler(this));
    }
    //请求新的时间分配表数据
    private void  changeAssign(View v){
        isAnimated=true;
        container=(LinearLayout) findViewById(R.id.nowDayAssign);
        ObjectAnimator fadeOutAlpha = ObjectAnimator.ofFloat(container, "alpha", 1.0f,0.0f);
        fadeOutAlpha.setDuration(1000);
        fadeOutAlpha.start();
        /*fadeOutAlpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                container.removeAllViews();
                appendAssign();
            }
        });*/
    }
    public void getAssign(JSONObject jsonObject)
    {
        try {
            Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
    private void addAffair(int isAffair,int k,LinearLayout assignContain){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        if(isAffair==1)
        {
            ViewGroup everyAssign =(ViewGroup)layoutInflater.inflate(R.layout.every_assign, null);
                    /*ViewGroup labelLinear=(ViewGroup)everyAssign.getChildAt(0);
                    ImageView labelImage=(ImageView)labelLinear.getChildAt(0);
                    TextView labelText=(TextView)labelLinear.getChildAt(1);
                    Label nowLabel=LabelUtil.findById(affair.get(k).getIdLabel());
                    labelImage.setImageResource(nowLabel.getImage());
                    labelText.setText(nowLabel.getName());*/
            //设置事件的标签
            ViewGroup content=(ViewGroup)everyAssign.getChildAt(2);
            TextView name=(TextView)content.getChildAt(0);
            name.setText(affair.get(k).getName());
            //设置事件的名字
            ViewGroup timeContain=(ViewGroup)content.getChildAt(1);
            TextView time=(TextView) timeContain.getChildAt(0);
            String timeIn=affair.get(k).getTimeStart()+" - "+affair.get(k).getTimeEnd();
            time.setText(timeIn);
            //设置事件的开始结束时间
            assignContain.addView(everyAssign);
            final int nowIndex=k;
            everyAssign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(assign_table.this,Timesharing_edit.class);
                    intent.putExtra("type","edit");
                    intent.putExtra("affairType","affair");
                    intent.putExtra("affair",((JSONObject) JSON.toJSON(affair.get(nowIndex))).toString());
                    Log.i("intentaffair",((JSONObject) JSON.toJSON(affair.get(nowIndex))).toString());
                    startActivity(intent);
                }
            });
            //为assign绑定事件监听，修改时间分配表，跳转并传送数据
        }
        else{
            ViewGroup everyAssign =(ViewGroup)layoutInflater.inflate(R.layout.every_assign, null);
                    /*ViewGroup labelLinear=(ViewGroup)everyAssign.getChildAt(0);
                    ImageView labelImage=(ImageView)labelLinear.getChildAt(0);
                    TextView labelText=(TextView)labelLinear.getChildAt(1);
                    labelImage.setImageResource();
                    labelText.setText();*/
            //设置事件的标签
            ViewGroup content=(ViewGroup)everyAssign.getChildAt(2);
            TextView name=(TextView)content.getChildAt(0);
            name.setText(saffair.get(k).getName());
            //设置事件的名字
            ViewGroup timeContain=(ViewGroup)content.getChildAt(1);
            TextView time=(TextView) timeContain.getChildAt(0);
            String timeIn=saffair.get(k).getTimeStart()+" - "+saffair.get(k).getTimeEnd();
            time.setText(timeIn);
            //设置事件的开始结束时间
            assignContain.addView(everyAssign);
            final int nowIndex=k;
            everyAssign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(assign_table.this,Timesharing_edit.class);
                    intent.putExtra("type","edit");
                    intent.putExtra("affairType","saffair");
                    intent.putExtra("saffair",((JSONObject) JSON.toJSON(saffair.get(nowIndex))).toString());
                    Log.i("intentsaffair",((JSONObject) JSON.toJSON(saffair.get(nowIndex))).toString());
                    startActivity(intent);
                }
            });
            //为assign绑定事件监听，修改时间分配表，跳转并传送数据
        }
    }
    public void appendAssign()
    {
        container=(LinearLayout) findViewById(R.id.nowDayAssign);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        container.removeAllViews();
        container.setAlpha(0.0f);
        LinearLayout assignDate =(LinearLayout)layoutInflater.inflate(R.layout.assign_date, null);
        //把时间分配表的基本信息加入表
        ViewGroup dateGroup=(ViewGroup) assignDate.getChildAt(1);
        TextView dateText=(TextView) dateGroup.getChildAt(0);
        TextView weekdayText=(TextView)dateGroup.getChildAt(1);
        try {
            dateText.setText(assignJson.getString("date"));
            weekdayText.setText(weekday[assignJson.getIntValue("weekday")-1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        container.addView(assignDate);
        //刷新UI界面时间分配表日期和星期几的基本信息
        LinearLayout.LayoutParams assignlp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, DensityUtil.dip2px(this,400));
        LinearLayout assignContain = new LinearLayout(this);
        assignContain.setOrientation(LinearLayout.VERTICAL);
        assignContain.setId(R.id.nowDayAssignIn);
        assignContain.setLayoutParams(assignlp);
        container.addView(assignContain);
       try {
            int i=0;
            int j=0;
            //affair和saffair已经是排好序的，显示的时候按照时间顺序将affair和saffair一起排序
            while(i<affair.size()&&j<saffair.size())
            {
                Log.i("tsjudge",String.valueOf(TimeUtil.compareOnlyTime(affair.get(i).getTimeStart(),saffair.get(j).getTimeStart())));
                if(1==TimeUtil.compareOnlyTime(affair.get(i).getTimeStart(),saffair.get(j).getTimeStart()))
                //如果当前affair的时间小于s_affair的时间,添加当前affair并且i++
                {
                    addAffair(1,i,assignContain);
                    i++;
                }
                //如果当前affair的时间小于s_affair的时间,添加当前saffair并且j++
                else{
                    addAffair(0,j,assignContain);
                    j++;
                }
            }
            while(i<affair.size())
            {
                addAffair(1,i,assignContain);
                i++;
            }
            while(j<saffair.size())
            {
                addAffair(0,j,assignContain);
                j++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ObjectAnimator fadeInAlpha = ObjectAnimator.ofFloat(container, "alpha", 0.0f,1.0f);
        //fadeInAlpha.setStartDelay(2000);
        fadeInAlpha.setDuration(1000);
        fadeInAlpha.start();
        fadeInAlpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimated=false;
            }
        });
        /*LinearLayout everyAssign =(LinearLayout)layoutInflater.inflate(R.layout.every_assign, null);
        assignContain.addView(everyAssign);
        ViewGroup everyAssign1=(ViewGroup) layoutInflater.inflate(R.layout.every_assign, null);
        ViewGroup xuexicon=(ViewGroup)everyAssign1.getChildAt(0);
        TextView xuexi2=(TextView)xuexicon.getChildAt(1);
        xuexi2.setText("吃鸡");
        assignContain.addView(everyAssign1);*/
    }
}
