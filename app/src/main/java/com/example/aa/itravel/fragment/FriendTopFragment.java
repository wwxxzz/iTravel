package com.example.aa.itravel.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
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


	private String fname1;
	private String fname2;

	TextView friendname1;//请求添加的好友的名字
	TextView friendname2;//请求添加的好友的名字

	@ViewInject(R.id.firend_01)
	private RelativeLayout friend01;
	@ViewInject(R.id.firend_02)
	private RelativeLayout friend02;


	public class FriendData implements OnClickListener{

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID",session);
			intent.putExtra("friendname",fname1);
			startActivity(intent);

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
			fname1=bundle.get("fname1").toString();
			//fname2=bundle.get("fname2").toString();
		}

	}




	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.notice_fragment, null);
		switch (name) {
			case "1":
				view = inflater.inflate(R.layout.friendlist_fragment, null);
				view.findViewById(R.id.fr_next_01).setOnClickListener(friendData);
				view.findViewById(R.id.new_friend).setOnClickListener(newFriend);
				friendname1 = (TextView) view.findViewById(R.id.fr_user_01);
				friendname1.setText(fname1);
				if(friendname1.toString()!=null){
					//friend01.setVisibility(View.VISIBLE);
				}
				friendname2 = (TextView) view.findViewById(R.id.fr_user_02);
				friendname2.setText(fname2);
				if(friendname2.toString()!=null){
					//friend02.setVisibility(View.VISIBLE);
				}
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

	public static FriendTopFragment newInstance(String name,String session,String fname1/*,String fname2*/) {
		Bundle args = new Bundle();
		args.putString("name", name);
		args.putString("session",session);
		args.putString("fname1",fname1);
		//args.putString("fname2",fname2);


		FriendTopFragment fragment = new FriendTopFragment();
		fragment.setArguments(args);
		return fragment;
	}

}