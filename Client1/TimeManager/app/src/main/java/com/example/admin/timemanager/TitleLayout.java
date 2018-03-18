package com.example.admin.timemanager;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.navi, this);
        ImageView naviBack=(ImageView) findViewById(R.id.naviBack);
        naviBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        });
    }
}