package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.Result;
import com.example.aa.itravel.tools.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by aa on 2017/9/3.
 */
@ContentView(R.layout.register)
public class Register_activity extends AppCompatActivity {

    private Context mContext;

    @ViewInject(R.id.et_phone)
    private EditText user;
    @ViewInject(R.id.et_name)
    private EditText name;
    @ViewInject(R.id.et_password)
    private EditText password;
    @ViewInject(R.id.et_password2)
    private EditText ensurepwd;
    //验证码暂时不做
//    @ViewInject(R.id.et_code)
//    private EditText code;
    OkHttpClient client = new OkHttpClient();
    String path = "http://223.3.74.248:8080/iTravel_Server_SSM/AndroidService/register";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i("REGISTER", qq);
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String back = re.getResult();
                System.out.println(re.getResult());
                if(back.equals("succeeded") ){
                    Log.i("REGISTER","即将跳转");
                    Toast.makeText(Register_activity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext,Home_activity.class);
                    startActivity(intent);
                    finish();
                }else if(back.equals("existed")){
                    Toast.makeText(Register_activity.this,"用户已存在", Toast.LENGTH_LONG).show();
                }

            }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.register);
        x.view().inject(this);
        mContext =this;
    }

    public void checkUser(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            String user_tel = user.getText().toString();
            String user_name = name.getText().toString();
            String user_psw = password.getText().toString();
            @Override
            public void run() {
                Response response = null;
                try {
                    //回调
                    User user = new User();
                    user.setUsertel(user_tel);
                    user.setUsername(user_name);
                    user.setUserpassword(user_psw);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(user);

                    RequestBody body = RequestBody.create(JSON, content);

                    Request request = new Request.Builder()
                            .url(path)
                            .post(body)
                            .build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    response = call.execute();
                    if (response.isSuccessful()) {
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        mHandler.obtainMessage(1, response.body().string()).sendToTarget();

                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    boolean checkPwd(){
        ///确认两次密码是否相同
        String user_psw1 = password.getText().toString();
        String user_psw2 = ensurepwd.getText().toString();
        Log.i("TST",user_psw1);
        Log.i("TES",user_psw2);
        if(user_psw1.equals(user_psw2)){
            Log.i("YE","suce");
            return true;
        }else{
            Log.i("NO","fail");
            return false;
        }

    }
    boolean checkCode(){
        //验证码确认
        return true;
    }

    @Event(value = {R.id.bt_register})
    private void event(View view){

       if(checkPwd()&&checkCode()){
            Log.i("I", "成功");
            checkUser();
            Log.i("I", "成功");
        }else if(!checkPwd()){
            Toast.makeText(Register_activity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Register_activity.this,"验证码错误",Toast.LENGTH_SHORT).show();
        }

    }
}
