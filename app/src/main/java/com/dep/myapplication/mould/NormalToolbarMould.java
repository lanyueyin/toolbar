package com.dep.myapplication.mould;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dep.myapplication.DpToPx;
import com.dep.myapplication.R;
import com.lanyueyin.toolbar.Toolbar;
import com.lanyueyin.toolbar.ToolbarBaseMould;

import java.lang.ref.WeakReference;

public class NormalToolbarMould extends ToolbarBaseMould{
    private final WeakReference<Activity> activity;
    private TextView leftView;
    private TextView titleView;
    private TextView rightView;

    public NormalToolbarMould(Activity activity, Toolbar toolbar) {
        super(toolbar);
        this.activity = new WeakReference<>(activity);
    }

    @Override
    public View initLeftMould(Context context) {
        leftView = getMould(context, "");
        leftView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        Drawable drawable = context.getDrawable(R.drawable.arrow_back);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        leftView.setCompoundDrawablesRelative(drawable, null, null, null);
        leftView.setOnClickListener(v -> {
            if(activity.get() != null) {
                activity.get().finish();
            }
        });
        return leftView;
    }

    @Override
    public View initTitleMould(Context context) {
        titleView = getMould(context, "");
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        return titleView;
    }

    @Override
    public View initRightMould(Context context) {
        rightView = getMould(context, "");
        rightView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        return rightView;
    }

    private TextView getMould(Context context, String text){
        int dp10 = DpToPx.dip2px(context, 10);
        int dp44 = DpToPx.dip2px(context, 44);
        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setText(text);
        textView.setHeight(dp44);
        textView.setPadding(dp10, 0, dp10, 0);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public NormalToolbarMould setLeftDrawable(Drawable drawable){
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        leftView.setCompoundDrawablesRelative(drawable, null, null, null);
        return this;
    }

    public NormalToolbarMould setTitleText(CharSequence text){
        titleView.setText(text);
        return this;
    }

    public NormalToolbarMould setRightText(CharSequence text){
        rightView.setText(text);
        return this;
    }

    public NormalToolbarMould setRightDrawable(Drawable drawable){
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        rightView.setCompoundDrawablesRelative(drawable, null, null, null);
        return this;
    }
}
