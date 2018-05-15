package com.srdp.admin.time_manager.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;

import android.content.pm.ActivityInfo;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.http.AffairHttp;
import com.srdp.admin.time_manager.model.moudle.Affair;
import com.srdp.admin.time_manager.model.moudle.MyApplication;
import com.srdp.admin.time_manager.model.moudle.S_Affair;
import com.srdp.admin.time_manager.util.TokenUtil;

public class CreateTimingActivity extends AppCompatActivity {
    private int isAffair=1;//为1则是Affair,为0则是S_Affair
    private MyApplication application;//Application是andorid单例模式的一个类，创建继承自Application的MyApplication类
    //提醒设置
    private S_Affair SAffair;//从服务器端获取的同名事件，可能是当前计时事件
    private Switch timing_switch;
    private LinearLayout timing_remind_text;
    private TextView timing_remind_time;
    private TextView timing_remind_date;

    //标签选择
    private LinearLayout timing_label_frame;
    private Button timing_change_label;
    private TextView timing_label_name;
    private Context context = this;

    //标签
    private RadioGroup timing_labels;
    private RadioButton timing_label_trans;
    private RadioButton timing_label_cours;
    private RadioButton timing_label_club;
    private RadioButton timing_label_rest;
    private RadioButton timing_label_sleep;
    private RadioButton timing_label_life;

    // 定义显示时间控件
    private Calendar calendar; // 通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    //创建按钮
    private Button timing_create_btn;
    private EditText timing_name;
    private EditText timing_ps;

    private String[] strItems = new String[]{"交通","课业","社团","休息","睡眠","生活"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timing);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        application=(MyApplication)this.getApplicationContext();
        //隐藏默认actionbar
        timing_name = (EditText) findViewById(R.id.timing_name);
        /*timing_name.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    EditText view=(EditText)v;
                    if(!view.getText().equals(""))
                    {
                        Toast.makeText(CreateTimingActivity.this,"是否是日程",Toast.LENGTH_SHORT).show();
                    }
                    // 此处为失去焦点时的处理内容
                }
            }
        });*/

        //提醒设置
        timing_switch = (Switch) findViewById(R.id.timing_remind_btn);
        timing_remind_text = (LinearLayout) findViewById(R.id.timing_remind_text);
        timing_remind_time = (TextView) findViewById(R.id.timing_remind_time);
        timing_remind_date = (TextView) findViewById(R.id.timing_remind_date);

        // 添加监听
        timing_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    timing_remind_text.setVisibility(View.VISIBLE);
                    // 锁定屏幕
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


