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
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.activity.Message_activity;
import com.example.aa.itravel.tools.CommentEntityWithBLOBs;
import com.example.aa.itravel.tools.Topic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private String name;
	String TAG = "TOPIC1_Activity";
	//s用来保存sessionid     发送refresh请求
	String session;
	String path = "http://223.3.74.248:8080/iTravel_Server_SSM/AndroidService/showcollectionoftopic";
	String path1 = "http://223.3.74.248:8080/iTravel_Server_SSM/AndroidService/entertopic";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static List<Topic> topic_list =new ArrayList<Topic>();
	private static String theme1 = "初值";
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                Log.i(TAG,"进入");
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Topic>>(){}.getType();
                topic_list = gson.fromJson(qq,type);
	            theme1 = topic_list.get(0).getTheme();
	            Log.i("HJ",theme1);
              //  theme1.setText(topic_list.get(0).getTheme());
//                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                String d1 = df.format(topic_list.get(0).getDate());
//                date1.setText(d1);
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
        View view= inflater.inflate(R.layout.collection_fragment, null);
        System.out.println(theme1);
	    switch (name){
            case "1":
                view= inflater.inflate(R.layout.topic_collection_fragment, null);
                showCollectionTopic();
                view.findViewById(R.id.cl_top_01).setOnClickListener(this);
	            TextView tv1 = (TextView) view.findViewById(R.id.cl_theme_01);
	            tv1.setText(theme1);
                break;
            default:
                view= inflater.inflate(R.layout.collection_fragment, null);
                view.findViewById(R.id.cl_msg_01).setOnClickListener(this);
                break;
    }
        return view;
    }

    public static CollectionFragment newInstance(String name,String s) {
        Bundle args = new Bundle();
        args.putString("name", name);
	    args.putString("session",s);
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), Message_activity.class));
    }
    public void showCollectionTopic(){
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
}
