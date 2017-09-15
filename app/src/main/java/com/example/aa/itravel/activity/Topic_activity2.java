package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.Comment;
import com.example.aa.itravel.tools.CommentEntityWithBLOBs;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.PreferredType;
import com.example.aa.itravel.tools.Result;
import com.example.aa.itravel.tools.Topic;
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
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@ContentView(R.layout.activity_topic)
public class Topic_activity2 extends Activity {
    private Context mContext;

    String TAG = "TOPIC2_Activity";
    //s用来保存sessionid     发送refresh请求
    String session;
    String path = Network.URL+"gettopic2";
    String path1 = Network.URL+"entertopic";
    String path2 = Network.URL+"newcollectionfortopic";
    String path3 = Network.URL+"topicifcollected";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @ViewInject(R.id.title_bar_name)
    private TextView textView;
    @ViewInject(R.id.iv_right)
    private ImageView right_icon;
    @ViewInject(R.id.img_comment)
    private ImageView comment_img;
    @ViewInject(R.id.tv_topic)
    private TextView topic_theme;
    @ViewInject(R.id.tv_topic_con)
    private TextView topic_content;
    @ViewInject(R.id.tv_username1)
    private  TextView user1_name;
    @ViewInject(R.id.tv_username2)
    private  TextView user2_name;
    @ViewInject(R.id.tv_username3)
    private  TextView user3_name;
    @ViewInject(R.id.tv_username4)
    private  TextView user4_name;
    @ViewInject(R.id.tv_username5)
    private  TextView user5_name;
    @ViewInject(R.id.tv_username6)
    private  TextView user6_name;
    @ViewInject(R.id.tv_topicComment1)
    private TextView user1_comment;
    @ViewInject(R.id.tv_topicComment2)
    private TextView user2_comment;
    @ViewInject(R.id.tv_topicComment3)
    private TextView user3_comment;
    @ViewInject(R.id.tv_topicComment4)
    private TextView user4_comment;
    @ViewInject(R.id.tv_topicComment5)
    private TextView user5_comment;
    @ViewInject(R.id.tv_topicComment6)
    private TextView user6_comment;
    @ViewInject(R.id.tv_likenumber1)
    private TextView user1_like;
    @ViewInject(R.id.tv_likenumber2)
    private TextView user2_like;
    @ViewInject(R.id.tv_likenumber3)
    private TextView user3_like;
    @ViewInject(R.id.tv_likenumber4)
    private TextView user4_like;
    @ViewInject(R.id.tv_likenumber5)
    private TextView user5_like;
    @ViewInject(R.id.tv_likenumber6)
    private TextView user6_like;
//	@ViewInject(R.id.topic_id)
//	private TextView topicID;

    public Integer topicID;
    private static List<CommentEntityWithBLOBs> com_list =new ArrayList<CommentEntityWithBLOBs>();

