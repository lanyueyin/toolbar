package com.dep.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.dep.myapplication.databinding.ActivityMainBinding;
import com.dep.myapplication.mould.NormalToolbarMould;
import com.gyf.immersionbar.ImmersionBar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)//绑定上下文环境；
                .statusBarColor(R.color.tran)//设置状态栏颜色（透明）
                .statusBarDarkFont(true)
                .init();//初始化和应用

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        new NormalToolbarMould(this, binding.toolbarView)
                .setTitleText("笔果折扣卡");


    }

    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}