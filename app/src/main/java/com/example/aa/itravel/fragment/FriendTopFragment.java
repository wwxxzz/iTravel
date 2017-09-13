package com.example.aa.itravel.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.activity.AddNewFriendActivity;
import com.example.aa.itravel.activity.ShowFriendInfo;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class FriendTopFragment extends Fragment {
	private String name;
	private FriendData friendData=new FriendData();
	private NewFriend newFriend=new NewFriend();
	private Context mContext;
	String TAG = "SHOW_FRIEND_LIST_Activity";
	//s用来保存sessionid     发送refresh请求
	String session;
	Response response;
	OkHttpClient client = new OkHttpClient();

	String path = Network.URL+ "showfriends";

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public Integer friendID;
	private static List<User> friend_list =new ArrayList<User>();

	@ViewInject(R.id.firend_01)
	private RelativeLayout friend1;
	@ViewInject(R.id.fr_user_01)
	private TextView friendname1;//请求添加的好友的名字

	@ViewInject(R.id.firend_02)
	private RelativeLayout friend2;
	@ViewInject(R.id.fr_user_02)
	private TextView friendname2;//请求添加的好友的名字





	public class FriendData implements OnClickListener{

		@Override
		public void onClick(View view) {
			startActivity(new Intent(getActivity(), ShowFriendInfo.class));
		}
	}
	public class NewFriend implements OnClickListener{

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(),AddNewFriendActivity.class);
			intent.putExtra("sessionID",session);

			startActivity(intent);
		}
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			name = bundle.get("name").toString();
			session = bundle.get("session").toString();
		}

		showFriendRequest();
	}

	private void showFriendRequest(){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {
					Request request = new Request.Builder().addHeader("cookie",session)
							.url(path)//.post(body)
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
				String qq = (String) msg.obj;
				//Log.i(TAG, qq);
				Gson gson = new Gson();
				Type type = new TypeToken<ArrayList<User>>(){}.getType();
				friend_list = gson.fromJson(qq,type);
				if(friend_list!=null){
					friendname1.setText(friend_list.get(0).getUsername());
					friend1.setVisibility(View.VISIBLE);
					if(friend_list.size()==2){
						friendname2.setText(friend_list.get(1).getUsername());
						friend2.setVisibility(View.VISIBLE);
					}
				}
				//Log.i("111",friendinfo_list.get(0).getFromusername());
			}
		}
	};



	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.notice_fragment, null);
		switch (name) {
			case "1":
				view = inflater.inflate(R.layout.friendlist_fragment, null);
				view.findViewById(R.id.fr_next_01).setOnClickListener(friendData);
				view.findViewById(R.id.new_friend).setOnClickListener(newFriend);
				break;
			case "2":
				view = inflater.inflate(R.layout.chat_fragment, null);
				break;
			default:
				view = inflater.inflate(R.layout.notice_fragment, null);
				break;
		}
		return view;
	}

	public static com.example.aa.itravel.fragment.FriendTopFragment newInstance(String name,String session) {
		Bundle args = new Bundle();
		args.putString("name", name);
		args.putString("session",session);
		com.example.aa.itravel.fragment.FriendTopFragment fragment = new com.example.aa.itravel.fragment.FriendTopFragment();
		fragment.setArguments(args);
		return fragment;
	}

}