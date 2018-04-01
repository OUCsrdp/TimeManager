package com.example.admin.timemanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Person_Infor extends AppCompatActivity {

    private TextView schoolPop;
    private String[] schools={"中国海洋大学","华东师范大学","中国科技大学","中国海洋大学","华东师范大学","华东师范大学"};
   // private WindowManager wm;
    //private DisplayMetrics dm;//使用这2个参数来获取屏幕宽高
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__infor);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        //wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //dm = new DisplayMetrics();
        //  构建一个popupwindow的布局
        schoolPop = (TextView) findViewById(R.id.userSchool);
        schoolPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog schoolDialog = new AlertDialog.Builder(Person_Infor.this,R.style.timeAlertDialog).create();
                final View dialogView = LayoutInflater.from(Person_Infor.this)
                        .inflate(R.layout.popschool,null);
                //为弹出层的布局添加xml
                ArrayAdapter<String> adapterSchool=new ArrayAdapter<String>(Person_Infor.this,R.layout.list_school,schools);
                ListView listSchool=(ListView) dialogView.findViewById(R.id.listSchool);
                listSchool.setAdapter(adapterSchool);
                //为listview添加时间
                schoolDialog.setView(dialogView);
                schoolDialog.show();
                Window dialogWindow = schoolDialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = DensityUtil.dip2px(Person_Infor.this,200);//宽高可设置具体大小
                lp.height =DensityUtil.dip2px(Person_Infor.this,180);
                schoolDialog.getWindow().setAttributes(lp);
            }
        });

    }
}