    private Handler mmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                //Log.i(TAG,"进入");
                //		Toast.makeText(ShowUserInfo.this,"成功", Toast.LENGTH_SHORT);
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Topic top = gson.fromJson(qq, Topic.class);
                topic_theme.setText(top.getTheme());
                topic_content.setText(top.getTopiccontent());
                topicID = top.getTopicid();
//				topicID.setText(top.getTopicid());
                Log.i(TAG,"进入函数");
                showComment();

            }
        }
    };
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                Log.i(TAG,"进入");
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<CommentEntityWithBLOBs>>(){}.getType();
                com_list = gson.fromJson(qq,type);
                user1_name.setText(com_list.get(0).getCommentatorname());
                user2_name.setText(com_list.get(1).getCommentatorname());
                user3_name.setText(com_list.get(2).getCommentatorname());
                user4_name.setText(com_list.get(3).getCommentatorname());
                user5_name.setText(com_list.get(4).getCommentatorname());
                user6_name.setText(com_list.get(5).getCommentatorname());
                user1_comment.setText(com_list.get(0).getCommentcontent());
                user2_comment.setText(com_list.get(1).getCommentcontent());
                user3_comment.setText(com_list.get(2).getCommentcontent());
                user4_comment.setText(com_list.get(3).getCommentcontent());
                user5_comment.setText(com_list.get(4).getCommentcontent());
                user6_comment.setText(com_list.get(5).getCommentcontent());
                System.out.println(com_list.get(0).getLikenumber());
                user1_like.setText(String.valueOf(com_list.get(0).getLikenumber()));
                user2_like.setText(String.valueOf(com_list.get(1).getLikenumber()));
                user3_like.setText(String.valueOf(com_list.get(2).getLikenumber()));
                user4_like.setText(String.valueOf(com_list.get(3).getLikenumber()));
                user5_like.setText(String.valueOf(com_list.get(4).getLikenumber()));
                user6_like.setText(String.valueOf(com_list.get(5).getLikenumber()));
            }

        }
    };
    private Handler cHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i("COMMENT", qq);
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String back = re.getResult();
                System.out.println(re.getResult());
                if(back.equals("true") ){
                    right_icon.setImageResource(R.drawable.star_c);
                    Toast.makeText(Topic_activity2.this,"收藏成功", Toast.LENGTH_SHORT).show();
                }else{
                    right_icon.setImageResource(R.drawable.star_c);
                    Toast.makeText(Topic_activity2.this,"已收藏", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    private Handler dHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String back = re.getResult();
                System.out.println(re.getResult());
                if(back.equals("true") ){
                    right_icon.setImageResource(R.drawable.star_c);
                }else{
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        mContext = this;
        textView.setText("话题2");
        Log.i(TAG,"这是话题2");
        right_icon.setImageResource(R.drawable.star_co);
        //	comment_img.setImageResource(R.drawable.topic_comment);
         /*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
            /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionID");
        Log.i(TAG,session);
        showTopic();

    }
    public void showTopic(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Request request = new Request.Builder().addHeader("cookie",session).url(path).build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i(TAG,"响应成功");
                    if (response.isSuccessful()) {
                        Log.i(TAG,"响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        mmHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void showComment(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //回调
                    Topic topi = new Topic();
                    System.out.println(topicID+"sdjkhsajk大家啊都卡啊拉萨大家了");
                    topi.setTopicid(topicID);

                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(topi);

                    RequestBody body = RequestBody.create(JSON, content);
                    Request request = new Request.Builder().addHeader("cookie",session).post(body).url(path1).build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i(TAG,"响应成功");
                    if (response.isSuccessful()) {
                        Log.i(TAG,"响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        mHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Event(value={R.id.img_comment})
    private void event(View v){
        Log.i(TAG,"点击成功");
        Intent intent = new Intent(mContext,PushCommit.class);
        intent.putExtra("sessionID", session);
        intent.putExtra("topicId",topicID);
        intent.putExtra("Index",2);
        startActivity(intent);
        finish();
    }
    @Event(value={R.id.iv_right})
    private void event1(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //回调
                    Topic top = new Topic();
                    top.setTopicid(topicID);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(top);

                    RequestBody body = RequestBody.create(JSON, content);

                    Request request = new Request.Builder()
                            .addHeader("cookie", session)
                            .url(path2)
                            .post(body)
                            .build();

                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i(TAG, "响应成功");
                    if (response.isSuccessful()) {
                        Log.i(TAG, "响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        cHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void showCollection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //回调
                    Topic top = new Topic();
                    top.setTopicid(topicID);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(top);
                    RequestBody body = RequestBody.create(JSON, content);
                    Log.i("TOPIC","显示收藏");
                    Request request = new Request.Builder()
                            .addHeader("cookie", session)
                            .post(body)
                            .url(path3)
                            .build();

                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i(TAG, "响应成功");
                    if (response.isSuccessful()) {
                        Log.i(TAG, "响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        //System.out.println(response.body().string());
                        dHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
