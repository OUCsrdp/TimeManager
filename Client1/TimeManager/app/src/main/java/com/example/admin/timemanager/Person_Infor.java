package com.example.admin.timemanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Person_Infor extends AppCompatActivity {

    private TextView schoolPop;
    private String[] datas={"中国海洋大学","华东师范大学","中国科技大学","南京理工大学"};
    private WindowManager wm;
    private DisplayMetrics dm;//使用这2个参数来获取屏幕宽高
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__infor);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        //  构建一个popupwindow的布局
        schoolPop = (TextView) findViewById(R.id.userSchool);
        schoolPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = Person_Infor.this.getLayoutInflater().inflate(R.layout.popschool, null);
                ListView lsvMore = (ListView) popupView.findViewById(R.id.lsvMore);
                String[] datas={"中国海洋大学","华东师范大学","中国科技大学","南京理工大学"};
                lsvMore.setAdapter(new ArrayAdapter<String>(Person_Infor.this,R.layout.list_school, datas));
                PopupWindow window = new PopupWindow(popupView, 400, 400);
                //window.setAnimationStyle(R.style.popup_window_anim);
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                window.setFocusable(true);
                wm.getDefaultDisplay().getMetrics(dm);
                int popWidth=(int)(dm.widthPixels/dm.density);
                int popHeight= (int)(dm.heightPixels/dm.density);
                window.showAtLocation(popupView, Gravity.NO_GRAVITY,(popWidth-400)/2, (popHeight-400)/2);
                //设置可以触摸弹出框以外的区域
                window.setOutsideTouchable(true);
                // 更新popupwindow的状态
                window.update();
                //以下拉的方式显示，并且可以设置显示的位置
                //window.showAsDropDown(schoolPop, 50, 50);
            }
        });

    }
}
