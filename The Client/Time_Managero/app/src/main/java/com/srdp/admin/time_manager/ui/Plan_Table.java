package com.srdp.admin.time_manager.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.http.PlanHttp;
import com.srdp.admin.time_manager.model.moudle.S_Affair;
import com.srdp.admin.time_manager.util.DensityUtil;
import com.srdp.admin.time_manager.util.TimeUtil;
import com.srdp.admin.time_manager.util.TokenUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Plan_Table extends AppCompatActivity {
    //一次获取所有的日程表,使用nowDayIndex标记当天的日程表，在翻页的时候将其加一或减一,把table[nowDayIndex-1],
    //table[nowDayIndex],table[nowDayIndex+1]分别添加在plan[0],plan[1],plan[2]
    private int nowDayIndex;
    private WindowManager windowmanager;
    private int screenWidth;
    private boolean isAnimated=true;
    private ViewGroup[] plan=new ViewGroup[4];
    private boolean toNextPlan=true;
    private boolean spareLeft=true;
    private int nowIndex=1;
    private int month;
    private int year;
    private int day;//
    private Calendar calendar;
    private Calendar realCalendar;
    private String date;
    private JSONObject[] threedays=new JSONObject[3];
    private String[] weekday={"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    private int lastClick;//1表示上一次按了向右，0表示初始，-1表示向左
    //表示这三天的jsonobject对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan__table);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        //隐藏默认actionbar
        TokenUtil.initToken(this);
        calendar= TimeUtil.createCalendar();
        realCalendar=TimeUtil.createCalendar();
        date=TimeUtil.getDate(realCalendar,0);//获取当前日期
        setCalendar(0);
        request("initial1");
        setCalendar(1);
        request("initial2");
        calendar.add(Calendar.DATE, -1);
        setCalendar(-1);
        request("initial0");
        calendar.add(Calendar.DATE, 1);
        setCalendar(0);
        //请求前一天，今天，后一天三天的日程表
        ObjectAnimator alpha1 = ObjectAnimator.ofFloat(findViewById(R.id.planTitle), "alpha", 0.0f,1.0f);
        alpha1.setDuration(800);
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(findViewById(R.id.planNail), "alpha", 0.0f,1.0f);
        alpha2.setDuration(100);
        ObjectAnimator alpha3 = ObjectAnimator.ofFloat(findViewById(R.id.planAll), "alpha", 0.0f,1.0f);
        alpha3.setDuration(200);
        ObjectAnimator alpha4 = ObjectAnimator.ofFloat(findViewById(R.id.planLeftRight), "alpha", 0.0f,1.0f);
        alpha4.setDuration(200);
        AnimatorSet createSet=new AnimatorSet();
        createSet.playSequentially(alpha1,alpha2,alpha3);
        createSet.playTogether(alpha3,alpha4);
        createSet.start();
        //初始页面淡入出现
        windowmanager = this.getWindowManager();
        screenWidth=(int) DensityUtil.px2dip(this,windowmanager.getDefaultDisplay().getWidth());
        //获取屏幕宽度，用dp作单位
        ImageView rightButton=(ImageView) findViewById(R.id.planRight);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("plananimated:",""+isAnimated);
                if(!isAnimated)
                {
                    isAnimated=true;
                    realCalendar.add(Calendar.DATE,1);
                    date=TimeUtil.getDate(realCalendar,0);
                    //设置日期加一
                    if(lastClick==1)
                        setCalendar(1);
                    else if(lastClick==0)
                        setCalendar(2);
                    else
                        setCalendar(3);
                    request("next");
                    Log.i("plandate+",year+"年"+month+"月"+day);
                    changeToOtherPlan(v);
                    lastClick=1;
                }

            }
        });
        ImageView leftButton=(ImageView) findViewById(R.id.planLeft);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAnimated)
                {
                    isAnimated=true;
                    realCalendar.add(Calendar.DATE,-1);
                    date=TimeUtil.getDate(realCalendar,0);
                    //设置日期-1
                    if(lastClick==-1)
                        setCalendar(-1);
                    else if(lastClick==0)
                        setCalendar(-2);
                    else
                        setCalendar(-3);
                    request("last");
                    Log.i("plandate-",year+"年"+month+"月"+day);
                    changeToOtherPlan(v);
                    lastClick=-1;
                }
            }
        });
        //切换日程表函数

        /*ViewGroup lastPlan=(ViewGroup)findViewById(R.id.lastDayPlan);
        LinearLayout lastView=(LinearLayout)lastPlan.getChildAt(0);
        appendPlans0(lastView);
        ViewGroup nowPlan=(ViewGroup)findViewById(R.id.nowDayPlan);
        LinearLayout nowView=(LinearLayout)nowPlan.getChildAt(0);
        appendPlans(nowView);
        ViewGroup nextPlan=(ViewGroup)findViewById(R.id.nextDayPlan);
        LinearLayout nextView=(LinearLayout)nextPlan.getChildAt(0);
        appendPlans2(nextView);*/
        //just for test
    }
    private static class PlanTableHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<Plan_Table> mActivty;

        public PlanTableHandler(Plan_Table activity) {
            mActivty = new WeakReference<Plan_Table>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Plan_Table activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 2:
                    case 1:activity.setJson((JSONObject)msg.obj);break;
                    case 0:activity.reLogin();break;
                }
            }
        }
    }
    //静态类handler，用于将网络请求线程里的数据调入主线程，并且使用当前活动的引用防止内存泄漏
    private void setCalendar(int addDay){
        calendar.add(Calendar.DATE, addDay);
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
        //设置完年月日后将calendar设置为原来值
    }
    //初始化当前日期数据
    private void request(String TimeSign)
    {
        PlanHttp planHttp=new PlanHttp(year,month,day,TimeSign);
        //需要请求的年月日和时间标志（上一页，下一页，初次进入页面）
       planHttp.requestByGet(new PlanTableHandler(this));
    }
    //请求某天的日程表数据
    private void reLogin(){}
    //尚未写的重新登录函数
    //other two just for test
    private void setJson(JSONObject j)
    {
        String timeSign=j.getString("TimeSign");
        if(timeSign.equals("next"))
        {
            for(int i=0;i<2;i++)
                threedays[i]=threedays[i+1];
            //if(j.getString("status").equals("success"))
                threedays[2]=j;
            /*LinearLayout nextView=(LinearLayout)((ViewGroup) findViewById(R.id.lastDayPlan)).getChildAt(0);
            appendPlan(threedays[2],nextView);*/
        }
        else if(timeSign.equals("last"))
        {
            for(int i=2;i>0;i--)
            threedays[i]=threedays[i-1];
            //if(j.getString("status").equals("success"))
                threedays[0]=j;
            /*LinearLayout lastView=(LinearLayout)((ViewGroup) findViewById(R.id.lastDayPlan)).getChildAt(0);
            appendPlan(threedays[0],lastView);*/
        }
        //翻页获取后一天和前一天日程的时候对jsonObject数组赋值
        else
        {
            isAnimated=false;
            threedays[Integer.parseInt(timeSign.substring(7,8))]=j;
            if(timeSign.substring(7,8).equals("1"))
            {
                ViewGroup nowPlan=(ViewGroup) findViewById(R.id.nowDayPlan);
                LinearLayout nowView=(LinearLayout) nowPlan.getChildAt(0);
                appendPlan(threedays[1],nowView,0);
            }
        }

        //第一次进入页面时对数组对应项赋值

    }
    private void appendPlan(JSONObject j,LinearLayout vg,int changeDate)
    {
        vg.removeAllViews();
        if(j.getString("status").equals("fail"))
            return;
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout planDate =(LinearLayout)layoutInflater.inflate(R.layout.plan_date, null);
        ((TextView)(planDate.getChildAt(0))).setText(j.getString("date"));
        //((TextView)(planDate.getChildAt(0))).setText(j.getString("date"));
        //((TextView)(planDate.getChildAt(0))).setText(TimeUtil.getDate(realCalendar,changeDate));
        ((TextView)(planDate.getChildAt(1))).setText(weekday[j.getIntValue("weekday")-1]);
        vg.addView(planDate);
        //在plan_table中添加日期相关信息
        JSONArray asarray=j.getJSONArray("saffair");
        //List<S_Affair> saffair=new ArrayList<S_Affair>();
        /*for(int i=0;i<asarray.size();i++)
        {
            JSONObject jaffiar=asarray.getJSONObject(i);
            saffair.add(new S_Affair(jaffiar.getIntValue("id"),jaffiar.getIntValue("idTS"),jaffiar.getIntValue("idS"),
                    jaffiar.getIntValue("idLabel"),jaffiar.getIntValue("satisfaction"),jaffiar.getString("isImportant"),
                    jaffiar.getString("name"),jaffiar.getString("tips"),
                    jaffiar.getString("timeStart"),jaffiar.getString("timeEnd"),jaffiar.getString("timeStartPlan"),
                    jaffiar.getString("timeEndPlan"),jaffiar.getString("timeStartAlarm"),jaffiar.getString("timeEndAlarm")));
        }*/
        for(int i=0;i<asarray.size();i++)
        {
            final JSONObject jobject=asarray.getJSONObject(i);
            ViewGroup everyPlan =(ViewGroup) layoutInflater.inflate(R.layout.every_plan, null);
            ViewGroup planInfor=(ViewGroup) everyPlan.getChildAt(1);
            ((TextView)planInfor.getChildAt(0)).setText(jobject.getString("name"));
            ((TextView)planInfor.getChildAt(1)).setText(jobject.getString("timeStartPlan")+" - "+jobject.getString("timeEndPlan"));
            everyPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(Plan_Table.this,CreateScheduleActivity.class);
                    intent.putExtra("type","edit");
                    intent.putExtra("date",date);
                    intent.putExtra("saffair",jobject.toString());
                    startActivity(intent);
                }
            });
            vg.addView(everyPlan);
        }
        LinearLayout newPlan =(LinearLayout) layoutInflater.inflate(R.layout.new_plan, null);
        newPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Plan_Table.this,CreateScheduleActivity.class);
                intent.putExtra("type","create");
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
        vg.addView(newPlan);
        //把新建日程按钮加入日程表中

    }
    private void appendPlans0(LinearLayout vg)
    {
        vg.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout planDate =(LinearLayout)layoutInflater.inflate(R.layout.plan_date, null);
        vg.addView(planDate);
        LinearLayout everyPlan =(LinearLayout)layoutInflater.inflate(R.layout.every_plan, null);
        vg.addView(everyPlan);
    }
    private void appendPlans(LinearLayout vg)
    {
        vg.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout planDate =(LinearLayout)layoutInflater.inflate(R.layout.plan_date, null);
        vg.addView(planDate);
        LinearLayout everyPlan =(LinearLayout)layoutInflater.inflate(R.layout.every_plan, null);
        vg.addView(everyPlan);
        ViewGroup everyPlan1=(ViewGroup) layoutInflater.inflate(R.layout.every_plan, null);
        ImageView planLabel=(ImageView) everyPlan1.getChildAt(0);
        planLabel.setImageResource(R.drawable.tryhead);
        vg.addView(everyPlan1);
    }
    private void appendPlans2(LinearLayout vg)
    {
        vg.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout planDate =(LinearLayout)layoutInflater.inflate(R.layout.plan_date, null);
        vg.addView(planDate);
        ViewGroup everyPlan1=(ViewGroup) layoutInflater.inflate(R.layout.every_plan, null);
        ImageView planLabel=(ImageView) everyPlan1.getChildAt(0);
        planLabel.setImageResource(R.drawable.tryhead);
        vg.addView(everyPlan1);
    }
    private void  changeToOtherPlan(View v)
    {
        plan[1]=(ViewGroup) findViewById(R.id.nowDayPlan);
        plan[0]=(ViewGroup) findViewById(R.id.lastDayPlan);
        plan[2]=(ViewGroup) findViewById(R.id.nextDayPlan);
        plan[3]=(ViewGroup) findViewById(R.id.spareDayPlan);
        plan[1].bringToFront();
        //other two just for test
        LinearLayout lastView=(LinearLayout)plan[0].getChildAt(0);
        appendPlan(threedays[0],lastView,-1);
        LinearLayout nowView=(LinearLayout)plan[1].getChildAt(0);
        appendPlan(threedays[1],nowView,0);
       LinearLayout nextView=(LinearLayout)plan[2].getChildAt(0);
        appendPlan(threedays[2],nextView,1);
        switch(v.getId())
        {
            case R.id.planLeft:
                toNextPlan=false;nowIndex=2;break;
            case R.id.planRight:
                toNextPlan=true;nowIndex=1;break;
        }
        int centerTo=DensityUtil.dip2px(this,130+(screenWidth-240)/2);
        int sideTo=DensityUtil.dip2px(this,110);
        int toTop=DensityUtil.dip2px(this,50);
        int spareBig=DensityUtil.dip2px(this,screenWidth+240);
        int spareSmall=DensityUtil.dip2px(this,screenWidth+130);
        ObjectAnimator lastTransX=null,nowTransX=null,nextTransX=null,spareTransX=null,lastTransY=null,nowTransY=null,nextTransY=null,lastAlpha=null;
        if(toNextPlan) {
            plan[2].bringToFront();
            lastTransX = ObjectAnimator.ofFloat(plan[0], "translationX",0,-sideTo);
            nowTransX = ObjectAnimator.ofFloat(plan[1], "translationX",0,-centerTo);
            nextTransX = ObjectAnimator.ofFloat(plan[2], "translationX",0,-centerTo);
            lastTransY = ObjectAnimator.ofFloat(plan[0], "translationY",0,0);
            nowTransY = ObjectAnimator.ofFloat(plan[1], "translationY",0,-toTop);
            nextTransY = ObjectAnimator.ofFloat(plan[2], "translationY",0,toTop);
            spareTransX = ObjectAnimator.ofFloat(plan[3], "translationX",spareBig,spareSmall);
            lastAlpha = ObjectAnimator.ofFloat(plan[2].getChildAt(1), "alpha", 1.0f,0.0f);
            plan[0].getChildAt(1).setAlpha(1.0f);
        }
        else{
            plan[0].bringToFront();
            lastTransX = ObjectAnimator.ofFloat(plan[0], "translationX",0,centerTo);
            nowTransX = ObjectAnimator.ofFloat(plan[1], "translationX",0,centerTo);
            nextTransX = ObjectAnimator.ofFloat(plan[2], "translationX",0,sideTo);
            lastTransY = ObjectAnimator.ofFloat(plan[0], "translationY",0,toTop);
            nowTransY = ObjectAnimator.ofFloat(plan[1], "translationY",0,-toTop);
            nextTransY = ObjectAnimator.ofFloat(plan[2], "translationY",0,0);
            spareTransX = ObjectAnimator.ofFloat(plan[3], "translationX",0,sideTo);
            lastAlpha = ObjectAnimator.ofFloat(plan[0].getChildAt(1), "alpha", 1.0f,0.0f);
            plan[2].getChildAt(1).setAlpha(1.0f);
        }
        ObjectAnimator nowAlpha = ObjectAnimator.ofFloat(plan[1].getChildAt(1), "alpha", 0.0f,1.0f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(lastTransX,nowTransX,nextTransX,spareTransX,lastTransY,nowTransY,nextTransY,lastAlpha,nowAlpha);
        set.setDuration(1000);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimated=false;
            }
        });

    }

}
