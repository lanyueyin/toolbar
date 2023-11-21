package com.lanyueyin.toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.nibushi.toolbar.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

public class Toolbar extends LinearLayout {
    public static final String LEFT = "left";
    public static final String TITLE = "title";
    public static final String RIGHT = "right";

    private Map<String, LinearLayout> targetViewMap;

    //通过android:labelFor属性设置的控件
    private View labelView;
    //上一次设置labelFor中的控件的margin时，记录下的标题栏高度
    private int lastMeasuredHeight;

    //标题栏的title是否需要居中
    private boolean isTitleInCenter = true;

    public Toolbar(Context context) {
        super(context);
        init();
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        //根据全屏状态设置，是否需要添加一个内边距保持不被系统状态栏遮挡
        setFitsSystemWindows(isWindowTranslucentStatusTrue());
        setElevation(1);
        setOrientation(LinearLayout.VERTICAL);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.tool_bar_view, this, false);
        addView(view);

        targetViewMap = new HashMap<>();
        targetViewMap.put(LEFT, view.findViewById(R.id.leftLayout));
        targetViewMap.put(TITLE, view.findViewById(R.id.titleLayout));
        targetViewMap.put(RIGHT, view.findViewById(R.id.rightLayout));
    }

    /**设置一个模版，根据这个模板创建控件
     * @param mould 模板，需要继承{@link ToolbarBaseMould}接口实现
     */
    protected void setMould(ToolbarBaseMould mould){
        if(mould == null) return;

        for(String target : targetViewMap.keySet()){
            //清空已有的控件
            targetViewMap.get(target).removeAllViews();
            //获取子控件
            View childView = null;
            if(LEFT.equals(target)){
                childView = mould.initLeftMould(getContext());
            }else if(TITLE.equals(target)){
                childView = mould.initTitleMould(getContext());
            }else if(RIGHT.equals(target)){
                childView = mould.initRightMould(getContext());
            }
            //添加子控件
            if(childView != null) {
                targetViewMap.get(target).setVisibility(VISIBLE);
                targetViewMap.get(target).addView(childView);
            }else {
                targetViewMap.get(target).setVisibility(GONE);
            }
        }
    }

    /**是否是全屏
     * @return
     */
    private boolean isWindowTranslucentStatusTrue() {
        int[] attr = new int[]{android.R.attr.windowTranslucentStatus};
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attr);
        boolean windowTranslucentStatus = array.getBoolean(0  , false);
        array.recycle();
        return windowTranslucentStatus;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setPadding(0, getPaddingTop(), 0, getPaddingBottom());
        layout();
        addMarginToLabelView();
    }

    @Override
    public void setLabelFor(int id) {
        super.setLabelFor(id);
        addMarginToLabelView();
    }

    /**查找到xml里labelFor属性中的控件
     * @param view 查找范围
     * @return
     */
    private View findLabelView(View view){
        if(view.getParent() instanceof View){
            View parentView = (View) view.getParent();
            View labelView = parentView.findViewById(getLabelFor());
            if(labelView != null){
                return labelView;
            }else {
                return findLabelView(parentView);
            }
        }
        return null;
    }
    /**为xml里labelFor属性中的控件添加标题栏高度的外边距，防止标题栏遮挡
     *
     */
    private void addMarginToLabelView(){
        //查找到xml里labelFor属性中的控件
        if(labelView == null){
            labelView = findLabelView(this);
        }
        if(labelView == null) return;

        MarginLayoutParams layoutParams = (MarginLayoutParams) labelView.getLayoutParams();
        //去除上一次添加的标题栏高度，就是当前labelFor属性中的控件的实际外边距
        layoutParams.topMargin = layoutParams.topMargin - lastMeasuredHeight;
        //重新增加一个标题栏高度的外边距，使得标题栏不遮挡住这个控件
        layoutParams.topMargin = layoutParams.topMargin + getMeasuredHeight();
        labelView.setLayoutParams(layoutParams);
        //记录本次添加的标题栏高度
        lastMeasuredHeight = getMeasuredHeight();
    }

    /**给titleLayout设置左内边距或右内边距，使得titleLayout中的内容一直居中
     *
     */
    private void layout(){
        if(!isTitleInCenter) return;

        //给titleLayout设置左内边距或右内边距，使得titleLayout中的内容一直居中
        int disparity = targetViewMap.get(LEFT).getMeasuredWidth() - targetViewMap.get(RIGHT).getMeasuredWidth();
        targetViewMap.get(TITLE).setPadding(disparity < 0 ? Math.abs(disparity) : 0, 0, Math.max(disparity, 0), 0);
    }

    /**标题是否居中显示
     * @param titleInCenter true 或 false
     * @return
     */
    public Toolbar setTitleInCenter(boolean titleInCenter) {
        isTitleInCenter = titleInCenter;
        return this;
    }

    /**为指定目标添加内边距
     * @param target 可选值{@link Toolbar#LEFT}，{@link Toolbar#TITLE}，{@link Toolbar#RIGHT}
     * @param left 左边距
     * @param top 上边距
     * @param right 右边距
     * @param bottom 下边距
     * @return
     */
    public Toolbar setTargetPadding(String target, int left, int top, int right, int bottom){
        targetViewMap.get(target).setPadding(left, top, right, bottom);
        return this;
    }

    /**向对应目标添加组件
     * @param target target 可选值{@link Toolbar#LEFT}，{@link Toolbar#TITLE}，{@link Toolbar#RIGHT}
     * @param view 组件
     * @return
     */
    public Toolbar addTargetView(String target, View view){
        if (getChildCount() > 0) {
            throw new IllegalStateException("Toolbar can host only one direct child");
        }

        targetViewMap.get(target).addView(view);
        targetViewMap.get(target).setVisibility(VISIBLE);
        return this;
    }

    /**移除目标中的组件
     * @param target 可选值{@link Toolbar#LEFT}，{@link Toolbar#TITLE}，{@link Toolbar#RIGHT}
     * @return
     */
    public Toolbar removeTargetView(String target){
        targetViewMap.get(target).removeAllViews();
        targetViewMap.get(target).setVisibility(GONE);
        return this;
    }

    /**设置目标中的组件的排列方向
     * @param target 可选值{@link Toolbar#LEFT}，{@link Toolbar#TITLE}，{@link Toolbar#RIGHT}
     * @param orientation 可选值{@link Toolbar#HORIZONTAL}，{@link Toolbar#VERTICAL}
     * @return
     */
    public Toolbar setTargetOrientation(String target, @OrientationMode int orientation){
        targetViewMap.get(target).setOrientation(orientation);
        return this;
    }
    /** @hide */
    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {}
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
}
