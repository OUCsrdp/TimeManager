package com.srdp.admin.time_manager.widget.AnalysisChart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.srdp.admin.time_manager.R;

/**
 * Created by admin on 2018/8/31.
 */

public class ChartButton extends LinearLayout {
    private boolean isWeekday=true;
    private Context nowContext;
    //初始化接口变量
    private TransforWeekday transforWeekday=null;
    //定义一个接口
    public interface TransforWeekday{
        public void onTransforWeekday(boolean isWeekday);
    }
    //TransforWeekday transforWeekday=null;
    //接口回调
    public void setonClick(TransforWeekday transforWeekday)
    {
        this.transforWeekday=transforWeekday;
    }
    public ChartButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        nowContext=context;
        LayoutInflater.from(context).inflate(R.layout.chart_button, this);
        final Button weekday=(Button)findViewById(R.id.ChartButtonWeekday);
        final Button weekend=(Button)findViewById(R.id.ChartButtonWeekend);
        weekday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isWeekday)
                {
                    isWeekday=true;
                    changeBackBtoA(weekday,weekend);
                    changeData(isWeekday);
                }
            }
        });
        weekend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWeekday)
                {
                    isWeekday=false;
                    changeBackBtoA(weekend,weekday);
                    changeData(isWeekday);
                }
            }
        });
    }
    private void changeBackBtoA(Button a,Button b)
    {
        a.setBackgroundResource(R.drawable.chart_button_back_select);
        a.setTextColor(nowContext.getResources().getColor(R.color.blueBackground));
        b.setBackgroundResource(R.drawable.chart_button_back_unselect);
        b.setTextColor(nowContext.getResources().getColor(R.color.chartWhite));
    }
    private void changeData(boolean isWeekday)
    {
        transforWeekday.onTransforWeekday(isWeekday);
    }
    public boolean getWeekDay(){
        return isWeekday;
    }

}
