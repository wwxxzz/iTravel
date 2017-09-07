package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
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
import java.util.Map;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.aa.itravel.R.color.button;

/**
 * Created by aa on 2017/9/3.
 */
@ContentView(R.layout.login)
public class Login_activity extends Activity {

    private Context mContext;

    //用户电话
    String usertel = "";
    //用户密码
    String password = "";

    Response response;
    OkHttpClient client = new OkHttpClient();
    String path = "http://223.3.82.239:8080/iTravel_Server_SSM/AndroidService/login";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i("WUJIE", qq);
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String back = re.getResult();
                System.out.println(re.getResult());
                if(back.equals("true") ){
                    Log.i("LOGIN","即将跳转");
                    Intent intent = new Intent();
                    intent = new Intent(mContext, Home_activity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Login_activity.this,"用户名或密码错误", Toast.LENGTH_LONG).show();
                }

            }

        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.login);
        x.view().inject(this);
        mContext = this;

    }

    @Event(value = {R.id.bt_login, R.id.bt_forgetpwd, R.id.bt_register})
    private void request(View view) {
        //获取电话输入框的文本
        EditText editText1 = (EditText) findViewById(R.id.et_phone);
        usertel = editText1.getText().toString();
        //获取密码文本
        EditText editText2 = (EditText) findViewById(R.id.et_password);
        password = editText2.getText().toString();
        Intent intent;
        switch (view.getId()) {
            case R.id.bt_login:
                postRequest(usertel,password);
                break;
            case R.id.bt_forgetpwd:
                intent = new Intent(mContext, Forgetpwd.class);
                startActivity(intent);
                break;
            case R.id.bt_register:
                intent = new Intent(mContext, Register_activity.class);
                startActivity(intent);
                break;
        }
    }
private void postRequest(String name,String pwd)  {


    //新建一个线程，用于得到服务器响应的参数
    new Thread(new Runnable() {
        @Override
        public void run() {
            Response response = null;
            try {
                //回调
                User user = new User();
                user.setUsetel(usertel);
                user.setPassword(password);
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
                if (response.code()==200) {
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

}



