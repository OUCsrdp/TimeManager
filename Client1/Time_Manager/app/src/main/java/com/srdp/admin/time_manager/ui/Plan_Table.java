package com.srdp.admin.time_manager.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.util.DensityUtil;

public class Plan_Table extends AppCompatActivity {
    //一次获取所有的日程表,使用nowDayIndex标记当天的日程表，在翻页的时候将其加一或减一,把table[nowDayIndex-1],
    //table[nowDayIndex],table[nowDayIndex+1]分别添加在plan[0],plan[1],plan[2]
    private int nowDayIndex;
    private WindowManager windowmanager;
    private int screenWidth;
    private boolean isAnimated=false;
    private ViewGroup[] plan=new ViewGroup[4];
    private boolean toNextPlan=true;
    private boolean spareLeft=true;
    private int nowIndex=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan__table);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        //隐藏默认actionbar
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
        windowmanager = this.getWindowManager();
        screenWidth=(int) DensityUtil.px2dip(this,windowmanager.getDefaultDisplay().getWidth());
        //获取屏幕宽度，用dp作单位
        ImageView rightButton=(ImageView) findViewById(R.id.planRight);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToOtherPlan(v);
            }
        });
        ImageView leftButton=(ImageView) findViewById(R.id.planLeft);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToOtherPlan(v);
            }
        });
        //切换日程表函数
        ViewGroup lastPlan=(ViewGroup)findViewById(R.id.lastDayPlan);
        LinearLayout lastView=(LinearLayout)lastPlan.getChildAt(0);
        appendPlans0(lastView);
        ViewGroup nowPlan=(ViewGroup)findViewById(R.id.nowDayPlan);
        LinearLayout nowView=(LinearLayout)nowPlan.getChildAt(0);
        appendPlans(nowView);
        ViewGroup nextPlan=(ViewGroup)findViewById(R.id.nextDayPlan);
        LinearLayout nextView=(LinearLayout)nextPlan.getChildAt(0);
        appendPlans2(nextView);
        //just for test
    }
    //other two just for test
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
        if(isAnimated) return;
        isAnimated=true;
        plan[1]=(ViewGroup) findViewById(R.id.nowDayPlan);
        plan[0]=(ViewGroup) findViewById(R.id.lastDayPlan);
        plan[2]=(ViewGroup) findViewById(R.id.nextDayPlan);
        plan[3]=(ViewGroup) findViewById(R.id.spareDayPlan);
        plan[1].bringToFront();
        //other two just for test
        LinearLayout nowView=(LinearLayout)plan[1].getChildAt(0);
        appendPlans2(nowView);
        LinearLayout lastView=(LinearLayout)plan[0].getChildAt(0);
        appendPlans(lastView);
        LinearLayout nextView=(LinearLayout)plan[2].getChildAt(0);
        appendPlans2(nextView);
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
        nextTransX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimated=false;
            }
        });

    }

}
