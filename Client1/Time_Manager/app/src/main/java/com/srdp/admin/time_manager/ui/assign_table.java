package com.srdp.admin.time_manager.ui;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.util.DensityUtil;

public class assign_table extends AppCompatActivity {
    private boolean isAnimated=false;
    private LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_table);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        //隐藏默认actionbar
        ImageView leftButton=(ImageView) findViewById(R.id.AssignLeft);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAssign(v);
            }
        });
        ImageView rightButton=(ImageView) findViewById(R.id.AssignRight);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAssign(v);
            }
        });
        //左右切换时间分配表
        ImageView newAssign=(ImageView) findViewById(R.id.newAssign);
        newAssignclick newassignc=new newAssignclick();
        newAssign.setOnClickListener(newassignc);
    }
    class newAssignclick implements View.OnClickListener{
        public void onClick(View view){

        }
    }
    private void  changeAssign(View v){
        if(isAnimated)
            return;
        isAnimated=true;
        container=(LinearLayout) findViewById(R.id.nowDayAssign);
        ObjectAnimator fadeOutAlpha = ObjectAnimator.ofFloat(container, "alpha", 1.0f,0.0f);
        fadeOutAlpha.setDuration(1000);
        fadeOutAlpha.start();
        fadeOutAlpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                container.removeAllViews();
                appendAssign();
            }
        });
       ObjectAnimator fadeInAlpha = ObjectAnimator.ofFloat(container, "alpha", 0.0f,1.0f);
        fadeInAlpha.setStartDelay(1000);
        fadeInAlpha.setDuration(1000);
        fadeInAlpha.start();
        fadeInAlpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimated=false;
            }
        });
        /*List<Animator> fadelist=null;
        fadelist.add(fadeOutAlpha);
        fadelist.add(fadeInAlpha);
        AnimatorSet fadeSet=new AnimatorSet();
        fadeSet.playSequentially(fadelist);*/
    }
    private void appendAssign()
    {
        container=(LinearLayout) findViewById(R.id.nowDayAssign);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout assignDate =(LinearLayout)layoutInflater.inflate(R.layout.assign_date, null);
        container.addView(assignDate);
        LinearLayout.LayoutParams assignlp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, DensityUtil.dip2px(this,400));
        LinearLayout assignContain = new LinearLayout(this);
        assignContain.setOrientation(LinearLayout.VERTICAL);
        assignContain.setId(R.id.nowDayAssignIn);
        assignContain.setLayoutParams(assignlp);
        container.addView(assignContain);
        //设置时间linearout的宽高并添加到页面上
        /*
        TextView dateText = new TextView(this);
        dateText.setText("2018年3月29日");
        dateText.setTextColor(Color.parseColor("#ffffff"));
        dateText.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        dateText.setGravity(Gravity.CENTER|Gravity.LEFT);
        LinearLayout.LayoutParams datelp = new LinearLayout.LayoutParams(
                0,LinearLayout.LayoutParams.FILL_PARENT);
        datelp.weight=(float)1.0;
        dateText.setLayoutParams(datelp);
        timeLinear.addView(dateText);
        //设置日期的样式内容并添加到页面上
        TextView whatdayText = new TextView(this);
        whatdayText.setText("星期四");
        whatdayText.setTextColor(Color.parseColor("#ffffff"));
        whatdayText.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        whatdayText.setGravity(Gravity.CENTER|Gravity.RIGHT);
        LinearLayout.LayoutParams whatdaylp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.FILL_PARENT);
        whatdayText.setLayoutParams(whatdaylp);
        timeLinear.addView(whatdayText);
        //设置星期几并添加到页面上
        View horLine = new View(this);
        horLine.setBackgroundColor(Color.parseColor("#ffffff"));
        LinearLayout.LayoutParams horlinelp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,DensityUtil.dip2px(this,2));
        horLine.setLayoutParams(horlinelp);
        container.addView(horLine);
        //设置水平分割线并添加到页面上*/
        LinearLayout everyAssign =(LinearLayout)layoutInflater.inflate(R.layout.every_assign, null);
        assignContain.addView(everyAssign);
        ViewGroup everyAssign1=(ViewGroup) layoutInflater.inflate(R.layout.every_assign, null);
        ViewGroup xuexicon=(ViewGroup)everyAssign1.getChildAt(0);
        TextView xuexi2=(TextView)xuexicon.getChildAt(1);
        xuexi2.setText("吃鸡");
        assignContain.addView(everyAssign1);
    }
}
