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
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.Message;
import com.example.aa.itravel.tools.MessageEntityWithBLOBs;
import com.example.aa.itravel.tools.Network;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.lang.reflect.Type;

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

    Integer message_id;
    String session;
    String path = Network.URL + "showonemessage";
    //private static List<MessageEntityWithBLOBs> mess_list =new ArrayList<MessageEntityWithBLOBs>();
    private static MessageEntityWithBLOBs mess_content =new MessageEntityWithBLOBs();
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
                //time.setText(mess_content.getMessagetime());
                com_num.setText(String.valueOf(mess_content.getCommitnumber()));
                like_num.setText(String.valueOf(mess_content.getLikenumber()));
                user.setText(mess_content.getUsername());
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
                like.setSelected(true);
                try {
                    int a = Integer.parseInt(like_num.toString());
                    a+=1;
                    like_num.setText(String.valueOf(a));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                showOneMessage();
                break;
            case R.id.tr_collection:
                collection.setSelected(true);
                break;
            case R.id.tr_comment_ic:
                intent = new Intent(mContext, PushCommit.class);
                startActivity(intent);
                break;
            case R.id.tr_transfer:
                intent = new Intent(mContext,SendMessageActivity.class);
                startActivity(intent);
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
                    mMessage.setMessageid(3);
                    mMessage.setLikenumber(Integer.parseInt(like_num.toString()));
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(mMessage);
                    Log.i("CONTENT",content.toString());
                    RequestBody body = RequestBody.create(JSON, content);
                    Log.i("BODY",body.toString());
                    Request request = new Request.Builder()
                            .addHeader("cookie",session)
                            .url(path)
                            .post(body)
                            .build();
                    Log.i("REQUEST",request.toString());
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Log.i("CALL",call.toString());
                    response = call.execute();
                    Log.i("RESPONSE",response.toString());
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
}
