package com.example.admin.timemanager;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class Timesharing_edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesharing_edit);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        Switch theSwitch=findViewById(R.id.turnSatisfy);
        final ViewGroup allSatisfy=(ViewGroup) findViewById(R.id.allSatisy);
        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    allSatisfy.setVisibility(View.VISIBLE);
                } else {
                    allSatisfy.setVisibility(View.INVISIBLE);
                }

            }
        });
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
    }
}
