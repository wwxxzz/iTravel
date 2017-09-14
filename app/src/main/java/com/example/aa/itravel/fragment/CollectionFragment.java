package com.example.aa.itravel.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.activity.SingleMessageActivity;
import com.example.aa.itravel.tools.MessageEntityWithBLOBs;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.Topic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ynez on 2017/9/8.
 */
@SuppressLint("ValidFragment")
public class CollectionFragment extends Fragment implements View.OnClickListener {
    private String name="null";
	/*private String theme1="nul";
    private String user="null";
    private String time="null";
    private String content="null";
	private String date1;*/
	String TAG = "TOPIC1_Activity";
	//s用来保存sessionid     发送refresh请求
	String session;
    String path = Network.URL+"showcollectionoftopic";
    //String path1 = Network.URL+"entertopic";
    String showcollectionofmsgpath=Network.URL+"showcollection";

	TextView theme_1;
    TextView msg_user;
    TextView msg_time;
    TextView msg_content;

	View view;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static List<Topic> topic_list =new ArrayList<Topic>();
    private static List<MessageEntityWithBLOBs> msg_list =new ArrayList<MessageEntityWithBLOBs>();
    private Handler MessageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                Log.i(TAG,"进入");
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                Type type = new TypeToken<ArrayList<MessageEntityWithBLOBs>>(){}.getType();
                msg_list = gson.fromJson(qq,type);
                msg_user.setText(msg_list.get(0).getUsername());
                msg_time.setText(msg_list.get(0).getMessagetime());
                msg_content.setText(msg_list.get(0).getMessagecontent());
            }
        }
    };
    private Handler TopicHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                Log.i(TAG,"进入");
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Topic>>(){}.getType();
                topic_list = gson.fromJson(qq,type);
                theme_1.setText(topic_list.get(0).getTheme());
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.get("name").toString();
	        session = bundle.get("session").toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.collection_fragment, null);
	    switch (name){
            case "1":
                view= inflater.inflate(R.layout.topic_collection_fragment, null);
                view.findViewById(R.id.cl_top_01).setOnClickListener(this);
	            theme_1 = (TextView) view.findViewById(R.id.cl_theme_01);
                showcollectionoftopic();
                break;
            default:
                view= inflater.inflate(R.layout.collection_fragment, null);
                view.findViewById(R.id.cl_msg_01).setOnClickListener(this);
                msg_user =(TextView) view.findViewById(R.id.cl_user_01);
                msg_time =(TextView) view.findViewById(R.id.cl_time_01);
                msg_content =(TextView) view.findViewById(R.id.cl_data_01);
                showcollectionofmsg();
                break;
    }
        return view;
    }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i (TAG, "==onActivityCreated()执行了");
		super.onActivityCreated(savedInstanceState);
	}
    public static CollectionFragment newInstance(String name,String s) {
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("session",s);
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void showcollectionoftopic(){
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
                        TopicHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void showcollectionofmsg(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().addHeader("cookie",session).url(showcollectionofmsgpath).build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i(TAG,"响应成功");
                    if (response.isSuccessful()) {
                        Log.i(TAG,"响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        MessageHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), SingleMessageActivity.class));
    }
}
