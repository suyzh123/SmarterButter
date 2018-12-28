package com.bignerdranch.android.smarterbutter.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bignerdranch.android.smarterbutter.MainActivity;
import com.bignerdranch.android.smarterbutter.R;
import com.bignerdranch.android.smarterbutter.utils.ShareUtils;
import com.bignerdranch.android.smarterbutter.utils.StaticClass;
import com.bignerdranch.android.smarterbutter.utils.UtilTools;
/*1
1.延时2000ms
2.判断程序是否是第一次运行
3.自定义字体
4.Activity全屏主题
 */


public class SplashActivity extends AppCompatActivity {
    private TextView tv_splash;
    //handler 延时作用；what过滤
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                  ;
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));

                    }else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                    }
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }
    //初始化View
    private void initView(){
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        tv_splash=(TextView) findViewById(R.id.tv_splash);
        //设置字体
        UtilTools.setFont(this,tv_splash);


    }
    //判断程序是否是第一次运行
    private boolean isFirst() {
        boolean isFirst=ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            //是第一次
            return true;
        }else {
            return false;
        }
    }
//禁止返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
