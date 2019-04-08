package com.srdp.admin.time_manager.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
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
import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.http.AffairHttp;
import com.srdp.admin.time_manager.model.moudle.Receivers.AlarmReceiver;
import com.srdp.admin.time_manager.model.moudle.S_Affair;
import com.srdp.admin.time_manager.util.TimeUtil;
import com.srdp.admin.time_manager.util.TokenUtil;
import com.srdp.admin.time_manager.util.UserUtil;


public class CreateScheduleActivity extends AppCompatActivity {
    private Intent intent;
    private S_Affair s_affair=new S_Affair();
    private String date;
    //提醒设置
    private Switch schedule_switch;
    private LinearLayout schedule_remind_text;
    private TextView schedule_remind_time;
    private TextView schedule_remind_date;
    private boolean setAlarm=false;//是否设置提醒

    //标签选择
    private LinearLayout schedule_label_frame;
    private Button schedule_change_label;
    private TextView schedule_label_name;
    private Context context = this;

    //标签
    private RadioGroup schedule_labels;
    private RadioButton schedule_label_trans;
    private RadioButton schedule_label_cours;
    private RadioButton schedule_label_club;
    private RadioButton schedule_label_rest;
    private RadioButton schedule_label_sleep;
    private RadioButton schedule_label_life;

    // 定义显示时间控件
    private Calendar calendar; // 通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    //创建按钮
    private Button schedule_create_btn;
    private EditText schedule_name;
    private EditText schedule_ps;

    private String[] strItems = new String[]{"交通","课业","社团","休息","睡眠","生活"};


    //日程时间设置
    private TextView schedule_date;
    private TextView schedule_time_start;
    private TextView schedule_time_end;

