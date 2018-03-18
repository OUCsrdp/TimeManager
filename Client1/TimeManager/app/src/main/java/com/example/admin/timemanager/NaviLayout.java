package com.example.admin.timemanager;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class NaviLayout extends LinearLayout {
    public NaviLayout(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.navi2, this);
        ImageView naviShow=(ImageView) findViewById(R.id.naviShow);
        /*naviShow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)

            }
        });*/
    }
}