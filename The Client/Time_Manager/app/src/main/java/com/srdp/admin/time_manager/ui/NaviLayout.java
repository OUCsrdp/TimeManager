package com.srdp.admin.time_manager.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.util.DensityUtil;


public class NaviLayout extends LinearLayout {
    private FrameLayout showedNavi;
    private Context nowContext;
    private Intent theIntent;
    private int clickTimes=0;
    public NaviLayout(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        nowContext=context;
        LayoutInflater.from(context).inflate(R.layout.navi2, this);
        theIntent=new  Intent();
        ImageView naviShow=(ImageView) findViewById(R.id.naviShow);
        showedNavi=(FrameLayout) findViewById(R.id.showedNavi);
        naviShow.setOnClickListener(new OnClickListener() {
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
        TextView navi1=(TextView) findViewById(R.id.naviMyAssign);
        navi1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                theIntent.setClass(nowContext,assign_table.class);
                nowContext.startActivity(theIntent);
            }
        });
        TextView navi2=(TextView) findViewById(R.id.naviMyPlan);
        navi2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                theIntent.setClass(nowContext,Plan_Table.class);
                nowContext.startActivity(theIntent);
            }
        });
        TextView navi6=(TextView) findViewById(R.id.naviMyCenter);
        navi6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                theIntent.setClass(nowContext,Person_Infor.class);
                nowContext.startActivity(theIntent);
            }
        });
    }
    private void showWholeNavi()
    {

        ValueAnimator changeWid = ValueAnimator.ofInt(showedNavi.getLayoutParams().width, DensityUtil.dip2px(this.getContext(),249));
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