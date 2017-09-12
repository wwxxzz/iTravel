package com.example.aa.itravel.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.Network;
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

@ContentView(R.layout.activity_add_new_friend)
public class AddNewFriendActivity extends AppCompatActivity {
    private Context mContext;

    String searchFriendname=" ";
    String TAG = "AddNewFriend_Activity";
    //s用来保存sessionid     发送refresh请求
    String session;
    String path = Network.URL+ "searchfriend";
    String path1 = Network.URL+ "addfriend";
    String path2 = Network.URL+ "refresh";

    Response response;
    OkHttpClient client = new OkHttpClient();


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @ViewInject(R.id.title_bar_name)
    private TextView title;

    @ViewInject(R.id.search_friend)
    private EditText searchfriend;
    @ViewInject(R.id.search_result)
    private RelativeLayout result;

    @ViewInject(R.id.newfriend_user)
    private TextView showfriendname;

    @ViewInject(R.id.newfriend_request)
    private LinearLayout.LayoutParams requests;
    @ViewInject(R.id.frrequest_01)
    private RelativeLayout request1;
    @ViewInject(R.id.frrequest_02)
    private RelativeLayout request2;
    @ViewInject(R.id.frrequest_03)
    private RelativeLayout request3;

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
                    Request request = new Request.Builder().addHeader("cookie",session)
                            .url(path2)
                            .build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    response = call.execute();

                    if (response.isSuccessful()) {
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        //mHandler.obtainMessage(1, response.body().string()).sendToTarget();
                        System.out.println(response.body().string());
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
                result.setVisibility(View.VISIBLE);
                //requests.setMargins(0,0,0,0);
                break;
            case R.id.newfriend_add:
                //添加到好友列表
                addRequest(searchFriendname);
                //Toast.makeText(AddNewFriendActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.frrequest_accept_01:
                ;//添加到好友列表
                request1.setVisibility(View.GONE);
                break;
            case R.id.frrequest_refuse_01:
                ;//从好友列表中删除
                request1.setVisibility(View.GONE);
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

                    Request request = new Request.Builder()
                            .url(path)
                            .post(body)
                            .build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    response = call.execute();
//                    //获取回复的header 剥离sessionId
//                    Headers headers = response.headers();
//                    List<String> cookies = headers.values("Set-Cookie");
//                    String session1 = cookies.get(0);
//                    session = session1.substring(0,session1.indexOf(";"));

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



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                User re = gson.fromJson(qq, User.class);
                String friendname=re.getUsername();

                //String back = re.getResult();
                //System.out.println(re.getResult());
                if(friendname!=null){
                    Log.i(TAG,"即将跳转");
                    Log.i(TAG,"sessionId"+session);
                    Toast.makeText(AddNewFriendActivity.this,"查找到该好友", Toast.LENGTH_SHORT).show();
                    showfriendname.setText(re.getUsername());
                }else {
                    Toast.makeText(AddNewFriendActivity.this,"该好友不存在", Toast.LENGTH_LONG).show();
                }
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

                //String back = re.getResult();
                //System.out.println(re.getResult());
                if(result.equals("true")){
                    Toast.makeText(AddNewFriendActivity.this,"发送请求成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AddNewFriendActivity.this,"发送失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

}
