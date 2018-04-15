package com.srdp.admin.time_manager.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;

import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.util.DensityUtil;

public class Timesharing_edit extends AppCompatActivity {
    private LinearLayout allSatisfyLayout;
    private String[] allHours=new String[24];
    private String[] allMinutes=new String[60];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesharing_edit);
        for(int i=0;i<24;i++)
        {
            String str = String.format("%2d",i).replace(" ", "0");
            allHours[i]=str;
        }
        for(int i=0;i<60;i++)
        {
            String str = String.format("%2d",i).replace(" ", "0");
            allMinutes[i]=str;
        }
        //为弹窗的listview设置时间
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        //隐藏原来的actionBar
        Switch theSwitch=(Switch) findViewById(R.id.turnSatisfy);
        allSatisfyLayout=(LinearLayout) findViewById(R.id.allSatisy);
        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    allSatisfyLayout.setVisibility(View.VISIBLE);
                } else {
                    allSatisfyLayout.setVisibility(View.INVISIBLE);
                }

            }
        });
        // 根据开关的变化显示或者隐藏满意度选择
    }
    public void setSatisfy(View view)
    {
        int theIndex=1;
        switch(view.getId())
        {
            case R.id.satisfy1:
                theIndex=1;break;
            case R.id.satisfy2:
                theIndex=2;break;
            case R.id.satisfy3:
                theIndex=3;break;
            case R.id.satisfy4:
                theIndex=4;break;
            case R.id.satisfy5:
                theIndex=5;break;
        }
        ViewGroup allSatisfy=(ViewGroup) findViewById(R.id.allSatisy);
        //Drawable minipoint = getResources().getDrawable(R.drawable.minipoint);
        for(int i=0;i<theIndex;i++)
        {
            ImageView theImage=(ImageView) allSatisfy.getChildAt(i);
            theImage.setImageResource(R.drawable.minipoint);
            //theImage.setImageDrawable(minipoint);
        }
        for(int i=theIndex;i<5;i++)
        {
            ImageView theImage=(ImageView) allSatisfy.getChildAt(i);
            theImage.setImageResource(R.drawable.minipoint2);
        }
    }
    public void showChangeTime(View view)
    {
        /*AlertDialog.Builder timeDialog =
                new AlertDialog.Builder(Timesharing_edit.this);*/
        AlertDialog timeDialog = new AlertDialog.Builder(Timesharing_edit.this,R.style.timeAlertDialog).create();
        final View dialogView = LayoutInflater.from(Timesharing_edit.this)
                .inflate(R.layout.settime_dialog,null);
        //为弹出层的布局添加xml
        ArrayAdapter<String> adapterHour=new ArrayAdapter<String>(Timesharing_edit.this,R.layout.list_time,allHours);

        timeDialog.setView(dialogView);ListView listHour=(ListView) dialogView.findViewById(R.id.PopSetHour);
        listHour.setAdapter(adapterHour);
        ArrayAdapter<String> adapterMin=new ArrayAdapter<String>(Timesharing_edit.this,R.layout.list_time,allMinutes);
        ListView listMin=(ListView) dialogView.findViewById(R.id.PopSetMin);
        listMin.setAdapter(adapterMin);
        //为listview添加时间
        timeDialog.show();
        //为弹出层添加布局并展示
       Window dialogWindow = timeDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = DensityUtil.dip2px(Timesharing_edit.this,200);//宽高可设置具体大小
        lp.height =DensityUtil.dip2px(Timesharing_edit.this,180);
        timeDialog.getWindow().setAttributes(lp);

    }
}
