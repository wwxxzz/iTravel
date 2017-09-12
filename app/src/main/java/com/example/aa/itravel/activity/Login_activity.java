package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.Result;
import com.example.aa.itravel.tools.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by aa on 2017/9/3.
 */
@ContentView(R.layout.login)
public class Login_activity extends Activity {

    private Context mContext;
    String TAG = "LOGIN_Activity";
    //用户电话
    String usertel = "";
    //用户密码
    String password = "";
    //s用来保存sessionid     发送refresh请求
    String s;
    Response response;
    OkHttpClient client = new OkHttpClient();

    String path = Network.URL+"login";

   // String path1 = "http://223.3.82.239:8080/iTravel_Server_SSM/AndroidService/refresh";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String back = re.getResult();
                System.out.println(re.getResult());
                if(back.equals("true") ){
                    Log.i(TAG,"即将跳转");
                    Log.i(TAG,"sessionId"+s);
                    Toast.makeText(Login_activity.this,"登陆成功，即将跳转", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent = new Intent(mContext, Home_activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("sessionId", s);
                    startActivity(intent);
                    finish();
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
                finish();
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
                user.setUsertel(usertel);
                user.setUserpassword(password);
                Gson gson = new GsonBuilder().create();
                String content = gson.toJson(user);

                RequestBody body = RequestBody.create(JSON, content);

                Request request = new Request.Builder()
                        .url(path)
                        .post(body)
                        .build();
                OkHttpClient okhttpc = new OkHttpClient();
                Call call = okhttpc.newCall(request);
                Log.i(TAG,request.toString());
                response = call.execute();
                //获取回复的header 剥离sessionId
                Headers headers = response.headers();
                List<String> cookies = headers.values("Set-Cookie");
                String session = cookies.get(0);
                s = session.substring(0,session.indexOf(";"));

                if (response.code()==200) {
                    //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                    mHandler.obtainMessage(1, response.body().string()).sendToTarget();

                } else {
                    throw new IOException("Unexpected code:" + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            try{
//                Request request = new Request.Builder().addHeader("cookie",s).url(path1).build();
//
//                OkHttpClient okhttpc = new OkHttpClient();
//
//                Call call = okhttpc.newCall(request);
//                Response response1 = call.execute();
//
//                System.out.println(response1.body().string());
//            }catch(Exception e){
//                e.printStackTrace();
//            }
        }
    }).start();
}

}



