package com.dep.myapplication.mould;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dep.myapplication.DpToPx;
import com.dep.myapplication.R;
import com.dep.myapplication.databinding.CourseToolViewBinding;
import com.lanyueyin.toolbar.ToolbarBaseMouldImp;

import androidx.databinding.DataBindingUtil;

public class ToolbarDefaultMould implements ToolbarBaseMouldImp {
    private TextView leftView;

    @Override
    public View initLeftMould(Context context) {
        leftView = getMould(context, "");
        leftView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        Drawable drawable = context.getDrawable(R.drawable.back);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        leftView.setCompoundDrawablesRelative(drawable, null, null, null);
        return leftView;
    }

    @Override
    public View initTitleMould(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_tool_view, null);
        CourseToolViewBinding binding = DataBindingUtil.bind(view);
        View.OnClickListener onClickListener = v -> {
            if(binding.answerModeView == v && v.getTag() == null){
                changeMode(v.getContext(), binding.slideView, true);
                binding.rememberModeView.setTag(null);
            }else if(binding.rememberModeView == v && v.getTag() == null){
                changeMode(v.getContext(), binding.slideView, false);
                binding.answerModeView.setTag(null);
            }
        };
        binding.answerModeView.setOnClickListener(onClickListener);
        binding.rememberModeView.setOnClickListener(onClickListener);
        return view;
    }

    private void changeMode(Context context, View view, boolean isAnswerMode){
        //滑块的动画
        int dp90 = DpToPx.dip2px(context, 90);
        float start = isAnswerMode  ? dp90 : 0;
        float end = isAnswerMode  ? 0 : dp90;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", start, end);
        animator.setDuration(200);
        animator.start();
    }

    @Override
    public View initRightMould(Context context) {
        TextView leftView = getMould(context, "");
        leftView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        Drawable drawable = context.getDrawable(R.drawable.share);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        leftView.setCompoundDrawablesRelative(drawable, null, null, null);
        return leftView;
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

    public ToolbarDefaultMould setLeftFinishActivity(Activity activity){
        leftView.setOnClickListener(v -> activity.finish());
        return this;
    }
}