    //提醒功能
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);
        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        //获取当前日期
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        date=year+"年"+month+"月"+day+"日";
        schedule_create_btn = (Button) findViewById(R.id.schedule_create_btn);
        schedule_name = (EditText) findViewById(R.id.schedule_name);
        schedule_ps=(EditText)findViewById(R.id.schedule_create_ps);
        schedule_date=(TextView)findViewById(R.id.schedule_date);
        //提醒设置
        schedule_switch = (Switch) findViewById(R.id.schedule_remind_btn);
        schedule_remind_text = (LinearLayout) findViewById(R.id.schedule_remind_text);
        schedule_remind_time = (TextView) findViewById(R.id.schedule_remind_time);
        schedule_remind_date = (TextView) findViewById(R.id.schedule_remind_date);


        //设置日程日期为今天的日期，默认提醒日期是今天的日期
        String todayDate=TimeUtil.getNowDate();
        schedule_remind_date.setText(todayDate);
        schedule_date.setText(todayDate);

        //如果是编辑页面,初始化设置页面
        intent=getIntent();
        if(intent.getStringExtra("type").equals("edit"))
            initPage();

        // alarmManager创建
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        // 提醒添加监听
        schedule_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setAlarm=true;
                    schedule_remind_text.setVisibility(View.VISIBLE);
                    // 锁定屏幕
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    //setContentView(R.layout.activity_create_schedule);

                    //时间选择
                    schedule_remind_date.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Calendar c_date= Calendar.getInstance();
                            new DatePickerDialog(CreateScheduleActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int month, int day) {
                                            // TODO Auto-generated method stub
                                            mYear = year;
                                            mMonth = month+1;
                                            mDay = day;
                                            // 更新日期 小于10加0
                                            schedule_remind_date.setText(new StringBuilder()
                                                    .append(mYear)
                                                    .append("年")
                                                    .append((mMonth ) < 10 ? "0"
                                                            + (mMonth ) : (mMonth ))
                                                    .append("月")
                                                    .append((mDay < 10) ? "0" + mDay : mDay).append("日"));

                                        }

                                    },
                                    c_date.get(Calendar.YEAR), c_date
                                    .get(Calendar.MONTH), c_date
                                    .get(Calendar.DAY_OF_MONTH)).show();

                        }
                    });

                    schedule_remind_time.setOnClickListener(new OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Calendar c_time= Calendar.getInstance();
                            new TimePickerDialog(CreateScheduleActivity.this,
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hour,
                                                              int minute){
                                            // TODO Auto-generated method stub
                                            mHour = hour;
                                            mMinute = minute;
                                            // 更新时间 小于10加0
                                            schedule_remind_time.setText(new StringBuilder()
                                                    .append((mHour ) < 10 ? "0"
                                                            + (mHour ) : (mHour ))
                                                    .append(":")
                                                    .append((mMinute < 10) ? "0" + mMinute : mMinute));

                                        }
                                    },
                                    c_time.get(Calendar.HOUR), c_time
                                    .get(Calendar.MINUTE),true).show();
                        }
                    });




                }else {
                    schedule_remind_text.setVisibility(View.INVISIBLE);
                    setAlarm=false;
                }
            }
        });


        //日程时间设置
        schedule_time_start = (TextView) findViewById(R.id.schedule_time_start);
        schedule_time_end = (TextView) findViewById(R.id.schedule_time_end);
        schedule_time_start.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar c_time= Calendar.getInstance();
                new TimePickerDialog(CreateScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour,
                                                  int minute){
                                // TODO Auto-generated method stub
                                mHour = hour;
                                mMinute = minute;
                                // 更新时间 小于10加0
                                schedule_time_start.setText(new StringBuilder()
                                        .append((mHour ) < 10 ? "0"
                                                + (mHour ) : (mHour ))
                                        .append(":")
                                        .append((mMinute < 10) ? "0" + mMinute : mMinute));

                            }
                        },
                        c_time.get(Calendar.HOUR), c_time
                        .get(Calendar.MINUTE),true).show();
            }
        });
        schedule_time_end.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar c_time= Calendar.getInstance();
                new TimePickerDialog(CreateScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour,
                                                  int minute){
                                // TODO Auto-generated method stub
                                mHour = hour;
                                mMinute = minute;
                                // 更新时间 小于10加0
                                schedule_time_end.setText(new StringBuilder()
                                        .append((mHour ) < 10 ? "0"
                                                + (mHour ) : (mHour ))
                                        .append(":")
                                        .append((mMinute < 10) ? "0" + mMinute : mMinute));

                            }
                        },
                        c_time.get(Calendar.HOUR), c_time
                        .get(Calendar.MINUTE),true).show();
            }
        });


        //标签设置
        schedule_change_label = (Button) findViewById(R.id.schedule_change_label);
        schedule_label_name = (TextView) findViewById(R.id.schedule_label_name);

        schedule_labels = (RadioGroup) findViewById(R.id.schedule_labels);


        schedule_change_label.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                //点击按钮弹框
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                //builder.setView(R.layout.schedule_label_dialog);
                //builder.setView(R.layout.schedule_label_dialog);
                builder.setItems(strItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        schedule_label_name.setText(strItems[i].toString());
                    }
                });
                builder.create();
                builder.show();

            }

        });

        //创建
        schedule_create_btn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                String schedule_name_edit = schedule_name.getText().toString();//获取日程名
                if(TextUtils.isEmpty(schedule_name_edit)){
                    Toast.makeText(CreateScheduleActivity.this,"日程名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                request();
//                if(schedule_remind_text.getVisibility()== View.VISIBLE){
//                    final String schedule_remind_text_edit = schedule_remind_text.getContext().toString();
//                }
            }
        });

    }
    private static class createSHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<CreateScheduleActivity> mActivty;

        public createSHandler(CreateScheduleActivity activity) {
            mActivty = new WeakReference<CreateScheduleActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CreateScheduleActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        Toast.makeText(activity,"创建成功",Toast.LENGTH_SHORT).show();
                        if(activity.setAlarm){
                            Date date = new Date();
                            Calendar calendar = Calendar.getInstance();
                            String[] date1 = activity.schedule_remind_date.getText().toString().split("年");
                            String[] date2 = date1[1].split("月");
                            String[] date3 = date2[1].split("日");
                            String[] times =  activity.schedule_remind_time.getText().toString().split(":");
                            int year = Integer.parseInt(date1[0]);
                            int month = Integer.parseInt(date2[0]);
                            int day = Integer.parseInt(date3[0]);
                            int hour = Integer.parseInt(times[0]);
                            int minute = Integer.parseInt(times[1]);
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month-1);
                            calendar.set(Calendar.DAY_OF_MONTH, day);
                            calendar.set(Calendar.HOUR_OF_DAY, hour);
                            calendar.set(Calendar.MINUTE, minute);
                            calendar.set(Calendar.SECOND, 0);
                            date = calendar.getTime();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                            Log.d("alarmtime", simpleDateFormat.format(date));
                            Intent intent = new Intent(activity,AlarmReceiver.class);
<<<<<<< HEAD
                            intent.putExtra("Time", activity.s_affair.getTimeStart());
=======
                            intent.putExtra("Time", activity.s_affair.getTimeStartPlan());
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
                            intent.putExtra("Name", activity.s_affair.getName());
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, intent,0);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                            {
                                activity.alarmManager.setWindow(AlarmManager.RTC, date.getTime(), 3000, pendingIntent);
                            }
