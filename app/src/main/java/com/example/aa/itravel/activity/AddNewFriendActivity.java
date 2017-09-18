package com.example.aa.itravel.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.MessageBuffer;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.Result;
import com.example.aa.itravel.tools.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@ContentView(R.layout.activity_add_new_friend)
public class AddNewFriendActivity extends AppCompatActivity {
    private Context mContext;

    String searchFriendname=" ";
    String TAG = "AddNewFriend_Activity";
    //s用来保存sessionid     发送refresh请求
    String session;
    String path = Network.URL+ "searchfriend";
    String path1 = Network.URL+ "addfriend";
    String path2 = Network.URL+ "refreshfriend";
    String path3 = Network.URL+ "commitfriend";
    String path4 = Network.URL+ "refusefriend";

    Response response;
    OkHttpClient client = new OkHttpClient();
    private static List<MessageBuffer>  friendinfo_list =new ArrayList<MessageBuffer>();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @ViewInject(R.id.title_bar_name)
    private TextView title;

    @ViewInject(R.id.search_friend)
    private EditText searchfriend;
    @ViewInject(R.id.search_result)
    private RelativeLayout resultfriend;
    @ViewInject(R.id.newfriend_head)
    private ImageView showfriendphoto;
    String friendphoto;
    @ViewInject(R.id.newfriend_user)
    private TextView showfriendname;

    @ViewInject(R.id.newfriend_request)
    private LinearLayout.LayoutParams requests;

    @ViewInject(R.id.frrequest_01)
    private RelativeLayout request1;
    @ViewInject(R.id.frrequest_user_01)
    private TextView reqFriend1;//请求添加的好友的名字

    @ViewInject(R.id.frrequest_02)
    private RelativeLayout request2;
    @ViewInject(R.id.frrequest_user_02)
    private TextView reqFriend2;//请求添加的好友的名字


