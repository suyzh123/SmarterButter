package com.bignerdranch.android.smarterbutter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.smarterbutter.MainActivity;
import com.bignerdranch.android.smarterbutter.R;
import com.bignerdranch.android.smarterbutter.entity.MyUser;
import com.bignerdranch.android.smarterbutter.utils.ShareUtils;
import com.bignerdranch.android.smarterbutter.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

//
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //注册按钮
    private Button btn_registered;
    private EditText et_name;
    private EditText et_password;
    private Button btnLogin;
    private CheckBox keep_password;
    private TextView tv_forget;
    private CustomDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initiView();
    }
    private void initiView(){
        btn_registered=(Button)findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        et_name=(EditText)findViewById(R.id.et_name);
        et_password=findViewById(R.id.et_password);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        keep_password=findViewById(R.id.keep_password);
        tv_forget=findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);
        dialog=new CustomDialog(this,100,100,R.layout.dialog_loding,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //屏外点击无效
        dialog.setCancelable(false);



        boolean isCheck=ShareUtils.getBoolean(this,"keeppass",false);
        keep_password.setChecked(isCheck);
        if(isCheck){
       //设置密码
            et_name.setText(ShareUtils.getString(this,"name",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.btn_registered:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
            case R.id.btnLogin:
                //获取输入框的值
                String name=et_name.getText().toString().trim();
                String password=et_name.getText().toString().trim();
                //判断是否为空
                if(!TextUtils.isEmpty(name)&!TextUtils.isEmpty(password)){
                   dialog.show();
                    //登陆
                    final MyUser user=new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e){
                            dialog.dismiss();
                            //判断结果
                            if(e==null){
                                //判断邮箱是否验证
                                if(user.getEmailVerified()){
                                    //跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this,"请前往验证",Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(LoginActivity.this,"登录失败"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //假设我现在输入用户名和密码，但是我不点击登陆，而是直接退出
    @Override
    protected void onDestroy(){
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolean(this,"keeppass",keep_password.isChecked());
        //是否记住密码
        if (keep_password.isChecked()) {
            //记住用户名和密码
            ShareUtils.putString(this,"name",et_name.getText().toString().trim());
            ShareUtils.putString(this,"password",et_password.getText().toString().trim());

        }else {
            ShareUtils.deleShare(this,"name");

             ShareUtils.deleShare(this,"password");}

    }
}
