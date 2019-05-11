package com.srdp.admin.time_manager.ui;

import java.lang.String;
import java.util.Arrays;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;
import android.content.Context;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;


import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.User;
import com.srdp.admin.time_manager.util.UserUtil;

import cn.forward.androids.views.ScrollPickerView;

public class RegisterSchoolActivity extends AppCompatActivity {

    private Button register_next_btn;

    private EditText register_school;
    private EditText register_major;
    private EditText register_gpa;

    private ScrollPickerView select_school;

    private Context mContext;
    private User user=new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_school);

        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }


        register_next_btn = (Button) findViewById(R.id.register_next_btn);

        register_school = (EditText) findViewById(R.id.register_school);
        register_major = (EditText) findViewById(R.id.register_major);
        register_gpa = (EditText) findViewById(R.id.register_gpa);

        select_school = (ScrollPickerView) findViewById(R.id.select_school);



        register_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转注册下一页以及验证
                final String register_school_edit = register_school.getText().toString();//获取学校
                final String register_major_edit = register_major.getText().toString();//获取专业
                final String register_gpa_edit = register_gpa.getText().toString();//获取gpa

                if(TextUtils.isEmpty(register_school_edit)){
                    Toast.makeText(RegisterSchoolActivity.this,"学校不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(register_major_edit)){
                    Toast.makeText(RegisterSchoolActivity.this,"专业不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(register_gpa_edit)){
                    Toast.makeText(RegisterSchoolActivity.this,"gpa不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                //去往继续注册页
                Intent register_next_page = new Intent(RegisterSchoolActivity.this,RegisterActivity.class);
                register_next_page.putExtra("school",user.getSchool());
                register_next_page.putExtra("major",user.getMajor());
                register_next_page.putExtra("gpa",user.getGPA());
                startActivity(register_next_page);
            }
        });
//        register_school.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //选择学校
//                String[] school = {"中国政法大学","中国石油大学","中国海洋大学","中国科学院大学","中国地质大学"};
//                select_school.setData(Arrays.asList(school));
//                select_school.setVisibility(View.VISIBLE);
//                select_school.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //放置学校选项
//                        register_school.setText(select_school.getSelectedItem().toString());
//                        select_school.setVisibility(View.INVISIBLE);
//                        user.setSchool(select_school.getSelectedItem().toString());
//                    }
//                });
//            }
//        });
        register_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择专业
                String[] major = {"电子信息工程","物理学","计算机科学与技术","海洋技术","保密管理"};
                select_school.setData(Arrays.asList(major));
                select_school.setVisibility(View.VISIBLE);
                select_school.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //放置专业选项
                        register_major.setText(select_school.getSelectedItem().toString());
                        select_school.setVisibility(View.INVISIBLE);
                        user.setMajor(select_school.getSelectedItem().toString());
                    }
                });
            }
        });


    }





}