                    //时间选择
                    timing_remind_date.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Calendar c_date= Calendar.getInstance();
                            new DatePickerDialog(CreateTimingActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int month, int day) {
                                            // TODO Auto-generated method stub
                                            mYear = year;
                                            mMonth = month;
                                            mDay = day;
                                            // 更新日期 小于10加0
                                            timing_remind_date.setText(new StringBuilder()
                                                    .append(mYear)
                                                    .append("年")
                                                    .append((mMonth + 1) < 10 ? "0"
                                                            + (mMonth + 1) : (mMonth + 1))
                                                    .append("月")
                                                    .append((mDay < 10) ? "0" + mDay : mDay).append("日 "));

                                        }

                                    },
                                    c_date.get(Calendar.YEAR), c_date
                                    .get(Calendar.MONTH), c_date
                                    .get(Calendar.DAY_OF_MONTH)).show();

                        }
                    });

                    timing_remind_time.setOnClickListener(new OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Calendar c_time= Calendar.getInstance();
                            new TimePickerDialog(CreateTimingActivity.this,
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hour,
                                                              int minute){
                                            // TODO Auto-generated method stub
                                            mHour = hour;
                                            mMinute = minute;
                                            // 更新时间 小于10加0
                                            timing_remind_time.setText(new StringBuilder()
                                                    .append((mHour + 1) < 10 ? "0"
                                                            + (mHour + 1) : (mHour + 1))
                                                    .append(":")
                                                    .append((mMinute < 10) ? "0" + mMinute : mMinute));

                                        }
                                    },
                                    c_time.get(Calendar.HOUR), c_time
                                    .get(Calendar.MINUTE),true).show();
                        }
                    });




                }else {
                    timing_remind_text.setVisibility(View.INVISIBLE);
                }
            }
        });

        //标签设置
        timing_change_label = (Button) findViewById(R.id.timing_change_label);
        timing_label_name = (TextView) findViewById(R.id.timing_label_name);

        //timing_labels = (RadioGroup) findViewById(R.id.timing_labels);


        timing_change_label.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击按钮弹框
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //builder.setView(R.layout.schedule_label_dialog);
                //builder.setView(R.layout.schedule_label_dialog);
                builder.setItems(strItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        timing_label_name.setText(strItems[i].toString());
                    }
                });
                builder.create();
                builder.show();
            }
        });


        //创建
        timing_create_btn = (Button) findViewById(R.id.timing_create_btn);
        timing_ps = (EditText) findViewById(R.id.timing_ps);
        timing_create_btn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                String schedule_name_edit = timing_name.getText().toString();//获取事件名
                if(TextUtils.isEmpty(schedule_name_edit)){
                    Toast.makeText(CreateTimingActivity.this,"事件名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                request(schedule_name_edit);
            }
        });



    }
    private static class GuessAffairHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<CreateTimingActivity> mActivty;

        public GuessAffairHandler(CreateTimingActivity activity) {
            mActivty = new WeakReference<CreateTimingActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CreateTimingActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 4:
                        activity.setSAffair((JSONObject)msg.obj);break;
                    case 0:
                        activity.reLogin();
                }
            }
        }
    }
    private void request(String affairName)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);
        //获取日期和时间信息
        JSONObject jsonObj=new JSONObject();
        jsonObj.put("token", TokenUtil.getToken());
        jsonObj.put("name",affairName);
        jsonObj.put("date",year+"年"+month+"月"+day+"日");
        jsonObj.put("time",hour+":"+min);
        jsonObj.put("sign1",-2);
        //发出请求，获得同名日程信息
        AffairHttp affairHttp=new AffairHttp(jsonObj);
        affairHttp.requestByPost(new GuessAffairHandler(CreateTimingActivity.this));
    }
    //静态类handler，用于将网络请求线程里的数据调入主线程，并且使用当前活动的引用防止内存泄漏
    private void setSAffair(JSONObject jaffiar)
    {
        if(jaffiar.getIntValue("hasSAffair")==0)
        {
            createTiming(jaffiar.getIntValue("hasSAffair"));
            return;
        }
        SAffair=new S_Affair(jaffiar.getIntValue("id"),jaffiar.getIntValue("idTS"),jaffiar.getIntValue("idS"),
                jaffiar.getIntValue("idLabel"),jaffiar.getIntValue("satisfaction"),jaffiar.getString("isImportant"),
                jaffiar.getString("name"),jaffiar.getString("tips"),
                jaffiar.getString("timeStart"),jaffiar.getString("timeEnd"),jaffiar.getString("timeStartPlan"),
                jaffiar.getString("timeEndPlan"),jaffiar.getString("timeStartAlarm"),jaffiar.getString("timeEndAlarm"));
        createTiming(jaffiar.getIntValue("hasSAffair"));
    }
    private void createTiming(int hasSAffair )
    {
        String schedule_name_edit = timing_name.getText().toString();//获取事件名
        String schedule_ps_edit = timing_ps.getText().toString();//获取备注
        String time_end_plan=timing_remind_time.getText().toString();//获取结束时间提醒
        //Toast.makeText(CreateTimingActivity.this,schedule_name_edit,Toast.LENGTH_SHORT).show();

        //String schedule_exist ="高等数学";

//                if(timing_remind_text.getVisibility()== View.VISIBLE){
//                    final String timing_remind_text_edit = timing_remind_text.getContext().toString();
//                }
            if(hasSAffair==1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("我们在你的日程表中发现了同名事件，是否为相同事件？").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击确定按钮处理
                        isAffair = 0;
                        //获取application对象
                        application.setIsAffair(0);
                        application.setTimingSAffair(SAffair);
                        //把application全局变量里的现在计时事件赋值
                        dialog.cancel();
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击取消按钮处理
                        dialog.cancel();
                    }
                });
                builder.create();
                builder.show();
            }
        if(isAffair==1)
        {
            Affair affair=new Affair();
            affair.setName(schedule_name_edit);
            affair.setIdLabel(1);//设置id标签为1
            affair.setTimeEndPlan(time_end_plan);
            affair.setTips(schedule_ps_edit);
            affair.setIdTS(0);//设置所属时间分配表为0
            MyApplication application = (MyApplication)this.getApplicationContext();
            //获取application对象
            application.setIsAffair(1);
            application.setTimingAffair(affair);
        }
        //跳转回首页
        Intent jump_to_index=new Intent(this,Index_Timing.class);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);
        jump_to_index.putExtra("startTime",hour+":"+min);
        startActivity(jump_to_index);
    }
    private void reLogin()
    {
        Intent relogin=new Intent(this,LoginActivity.class);
        startActivity(relogin);
    }
}
