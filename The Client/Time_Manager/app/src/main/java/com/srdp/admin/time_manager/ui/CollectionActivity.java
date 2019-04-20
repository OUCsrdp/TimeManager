package com.srdp.admin.time_manager.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.srdp.admin.time_manager.R;

public class CollectionActivity extends AppCompatActivity {

    private TextView content_school;//自己的学校
    private TextView content_major;//自己的专业
    private ImageView content_icon;//自己的头像
    private TextView content_name;//自己的昵称

    private SwipeRefreshLayout listSwiplayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
    }
}
