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

import org.w3c.dom.Text;
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
	private String theme1;
	private String date1;
	String TAG = "TOPIC1_Activity";
	//s用来保存sessionid     发送refresh请求
	String session;
	TextView theme_1;

	View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.get("name").toString();
	        session = bundle.get("session").toString();
	        theme1 = bundle.get("theme1").toString();
//	        date1 = bundle.get("date1").toString();
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
	            theme_1.setText(theme1);
                break;
            default:
                view= inflater.inflate(R.layout.collection_fragment, null);
                view.findViewById(R.id.cl_msg_01).setOnClickListener(this);
                break;
    }
        return view;
    }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i (TAG, "==onActivityCreated()执行了");
		super.onActivityCreated(savedInstanceState);
	}
    public static CollectionFragment newInstance(String name,String s,Topic th1) {
        Bundle args = new Bundle();
        args.putString("name", name);
	    args.putString("session",s);
	    args.putString("theme1",th1.getTheme());
//	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//	    String d1 = df.format(th1.getDate());
//	    args.putString("date1",d1);
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), Message_activity.class));
    }

}
