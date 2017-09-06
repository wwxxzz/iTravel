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
@ContentView(R.layout.login)
public class Login_activity extends AppCompatActivity {

    private Context mContext;

    @ViewInject(R.id.et_phone)
    private String username;
    @ViewInject(R.id.et_password)
    private String password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.login);
        x.view().inject(this);
        mContext =this;
    }
    @Event(value = {R.id.bt_login,R.id.bt_forgetpwd,R.id.bt_register})
    private void request(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.bt_login:
                Toast.makeText(Login_activity.this,"登录成功，用户名："+username+"密码"+password,Toast.LENGTH_SHORT).show();
                intent = new Intent(mContext,Home_activity.class);
                startActivity(intent);
                break;
            case R.id.bt_forgetpwd:
                intent = new Intent(mContext, Forgetpwd.class);
                startActivity(intent);;
                break;
            case R.id.bt_register:
                intent = new Intent(mContext, Register_activity.class);
                startActivity(intent);
                break;
        }
    }

}
