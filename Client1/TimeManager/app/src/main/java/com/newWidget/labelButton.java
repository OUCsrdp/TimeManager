package com.newWidget;

import android.widget.RelativeLayout;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.timemanager.R;


public class labelButton extends RelativeLayout {
    private ImageView imgView;
    private TextView  textView;

    public labelButton(Context context) {
        super(context,null);
    }

    public labelButton(Context context,AttributeSet attributeSet) {
        super(context, attributeSet);

        LayoutInflater.from(context).inflate(R.layout.label_button, this,true);

        this.imgView = (ImageView)findViewById(R.id.labelImage);
        this.textView = (TextView)findViewById(R.id.labelName);

        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setImgResource(int resourceID) {
        this.imgView.setImageResource(resourceID);
    }

    public void setText(String text) {
        this.textView.setText(text);
    }

    public void setTextColor(int color) {
        this.textView.setTextColor(color);
    }

    public void setTextSize(float size) {
        this.textView.setTextSize(size);
    }
}