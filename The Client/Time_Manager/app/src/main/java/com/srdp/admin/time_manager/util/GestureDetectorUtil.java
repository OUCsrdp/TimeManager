package com.srdp.admin.time_manager.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.srdp.admin.time_manager.R;


public class GestureDetectorUtil {
    private Activity fromC;
    private Class<?> toC;
    public GestureDetectorUtil(Activity fromC,Class<?> toC){
        this.fromC=fromC;
        this.toC=toC;
    }
    public GestureDetector getDetector()
    {
        Log.i("gesture","enter");
        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float
                    distanceY) {
                Log.i("gesdis",String.valueOf(distanceY));
                if(distanceY>10){
                    Intent intent = new Intent();
                    intent.setClass(fromC, toC);//从哪里跳到哪里
                    //fromC.startActivity(intent);
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(fromC, R.transition.in_from_bottom,R.transition.out_to_top);
                    ActivityCompat.startActivity(fromC,intent, compat.toBundle());
                }
                return true;
                //overridePendingTransition(R.transition.in_from_bottom, R.transition.out_to_top);
            }
        };

        // 2.创建一个检测器
        GestureDetector detector = new GestureDetector(fromC, listener);
        return detector;
    }
}
