package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.Network;
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
 * Created by aa on 2017/9/12.
 */

@ContentView(R.layout.friend_info)
public class ShowFriendInfo extends Activity{

    private Context mContext;
    String TAG = "SHOW_FRIEND_INFO_Activity";
    //s用来保存sessionid     发送refresh请求
    String session;
    Response response;

    String fname;

    OkHttpClient client = new OkHttpClient();
    String path = Network.URL+ "friendpersonalinfo";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @ViewInject(R.id.showname)
    private TextView friend_name;
    @ViewInject(R.id.showlocation)
    private TextView friend_location;
    @ViewInject(R.id.showoccupation)
    private TextView friend_career;
    @ViewInject(R.id.showemail)
    private TextView friend_email;
    @ViewInject(R.id.showphone)
    private TextView friend_phone;
    @ViewInject(R.id.showbirthday)
    private TextView friend_birth;
    @ViewInject(R.id.showsex)
    private TextView friend_sex;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.title_bar_name)
    private  TextView titlebar;
    @ViewInject(R.id.iv_right)
    private ImageView right_icon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.forgetpwd);
        x.view().inject(this);
        mContext =this;
        titlebar.setText("好友资料");
        right_icon.setImageResource(R.drawable.ic_chat);

        /*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
        /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionID");
        fname=bundle.get("friendname").toString();


        showFriendInfoRequest(fname);

    }

    private void showFriendInfoRequest(final String friendname){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    //回调
                    User user = new User();
                    user.setUsername(friendname);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(user);

                    RequestBody body = RequestBody.create(JSON, content);
                    System.out.println(content);
                    Request request = new Request.Builder().addHeader("cookie",session)
                            .url(path).post(body)
                            .build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    response = call.execute();

                    if (response.isSuccessful()) {
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        showHandler.obtainMessage(1, response.body().string()).sendToTarget();
                        //System.out.println(response.body().string());
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    private Handler showHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                //Log.i(TAG,"进入");
                //		Toast.makeText(ShowUserInfo.this,"成功", Toast.LENGTH_SHORT);
                String qq = (String) msg.obj;
                //Log.i(TAG, qq);
                Gson gson = new Gson();
                User re = gson.fromJson(qq, User.class);

                friend_name.setText(re.getUsername());
                friend_location.setText(re.getUserlocation());
                friend_career.setText(re.getUsercareer());
                friend_email.setText(re.getUseremail());
                friend_phone.setText(re.getUsertel());
                friend_birth.setText(re.getUserbirth());
                friend_sex.setText(re.getUsersex());
            }

        }
    };


    @Event(value = R.id.iv_right)
    private void event(View view) {
        Intent intent = new Intent(mContext,ChatDemoActivity.class);
        startActivity(intent);

    }

}
