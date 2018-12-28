package com.bignerdranch.android.smarterbutter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bignerdranch.android.smarterbutter.fragment.ButlerFragment;
import com.bignerdranch.android.smarterbutter.fragment.GirlFragment;
import com.bignerdranch.android.smarterbutter.fragment.UserFragment;
import com.bignerdranch.android.smarterbutter.fragment.WechatFragment;
import com.bignerdranch.android.smarterbutter.ui.SettingActivity;
import com.bignerdranch.android.smarterbutter.utils.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //TabLayout
private TabLayout mTabLayout;
//ViewPager
    private ViewPager mViewPager;
    //Title
    private List<String>mTitle;
    //Fragment
    private List<Fragment>mFragment;
   private FloatingActionButton fab_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去掉阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();
        L.d("Test");
        L.i("Test");
        L.w("Test");
        L.e("Test");
        L.v("Test");

    }
    //初始数据
    private void initData(){
       mTitle=new ArrayList<>();

        mTitle.add("服务管家");
        mTitle.add("微信精选");
        mTitle.add("美女社区");
        mTitle.add("个人中心 ");

        mFragment =new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());

    }
    //初始化View
    private void initView(){
        fab_setting=(FloatingActionButton)findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);
        //默认隐藏
        fab_setting.hide();
        mTabLayout=(TabLayout)findViewById(R.id.mTabLayout);
        mViewPager=(ViewPager)findViewById(R.id.mViewPager);
        //mViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG","position"+position);
                if(position==0){
                    fab_setting.hide();
                }else {
                    fab_setting.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });
        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());
        //设置适配器
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }
         //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }
            //设置标题
            @Override
            public CharSequence getPageTitle(int position){
                return mTitle.get(position);}
        });

        //绑定
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class) );
                break;
        }
    }
}