    @ViewInject(R.id.frrequest_03)
    private RelativeLayout request3;
    @ViewInject(R.id.frrequest_user_03)
    private TextView reqFriend3;//请求添加的好友的名字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_new_friend);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.friend);
        mContext =this;
        x.view().inject(this);
         /*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
        /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionID");
        title.setText("添加好友");

        showFriendRequest();

    }

    private void showFriendRequest(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    //回调
//                    User user = new User();
//                    user.setUsername(friendname);
//                    Gson gson = new GsonBuilder().create();
//                    String content = gson.toJson(user);
//
//                    RequestBody body = RequestBody.create(JSON, content);

                    Request request = new Request.Builder().addHeader("cookie",session)
                            .url(path2)//.post(body)
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


    private Handler mmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                //Log.i(TAG,"进入");
                //		Toast.makeText(ShowUserInfo.this,"成功", Toast.LENGTH_SHORT);
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                User friend = gson.fromJson(qq,User.class);


                //Topic top = gson.fromJson(qq, Topic.class);
                //topic_theme.setText(top.getTheme());
                //topic_content.setText(top.getTopiccontent());
                //topicID = top.getTopicid();
//				topicID.setText(top.getTopicid());
                Log.i(TAG,"进入函数");
                //showComment();
                //showCollection();

            }
        }
    };

    @Event(value = {R.id.bt_search_sure,R.id.frrequest_accept_01,R.id.frrequest_refuse_01,R.id.newfriend_add })
    private void event(View view){
        //获取搜索好友id
        searchFriendname = searchfriend.getText().toString();

        switch (view.getId()){
            case R.id.bt_search_sure:
                //通过editText内容检索
                searchRequest(searchFriendname);
                //result.setVisibility(View.VISIBLE);
                //requests.setMargins(0,0,0,0);
                break;
            case R.id.newfriend_add:
                //发送好友请求
                addRequest(searchFriendname);
                //Toast.makeText(AddNewFriendActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.frrequest_accept_01:
                //同意添加好友
                commitRequest(reqFriend1.getText().toString());
                break;
            case R.id.frrequest_refuse_01:
                //拒绝添加好友
                refuseRequest(reqFriend1.getText().toString());
                break;
            case R.id.frrequest_accept_02:
                //同意添加好友
                commitRequest(reqFriend2.getText().toString());
                break;
            case R.id.frrequest_refuse_02:
                //拒绝添加好友
                refuseRequest(reqFriend1.getText().toString());
                break;

        }
    }

    private void searchRequest(final String friendname)  {

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

                    Request request = new Request.Builder().addHeader("cookie",session)
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

    private void addRequest(final String friendname)  {

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

                    Request request = new Request.Builder().addHeader("cookie",session).url(path1).post(body).build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    response = call.execute();
                    if (response.isSuccessful()) {
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        aHandler.obtainMessage(1, response.body().string()).sendToTarget();

                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void commitRequest(final String friendname)  {

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

                    Request request = new Request.Builder().addHeader("cookie",session).url(path3).post(body).build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    response = call.execute();
                    if (response.isSuccessful()) {
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        cHandler.obtainMessage(1, response.body().string()).sendToTarget();

                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void refuseRequest(final String friendname)  {

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

                    Request request = new Request.Builder().addHeader("cookie",session).url(path4).post(body).build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    response = call.execute();
                    if (response.isSuccessful()) {
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        rHandler.obtainMessage(1, response.body().string()).sendToTarget();

                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                User re = gson.fromJson(qq, User.class);
                String friendname=re.getUsername();

                if(friendname!=null){
                    Log.i(TAG,"即将跳转");
                    Toast.makeText(AddNewFriendActivity.this,"查找到该好友", Toast.LENGTH_SHORT).show();
                    resultfriend.setVisibility(View.VISIBLE);
                    showfriendname.setText(re.getUsername());
                    friendphoto = re.getUserphoto();
                    getUserImg(friendphoto);

                }else {
                    Toast.makeText(AddNewFriendActivity.this,"该好友不存在", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
    public void getUserImg(final String userphoto){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    URL url = new URL(Network.IMGURL + userphoto);
                    Bitmap pp = BitmapFactory.decodeStream(url.openStream());
                    android.os.Message msg = new android.os.Message();
                    //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                    System.out.println("进入handler");
                    topicHandler.obtainMessage(1, pp).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private Handler topicHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                Bitmap bmp = (Bitmap) msg.obj;
                showfriendphoto.setImageBitmap(bmp);
            }
        }
    };
    private Handler aHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String result=re.getResult();

                if(result.equals("true")){
                    Toast.makeText(AddNewFriendActivity.this,"发送请求成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AddNewFriendActivity.this,"发送失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    private Handler showHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                Log.i(TAG,"进入");
                String qq = (String) msg.obj;
                Log.i("ADDADD", qq);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<MessageBuffer>>(){}.getType();
                friendinfo_list = gson.fromJson(qq,type);
                if(friendinfo_list!= null){
                    if(friendinfo_list.size()==1){
                        reqFriend1.setText(friendinfo_list.get(0).getFromusername());
                        request1.setVisibility(View.VISIBLE);
                    }
                    if(friendinfo_list.size()==2){
                        reqFriend2.setText(friendinfo_list.get(1).getFromusername());
                        request2.setVisibility(View.VISIBLE);
                    }
                    if(friendinfo_list.size()==3){
                        reqFriend3.setText(friendinfo_list.get(2).getFromusername());
                        request3.setVisibility(View.VISIBLE);
                    }
                }
                //Log.i("111",friendinfo_list.get(0).getFromusername());
            }
        }
    };


    private Handler cHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String result=re.getResult();

                if(result.equals("true")){
                    Toast.makeText(AddNewFriendActivity.this,"同意添加好友", Toast.LENGTH_SHORT).show();
                    request1.setVisibility(View.GONE);
                }else {
                    Toast.makeText(AddNewFriendActivity.this,"同意添加好友失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    private Handler rHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String result=re.getResult();

                if(result.equals("true")){
                    Toast.makeText(AddNewFriendActivity.this,"拒绝添加好友", Toast.LENGTH_SHORT).show();
                    request1.setVisibility(View.GONE);
                }else {
                    Toast.makeText(AddNewFriendActivity.this,"拒绝添加好友失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

}
