package com.dep.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dep.myapplication.databinding.ActivityMainBinding;
import com.dep.myapplication.mould.NormalToolbarMould;
import com.gyf.immersionbar.ImmersionBar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    //项目链接
    private final String projectLink = "https://github.com/lanyueyin/toolbar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)//绑定上下文环境；
                .statusBarColor(R.color.tran)//设置状态栏颜色（透明）
                .statusBarDarkFont(true)
                .init();//初始化和应用
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //模板绑定控件
        new NormalToolbarMould(this, findViewById(R.id.toolbarView), 1)
                .setTitleText("沉浸式布局")
                .setRightClickListener(v -> share())
                .addScrollListener(findViewById(R.id.nestedScrollView));

        //设置项目链接
        binding.linkView.setText(String.format("项目链接：%s", projectLink));
        //添加复制链接的点击事件
        binding.copyView.setOnClickListener(v -> {
            copyText(projectLink);
            showMessage("复制成功");
        });
    }

    /**
     * 分享
     */
    public void share(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, projectLink);
        intent.setType("text/plan");
        startActivity(Intent.createChooser(intent, "分享到"));
    }

    /**复制
     * @param content 复制的内容
     */
    public void copyText(String content){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("text", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }


    /**Toast弹出消息
     * @param message 消息
     */
    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}