package com.example.admin.timemanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by admin on 2018/3/15.
 */

public class PopLayout extends LinearLayout{
    public PopLayout(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.popschool, this);
        TextView schoolPop = (TextView) findViewById(R.id.userSchool);
        final View popupView =LayoutInflater.from(context).inflate(R.layout.popschool, null);
        schoolPop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


            }
        });
    }
}
