package com.dep.myapplication.mould;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dep.myapplication.DpToPx;
import com.dep.myapplication.R;
import com.lanyueyin.toolbar.Toolbar;
import com.lanyueyin.toolbar.ToolbarBaseMould;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;

public class NormalToolbarMould extends ToolbarBaseMould{
    private final Activity activity;
    private TextView leftView;
    private TextView titleView;
    private TextView[] rightView;

    private int rightViewCount;

    public NormalToolbarMould(Activity activity, Toolbar toolbar) {
        super(toolbar);
        this.activity = activity;
    }

    public NormalToolbarMould(Activity activity, Toolbar toolbar, int rightViewCount) {
        //当调用super方法时，会自动绑定到toolbar，同时会去创建控件，因此传递进来的参数会在控件之后才能赋值给属性
        //比如此处的rightViewCount参数，是用来指定右边控件需要创建几个控件的，但是控件创建时，属性this.rightViewCount未被初始化，因此就不能用上
        //若创建控件需要某些参数，请注释掉super()方法，参数初始化完成后，再手动调用Toolbar.setMould();方法
        //super(toolbar);

        this.activity = activity;
        this.rightViewCount = rightViewCount;
        bindToolbar(toolbar);
    }

    @Override
    public View initLeftMould(Context context) {
        leftView = getMould(context, "");
        leftView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        leftView.setCompoundDrawablesRelative(TintDrawable(getDrawable(R.drawable.back), Color.WHITE), null, null, null);
        leftView.setOnClickListener(v -> activity.finish());
        return leftView;
    }

    @Override
    public View initTitleMould(Context context) {
        titleView = getMould(context, "");
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        titleView.setTextColor(Color.WHITE);
        return titleView;
    }

    @Override
    public View initRightMould(Context context) {
        Log.d("dddd", rightViewCount+"");
        LinearLayout linearLayout = new LinearLayout(context);
        rightView = new TextView[rightViewCount];
        for(int i=0; i<rightViewCount; i++) {
            rightView[i] = getMould(context, "");
            rightView[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            rightView[i].setCompoundDrawablesRelative(TintDrawable(getDrawable(R.drawable.share), Color.WHITE), null, null, null);
            linearLayout.addView(rightView[i]);
        }
        return linearLayout;
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


    public NormalToolbarMould setTitleText(CharSequence text){
        titleView.setText(text);
        return this;
    }

    public NormalToolbarMould setRightClickListener(View.OnClickListener onClickListener){
        rightView[0].setOnClickListener(onClickListener);
        return this;
    }

    public NormalToolbarMould setRightClickListener(View.OnClickListener onClickListener, int index){
        rightView[index].setOnClickListener(onClickListener);
        return this;
    }

    /**图标和文字以及toolbar的背景颜色随滚动高度变化
     * @param scrollView 滚动控件
     * @return
     */
    public NormalToolbarMould addScrollListener(NestedScrollView scrollView){
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, i, y, i2, i3) -> {
            int effectiveRange = Math.min(y, getToolbar().getHeight());//选取有效范围 0 - getHeight()之间
            float changeRate = 1f * effectiveRange / getToolbar().getHeight();//变化了百分之几，除法运算有小数，乘以-1f是为了保留小数，从而提高精度
            int alpha = (int) (changeRate * 255);
            //View.setBackgroundColor()方法在鸿蒙4.0上，设置argb值时，a为0的情况下，偶发出现不透明，但鸿蒙3.0正常
            getToolbar().setBackground(new ColorDrawable(Color.argb(Math.min(alpha, 255), 255, 255, 255)));

            //计算图标的颜色，夜间模式,图标只需要白色
            int rgb = Math.max(0, 255 - (int) (changeRate * 255));
            int color = Color.rgb(rgb, rgb, rgb);

            TintDrawable(leftView.getCompoundDrawablesRelative()[0], color);
            for(TextView rightView : this.rightView) {
                TintDrawable(rightView.getCompoundDrawablesRelative()[0], color);
            }
            titleView.setTextColor(color);

        });
        return this;
    }


    /**给指定的drawable进行着色,并设置宽高
     * @param drawable 指定图标
     * @param tintColor 颜色
     */
    private Drawable TintDrawable(Drawable drawable, int tintColor){
        if(drawable != null) {
            //若图片本身有设置宽高，则优先选用设置的宽高，否则使用默认宽高，即图片本身的宽高
            int width = drawable.getBounds().right > 0 ? drawable.getBounds().right : drawable.getIntrinsicWidth();
            int height = drawable.getBounds().bottom > 0 ? drawable.getBounds().bottom : drawable.getIntrinsicHeight();
            drawable = DrawableCompat.wrap(drawable).mutate();
            drawable.setBounds(0,0, width, height);
            DrawableCompat.setTint(drawable, tintColor);
            return drawable;
        }
        return null;
    }
}
