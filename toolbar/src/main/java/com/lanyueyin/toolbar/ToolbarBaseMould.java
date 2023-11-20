package com.lanyueyin.toolbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

public abstract class ToolbarBaseMould implements ToolbarBaseMouldImp {
    private Toolbar toolbar;

    /**需要手动绑定toolbar的构造方法
     *
     */
    public ToolbarBaseMould(){

    }

    /**自动绑定toolbar的构造方法
     * @param toolbar
     */
    public ToolbarBaseMould(Toolbar toolbar) {
        bindToolbar(toolbar);
    }

    /**绑定toolbar
     * @param toolbar
     */
    public void bindToolbar(Toolbar toolbar){
        this.toolbar = toolbar;
        toolbar.setMould(this);
    }

    /**获取与模板板顶的toolbar
     * @return
     */
    public Toolbar getToolbar() {
        if(toolbar == null){
            throw new NullPointerException("The toolbar is not bound");
        }
        return toolbar;
    }

    /**获取Contex
     * @return
     */
    public Context getContext(){
        return getToolbar().getContext();
    }

    /**获取Resources
     * @return
     */
    public Resources getResources(){
        return getContext().getResources();
    }

    /**获取当前Context所在的Theme
     * @return
     */
    public Resources.Theme getTheme(){
        return getContext().getTheme();
    }

    /**根据颜色资源获取颜色值
     * @param colorRes @ColorRes
     * @return
     */
    public int getColor(@ColorRes int colorRes){
        return ResourcesCompat.getColor(getResources(), colorRes, getTheme());
    }

    /**根据图片资源获取颜色值
     * @param drawableRes @DrawableRes
     * @return
     */
    public Drawable getDrawable(@DrawableRes int drawableRes){
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), drawableRes, getTheme());
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }
}