<<<<<<< HEAD
                            else
                            {
=======
                            else {
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
                                activity.alarmManager.set(AlarmManager.RTC, date.getTime(), pendingIntent);
                            }
                        }
                        activity.finish();break;
                        //创建日程成功后弹出提示并且返回原来页面
                    case 0:
                        activity.reLogin();break;
                }
            }
        }
    }
    private void initPage()
    {
        JSONObject jaffiar= JSONObject.parseObject(intent.getStringExtra("saffair"));
        s_affair=new S_Affair(jaffiar.getIntValue("id"),jaffiar.getIntValue("idTS"),jaffiar.getIntValue("idS"),
                jaffiar.getIntValue("idLabel"),jaffiar.getIntValue("satisfaction"),jaffiar.getString("isImportant"),
                jaffiar.getString("name"),jaffiar.getString("tips"), jaffiar.getString("timeStart"),jaffiar.getString("timeEnd"),
                jaffiar.getString("timeStartPlan"), jaffiar.getString("timeEndPlan"),jaffiar.getString("timeStartAlarm"),
                jaffiar.getString("timeEndAlarm"));
        schedule_name.setText(s_affair.getName());
        schedule_ps.setText(s_affair.getTips());
        ((TextView) findViewById(R.id.schedule_time_start)).setText(s_affair.getTimeStartPlan());
        ((TextView) findViewById(R.id.schedule_time_end)).setText(s_affair.getTimeEndPlan());
        String timeStartAlarm=s_affair.getTimeStartAlarm();
        if(timeStartAlarm!=null)
<<<<<<< HEAD
            schedule_remind_time.setText(timeStartAlarm);
=======
        {
            int index=timeStartAlarm.indexOf("日");
            schedule_remind_date.setText(timeStartAlarm.substring(0,index+1));
            schedule_remind_time.setText(timeStartAlarm.substring(index+1));
        }
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
        schedule_date.setText(intent.getStringExtra("date"));
        //初始设置事件名称，事件备注，计划开始时间，计划结束时间，提醒时间

    }
    private void request()
    {
        s_affair.setName(schedule_name.getText().toString());
        s_affair.setTips(schedule_ps.getText().toString());
        s_affair.setTimeStartPlan(schedule_time_start.getText().toString());
        s_affair.setTimeEndPlan(schedule_time_end.getText().toString());
        if(setAlarm)
        {
            s_affair.setTimeStartAlarm(schedule_remind_date.getText().toString()+schedule_remind_time.getText().toString());
        }
        if(intent.getStringExtra("type").equals("create"))
        {
            s_affair.setIdS(0);
            s_affair.setIsImportant("n");
        }
        JSONObject jobject=(JSONObject) JSON.toJSON(s_affair);
        if(intent.getStringExtra("type").equals("create"))
            jobject.put("sign1", 0);
        else
            jobject.put("sign1",1);
        jobject.put("sign2",0);
        jobject.put("token", TokenUtil.getToken());
        jobject.put("date",intent.getStringExtra("date"));//plantable传送过来的时间
        jobject.put("username", UserUtil.getUser().getName());
        AffairHttp affairHttp=new AffairHttp(jobject);
        affairHttp.requestByPost(new createSHandler(this));
    }
    private void reLogin()
    {
        Intent relogin=new Intent(this,LoginActivity.class);
        startActivity(relogin);
    }
}
