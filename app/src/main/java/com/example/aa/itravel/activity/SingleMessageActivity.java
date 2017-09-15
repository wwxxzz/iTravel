package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.CommentEntityWithBLOBs;
import com.example.aa.itravel.tools.Message;
import com.example.aa.itravel.tools.MessageEntityWithBLOBs;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.Result;
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

@ContentView(R.layout.activity_single_message)
public class SingleMessageActivity extends AppCompatActivity {
    private Context mContext;
    @ViewInject(R.id.title_bar_name)
    private TextView textView;

    @ViewInject(R.id.tr_user)
    private TextView user;
    @ViewInject(R.id.tr_textdata)
    private TextView content;
    @ViewInject(R.id.tr_time)
    private TextView time;
    @ViewInject(R.id.tr_commentnum)
    private TextView com_num;
    @ViewInject(R.id.tr_likenum)
    private TextView like_num;

    @ViewInject(R.id.tr_like)
    private Button like;
    @ViewInject(R.id.tr_collection)
    private Button collection;

    @ViewInject(R.id.comment_user_01)
    private TextView c_user_01;
    @ViewInject(R.id.comment_user_02)
    private TextView c_user_02;
    @ViewInject(R.id.comment_user_03)
    private TextView c_user_03;
    @ViewInject(R.id.comment_user_04)
    private TextView c_user_04;

    @ViewInject(R.id.comment_data_01)
    private TextView c_content_01;
    @ViewInject(R.id.comment_data_02)
    private TextView c_content_02;
    @ViewInject(R.id.comment_data_03)
    private TextView c_content_03;
    @ViewInject(R.id.comment_data_04)
    private TextView c_content_04;

    @ViewInject(R.id.comment_01)
    private RelativeLayout comment_01;
    @ViewInject(R.id.comment_02)
    private RelativeLayout comment_02;
    @ViewInject(R.id.comment_03)
    private RelativeLayout comment_03;
    @ViewInject(R.id.comment_04)
    private RelativeLayout comment_04;

    @ViewInject(R.id.nocomment)
    private TextView nocomment;

    Integer message_id;
    String session;

    String showpath = Network.URL + "showonemessage";
    String showcommentpath = Network.URL+"entermessage";
    String showcollectioninfopath = Network.URL+"msgifcollected";
    String showlikeinfopath = Network.URL+"msgifliked";

    String likepath = Network.URL+"likemessage";
    String collectmessagepath = Network.URL+"newcollectionformsg";
    String transferpath=Network.URL+"forwardmessage";

