package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by aa on 2017/9/3.
 */
@ContentView(R.layout.register)
public class Register_activity extends AppCompatActivity {

    private Context mContext;

    @ViewInject(R.id.et_phone)
    private String user;
    @ViewInject(R.id.et_name)
    private String name;
    @ViewInject(R.id.et_password)
    private String password;
    @ViewInject(R.id.et_password2)
    private String ensurepwd;
    @ViewInject(R.id.et_code)
    private String code;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.register);
        x.view().inject(this);
        mContext =this;
    }

    boolean checkUser(){
        //确认账号是否存在
        //建新类
        return true;
    }
    boolean checkPwd(){
        ///确认两次密码是否相同
        return true;
    }
    boolean checkCode(){
        //验证码确认
        return true;
    }

    @Event(value = {R.id.bt_register})
    private void event(View view){
        if(checkUser()&&checkPwd()&&checkCode()){
            //建新类
            //存储用户数据
            Toast.makeText(Register_activity.this,"注册成功",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext,Home_activity.class);
            startActivity(intent);
        }else if (!checkUser()){
            Toast.makeText(Register_activity.this,"账户已注册",Toast.LENGTH_SHORT).show();
        }else if(!checkPwd()){
            Toast.makeText(Register_activity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Register_activity.this,"验证码错误",Toast.LENGTH_SHORT).show();
        }

    }
}
