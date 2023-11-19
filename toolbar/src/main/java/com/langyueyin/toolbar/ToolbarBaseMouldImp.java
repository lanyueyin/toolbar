package com.langyueyin.toolbar;

import android.content.Context;
import android.view.View;

public interface ToolbarBaseMouldImp {
    /**设置左边控件的初始化模板
     * @param context 上下文
     * @return
     */
    View initLeftMould(Context context);

    /**设置标题控件的初始化模板
     * @param context 上下文
     * @return
     */
    View initTitleMould(Context context);

    /**设置右边控件的初始化模板
     * @param context 上下文
     * @return 设置好的模板
     */
    View initRightMould(Context context);
}