    private static MessageEntityWithBLOBs mess_content =new MessageEntityWithBLOBs();
    private static List<CommentEntityWithBLOBs> com_list =new ArrayList<CommentEntityWithBLOBs>();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg){
            if(msg.what==1){
                Log.i("ONEMESSAGE","进入");
                String qq = (String) msg.obj;
                Log.i("ONEMESSAGE", qq);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                Type type = new TypeToken<MessageEntityWithBLOBs>(){}.getType();
                mess_content = gson.fromJson(qq,type);
                //System.out.println(mess_content.size());
                content.setText(mess_content.getMessagecontent());
                time.setText(mess_content.getMessagetime());
                com_num.setText(String.valueOf(mess_content.getCommitnumber()));
                like_num.setText(String.valueOf(mess_content.getLikenumber()));
                user.setText(mess_content.getUsername());
                showComment();
                showCollection();
                showLike();
            }
        }
    };

    private Handler showCollectionHandler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String back = re.getResult();
                System.out.println(re.getResult());
                if(back.equals("true") ){
                    collection.setSelected(true);
                }else{
                    collection.setSelected(false);
                }
            }
        }
    };
    private Handler LikeHandler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String back = re.getResult();
                System.out.println(re.getResult());
                if(back.equals("true") ){
                    like.setSelected(true);
                    like_num.setText(Integer.valueOf(like_num.getText().toString()) + 1 + "");
                    Toast.makeText(SingleMessageActivity.this,"点赞成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SingleMessageActivity.this,"点赞失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private Handler showLikeHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                String qq = (String) msg.obj;
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String back = re.getResult();
                System.out.println(re.getResult());
                if (back.equals("true")) {
                    like.setSelected(true);
                } /*else {
                    like.setSelected(false);
                }*/
            }
        }
    };
    private Handler transferHandler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String back = re.getResult();
                System.out.println(re.getResult());
                if(back.equals("true") )
                    Toast.makeText(SingleMessageActivity.this,"转发成功", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Handler commentHandler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;

                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<CommentEntityWithBLOBs>>(){}.getType();
                Log.i("QQ", qq);
                com_list = gson.fromJson(qq,type);
                List<TextView> c_user=new ArrayList<TextView>();
                c_user.add(c_user_01);
                c_user.add(c_user_02);
                c_user.add(c_user_03);
                c_user.add(c_user_04);
                List<TextView> c_content=new ArrayList<TextView>();
                c_content.add(c_content_01);
                c_content.add(c_content_02);
                c_content.add(c_content_03);
                c_content.add(c_content_04);
                List<RelativeLayout> comment=new ArrayList<RelativeLayout>();
                comment.add(comment_01);
                comment.add(comment_02);
                comment.add(comment_03);
                comment.add(comment_04);
                //Log.i("Comment",com_list.toString());
                //Log.i("Comment",com_list.get(0).getCommentatorname());
                //Log.i("Comment",com_list.get(0).getCommentcontent().toString());
                if (com_list!=null&&!com_list.isEmpty()) {
                    for (int i = 1; i <= com_list.size(); i++) {
                        comment.get(i - 1).setVisibility(View.VISIBLE);
                        c_user.get(i - 1).setText(com_list.get(i-1).getCommentatorname());
                        c_content.get(i - 1).setText(com_list.get(i-1).getCommentcontent());
                    }
                }else {
                    nocomment.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        x.view().inject(this);
        textView.setText("详情");
        Bundle bundle = this.getIntent().getExtras();
            /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionID");
        message_id=bundle.getInt("messageID");
        showOneMessage();
    }
    @Event(value = {R.id.tr_like,R.id.tr_collection,R.id.tr_comment_ic,R.id.tr_transfer})
    private void event(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.tr_like:
                if (like.isSelected()){
                    Toast.makeText(SingleMessageActivity.this,"已点赞",Toast.LENGTH_SHORT).show();
                }else{
                    clicklike();
                }
                break;
            case R.id.tr_collection:
                if (collection.isSelected()){
                    Toast.makeText(SingleMessageActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                }else {
                    clickcollection();
                    collection.setSelected(true);
                    Toast.makeText(SingleMessageActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tr_comment_ic:
                intent = new Intent(mContext, PushCommit.class);
                intent.putExtra("sessionID", session);
                intent.putExtra("topicId",0);
                intent.putExtra("messageID",message_id);
                intent.putExtra("Index",0);
                startActivity(intent);
                break;
            case R.id.tr_transfer:
                clicktransfer();
                break;
        }
    }

    public void showOneMessage(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    Message mMessage=new Message();
                    mMessage.setMessageid(message_id);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(mMessage);
                    RequestBody body = RequestBody.create(JSON, content);
                    Request request = new Request.Builder()
                            .addHeader("cookie",session)
                            .url(showpath)
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
    public void showComment(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //回调
                    MessageEntityWithBLOBs mMessage = new  MessageEntityWithBLOBs();
                    mMessage.setMessageid(message_id);

                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(mMessage);

                    RequestBody body = RequestBody.create(JSON, content);
                    Request request = new Request.Builder().addHeader("cookie",session).url(showcommentpath).post(body).build();
                    Log.i("TAG", request.toString());
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    //Log.i(TAG,"响应成功");
                    if (response.isSuccessful()) {
                        Log.i("RESPONSE","响应成功");
                        Log.i("TAG", response.toString());
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        commentHandler.obtainMessage(1, response.body().string()).sendToTarget();
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
                    MessageEntityWithBLOBs mMessage = new  MessageEntityWithBLOBs();
                    mMessage.setMessageid(message_id);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(mMessage);
                    RequestBody body = RequestBody.create(JSON, content);
                    Log.i("MESSAGE","显示收藏");
                    Request request = new Request.Builder()
                            .addHeader("cookie", session)
                            .post(body)
                            .url(showcollectioninfopath)
                            .build();

                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i("TAG", "响应成功");
                    if (response.isSuccessful()) {
                        Log.i("TAG", "响应成");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        //System.out.println(response.body().string());
                        showCollectionHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void showLike() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //回调
                    MessageEntityWithBLOBs mMessage = new  MessageEntityWithBLOBs();
                    mMessage.setMessageid(message_id);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(mMessage);
                    RequestBody body = RequestBody.create(JSON, content);
                    Log.i("MESSAGE","显示点赞");
                    Request request = new Request.Builder()
                            .addHeader("cookie", session)
                            .post(body)
                            .url(showlikeinfopath)
                            .build();

                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i("TAG", "响应成功");
                    if (response.isSuccessful()) {
                        Log.i("TAG", "响应");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        //System.out.println(response.body().string());
                        showLikeHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void clicklike() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                MessageEntityWithBLOBs mMessage=new MessageEntityWithBLOBs();
                mMessage.setMessageid(message_id);
                Gson gson = new GsonBuilder().create();
                String content = gson.toJson(mMessage);
                RequestBody body = RequestBody.create(JSON, content);
                Request request = new Request.Builder()
                        .addHeader("cookie",session)
                        .url(likepath)
                        .post(body)
                        .build();
                OkHttpClient okhttpc = new OkHttpClient();
                Call call = okhttpc.newCall(request);
                Response response = call.execute();
                Log.i("TAG", "响应成功");
                if (response.isSuccessful()) {
                    Log.i("TAG", "响应成功");
                    //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                    LikeHandler.obtainMessage(1, response.body().string()).sendToTarget();
                } else {
                    throw new IOException("Unexpected code:" + response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }).start();
    }
    private void clickcollection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //回调
                    MessageEntityWithBLOBs mMessage=new MessageEntityWithBLOBs();
                    mMessage.setMessageid(message_id);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(mMessage);

                    RequestBody body = RequestBody.create(JSON, content);

                    Request request = new Request.Builder()
                            .addHeader("cookie", session)
                            .url(collectmessagepath)
                            .post(body)
                            .build();

                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i("TAG", "响应成功");
                    if (response.isSuccessful()) {
                        Log.i("TAG", "响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        //collectHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void clicktransfer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //回调
                    MessageEntityWithBLOBs mMessage=new MessageEntityWithBLOBs();
                    mMessage.setMessageid(message_id);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(mMessage);

                    RequestBody body = RequestBody.create(JSON, content);

                    Request request = new Request.Builder()
                            .addHeader("cookie", session)
                            .url(transferpath)
                            .post(body)
                            .build();
                    Log.i("REQUEST", request.toString());
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i("RESPONSE", response.toString());
                    if (response.isSuccessful()) {
                        Log.i("TAG", "响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        transferHandler.obtainMessage(1, response.body().string()).sendToTarget();
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
