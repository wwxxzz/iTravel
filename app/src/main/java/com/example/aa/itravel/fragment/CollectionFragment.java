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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.activity.SingleMessageActivity;
import com.example.aa.itravel.activity.Topic_activity;
import com.example.aa.itravel.activity.Topic_activity2;
import com.example.aa.itravel.activity.Topic_activity3;
import com.example.aa.itravel.activity.Topic_activity4;
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
public class CollectionFragment extends Fragment {
    private String name="null";
	String TAG = "TOPIC1_Activity";
	//s用来保存sessionid     发送refresh请求
	String session;
    String path = Network.URL+"showcollectionoftopic";
    //String path1 = Network.URL+"entertopic";
    String showcollectionofmsgpath=Network.URL+"showcollection";

	TextView theme_1;
    TextView theme_2;
    TextView theme_3;
    TextView theme_4;

    TextView top_content1;
    TextView top_content2;
    TextView top_content3;
    TextView top_content4;

    List<RelativeLayout> cl_msg=new ArrayList<RelativeLayout>();
    List<TextView> cl_msg_user=new ArrayList<TextView>();
    List<TextView> cl_msg_time=new ArrayList<TextView>();
    List<TextView> cl_msg_content=new ArrayList<TextView>();
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
                if(msg_list!=null&&!msg_list.isEmpty()) {
                    for (int i = 1; i <= msg_list.size(); i++) {
                        cl_msg_user.get(i - 1).setText(msg_list.get(i - 1).getUsername());
                        cl_msg_time.get(i - 1).setText(msg_list.get(i - 1).getMessagetime());
                        cl_msg_content.get(i - 1).setText(msg_list.get(i - 1).getMessagecontent());
                        cl_msg.get(i-1).setOnClickListener(new MsgEvent(msg_list.get(i - 1).getMessageid()));
                        cl_msg.get(i - 1).setVisibility(View.VISIBLE);
                    }
                }
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
                if(topic_list!=null&&!topic_list.isEmpty()) {
                    switch (topic_list.size()) {
                        case 1:
                            theme_1.setText(topic_list.get(0).getTheme());
                            top_content1.setText(topic_list.get(0).getTopiccontent());
                            view.findViewById(R.id.cl_top_01).setOnClickListener(new TopicEvent1());
                            view.findViewById(R.id.cl_top_01).setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            theme_1.setText(topic_list.get(0).getTheme());
                            theme_2.setText(topic_list.get(1).getTheme());
                            top_content1.setText(topic_list.get(0).getTopiccontent());
                            top_content2.setText(topic_list.get(1).getTopiccontent());
                            view.findViewById(R.id.cl_top_01).setOnClickListener(new TopicEvent1());
                            view.findViewById(R.id.cl_top_01).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.cl_top_02).setOnClickListener(new TopicEvent2());
                            view.findViewById(R.id.cl_top_02).setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            theme_1.setText(topic_list.get(0).getTheme());
                            theme_2.setText(topic_list.get(1).getTheme());
                            theme_3.setText(topic_list.get(2).getTheme());
                            top_content1.setText(topic_list.get(0).getTopiccontent());
                            top_content2.setText(topic_list.get(1).getTopiccontent());
                            top_content3.setText(topic_list.get(2).getTopiccontent());
                            view.findViewById(R.id.cl_top_01).setOnClickListener(new TopicEvent1());
                            view.findViewById(R.id.cl_top_01).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.cl_top_02).setOnClickListener(new TopicEvent2());
                            view.findViewById(R.id.cl_top_02).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.cl_top_03).setOnClickListener(new TopicEvent3());
                            view.findViewById(R.id.cl_top_03).setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            theme_1.setText(topic_list.get(0).getTheme());
                            theme_2.setText(topic_list.get(1).getTheme());
                            theme_3.setText(topic_list.get(2).getTheme());
                            theme_4.setText(topic_list.get(3).getTheme());
                            top_content1.setText(topic_list.get(0).getTopiccontent());
                            top_content2.setText(topic_list.get(1).getTopiccontent());
                            top_content3.setText(topic_list.get(2).getTopiccontent());
                            top_content4.setText(topic_list.get(3).getTopiccontent());
                            view.findViewById(R.id.cl_top_01).setOnClickListener(new TopicEvent1());
                            view.findViewById(R.id.cl_top_01).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.cl_top_02).setOnClickListener(new TopicEvent2());
                            view.findViewById(R.id.cl_top_02).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.cl_top_03).setOnClickListener(new TopicEvent3());
                            view.findViewById(R.id.cl_top_03).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.cl_top_04).setOnClickListener(new TopicEvent4());
                            view.findViewById(R.id.cl_top_04).setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                }

                //theme_1.setText(topic_list.get(0).getTheme());
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
	            theme_1 = (TextView) view.findViewById(R.id.cl_theme_01);
                theme_2 = (TextView) view.findViewById(R.id.cl_theme_02);
                theme_3 = (TextView) view.findViewById(R.id.cl_theme_03);
                theme_4 = (TextView) view.findViewById(R.id.cl_theme_04);
                top_content1 =(TextView) view.findViewById(R.id.cl_date_01);
                top_content2 =(TextView) view.findViewById(R.id.cl_date_02);
                top_content3 =(TextView) view.findViewById(R.id.cl_date_03);
                top_content4 =(TextView) view.findViewById(R.id.cl_date_04);
                showcollectionoftopic();
                break;
            default:
                view= inflater.inflate(R.layout.collection_fragment, null);

                cl_msg.add((RelativeLayout) view.findViewById(R.id.cl_msg_01));
                cl_msg.add((RelativeLayout) view.findViewById(R.id.cl_msg_02));
                cl_msg.add((RelativeLayout) view.findViewById(R.id.cl_msg_03));
                cl_msg.add((RelativeLayout) view.findViewById(R.id.cl_msg_04));
                cl_msg.add((RelativeLayout) view.findViewById(R.id.cl_msg_05));
                cl_msg.add((RelativeLayout) view.findViewById(R.id.cl_msg_06));
                cl_msg.add((RelativeLayout) view.findViewById(R.id.cl_msg_07));
                cl_msg.add((RelativeLayout) view.findViewById(R.id.cl_msg_08));
                cl_msg.add((RelativeLayout) view.findViewById(R.id.cl_msg_09));
                cl_msg_user.add((TextView) view.findViewById(R.id.cl_user_01));
                cl_msg_user.add((TextView) view.findViewById(R.id.cl_user_02));
                cl_msg_user.add((TextView) view.findViewById(R.id.cl_user_03));
                cl_msg_user.add((TextView) view.findViewById(R.id.cl_user_04));
                cl_msg_user.add((TextView) view.findViewById(R.id.cl_user_05));
                cl_msg_user.add((TextView) view.findViewById(R.id.cl_user_06));
                cl_msg_user.add((TextView) view.findViewById(R.id.cl_user_07));
                cl_msg_user.add((TextView) view.findViewById(R.id.cl_user_08));
                cl_msg_user.add((TextView) view.findViewById(R.id.cl_user_09));
                cl_msg_time.add((TextView) view.findViewById(R.id.cl_time_01));
                cl_msg_time.add((TextView) view.findViewById(R.id.cl_time_02));
                cl_msg_time.add((TextView) view.findViewById(R.id.cl_time_03));
                cl_msg_time.add((TextView) view.findViewById(R.id.cl_time_04));
                cl_msg_time.add((TextView) view.findViewById(R.id.cl_time_05));
                cl_msg_time.add((TextView) view.findViewById(R.id.cl_time_06));
                cl_msg_time.add((TextView) view.findViewById(R.id.cl_time_07));
                cl_msg_time.add((TextView) view.findViewById(R.id.cl_time_08));
                cl_msg_time.add((TextView) view.findViewById(R.id.cl_time_09));
                cl_msg_content.add((TextView) view.findViewById(R.id.cl_data_01));
                cl_msg_content.add((TextView) view.findViewById(R.id.cl_data_02));
                cl_msg_content.add((TextView) view.findViewById(R.id.cl_data_03));
                cl_msg_content.add((TextView) view.findViewById(R.id.cl_data_04));
                cl_msg_content.add((TextView) view.findViewById(R.id.cl_data_05));
                cl_msg_content.add((TextView) view.findViewById(R.id.cl_data_06));
                cl_msg_content.add((TextView) view.findViewById(R.id.cl_data_07));
                cl_msg_content.add((TextView) view.findViewById(R.id.cl_data_08));
                cl_msg_content.add((TextView) view.findViewById(R.id.cl_data_09));
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


    public class MsgEvent implements  View.OnClickListener {
        private Integer msgid;
        MsgEvent(Integer id){
            msgid=id;
        }
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), SingleMessageActivity.class);
            intent.putExtra("sessionID", session);
            intent.putExtra("messageID",msgid);
            startActivity(intent);
        }
    };
    public class TopicEvent1 implements  View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), Topic_activity.class);
            intent.putExtra("sessionID", session);
            startActivity(intent);
        }
    };
    public class TopicEvent2 implements  View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), Topic_activity2.class);
            intent.putExtra("sessionID", session);
            startActivity(intent);
        }
    };
    public class TopicEvent3 implements  View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), Topic_activity3.class);
            intent.putExtra("sessionID", session);
            startActivity(intent);
        }
    };
    public class TopicEvent4 implements  View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), Topic_activity4.class);
            intent.putExtra("sessionID", session);
            startActivity(intent);
        }
    };
}
