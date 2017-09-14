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
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.activity.AddNewFriendActivity;
import com.example.aa.itravel.activity.ShowFriendInfo;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class FriendTopFragment extends Fragment {
	private String name;
	private int fnumber;
	private FriendData1 friendData1=new FriendData1();
	private FriendData2 friendData2=new FriendData2();
	private FriendData3 friendData3=new FriendData3();
	private FriendData4 friendData4=new FriendData4();
	private FriendData5 friendData5=new FriendData5();
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
	private String fname3;
	private String fname4;
	private String fname5;


	TextView friendname1;
	TextView friendname2;
	TextView friendname3;
	TextView friendname4;
	TextView friendname5;


	//查看第一个好友资料
	public class FriendData1 implements OnClickListener{
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID",session);
			intent.putExtra("friendname",fname1);
			startActivity(intent);

		}
	}

	//查看第二个好友资料
	public class FriendData2 implements OnClickListener{
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID",session);
			intent.putExtra("friendname",fname2);
			startActivity(intent);

		}
	}

	//查看第三个好友资料
	public class FriendData3 implements OnClickListener{
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID",session);
			intent.putExtra("friendname",fname3);
			startActivity(intent);

		}
	}

	//查看第四个好友资料
	public class FriendData4 implements OnClickListener{
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID",session);
			intent.putExtra("friendname",fname4);
			startActivity(intent);

		}
	}

	//查看第五个好友资料
	public class FriendData5 implements OnClickListener{
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID",session);
			intent.putExtra("friendname",fname5);
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
			fnumber=bundle.getInt("fnumber");
			if(fnumber==1){
				fname1=bundle.get("fname1").toString();
			}if(fnumber==2){
				fname1=bundle.get("fname1").toString();
				fname2=bundle.get("fname2").toString();
			}if(fnumber==3){
				fname1=bundle.get("fname1").toString();
				fname2=bundle.get("fname2").toString();
				fname3=bundle.get("fname3").toString();
			}if(fnumber==4){
				fname1=bundle.get("fname1").toString();
				fname2=bundle.get("fname2").toString();
				fname3=bundle.get("fname3").toString();
				fname4=bundle.get("fname4").toString();
			}if(fnumber==5){
				fname1=bundle.get("fname1").toString();
				fname2=bundle.get("fname2").toString();
				fname3=bundle.get("fname3").toString();
				fname4=bundle.get("fname4").toString();
				fname5=bundle.get("fname5").toString();
			}
		}

	}




	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.notice_fragment, null);
		switch (name) {
			case "1":
				view = inflater.inflate(R.layout.friendlist_fragment, null);
				view.findViewById(R.id.new_friend).setOnClickListener(newFriend);
				if(fnumber==1){
					view.findViewById(R.id.fr_next_01).setOnClickListener(friendData1);
					friendname1 = (TextView) view.findViewById(R.id.fr_user_01);
					friendname1.setText(fname1);
					view.findViewById(R.id.firend_01).setVisibility(View.VISIBLE);

				}if(fnumber==2){
					view.findViewById(R.id.fr_next_01).setOnClickListener(friendData1);
					friendname1 = (TextView) view.findViewById(R.id.fr_user_01);
					friendname1.setText(fname1);
					view.findViewById(R.id.firend_01).setVisibility(View.VISIBLE);

					view.findViewById(R.id.fr_next_02).setOnClickListener(friendData2);
					friendname2 = (TextView) view.findViewById(R.id.fr_user_02);
					friendname2.setText(fname2);
					view.findViewById(R.id.firend_02).setVisibility(View.VISIBLE);

				}if(fnumber==3){
					view.findViewById(R.id.fr_next_01).setOnClickListener(friendData1);
					friendname1 = (TextView) view.findViewById(R.id.fr_user_01);
					friendname1.setText(fname1);
					view.findViewById(R.id.firend_01).setVisibility(View.VISIBLE);

					view.findViewById(R.id.fr_next_02).setOnClickListener(friendData2);
					friendname2 = (TextView) view.findViewById(R.id.fr_user_02);
					friendname2.setText(fname2);
					view.findViewById(R.id.firend_02).setVisibility(View.VISIBLE);

					view.findViewById(R.id.fr_next_03).setOnClickListener(friendData3);
					friendname3 = (TextView) view.findViewById(R.id.fr_user_03);
					friendname3.setText(fname3);
					view.findViewById(R.id.firend_03).setVisibility(View.VISIBLE);

				}if(fnumber==4){
					view.findViewById(R.id.fr_next_01).setOnClickListener(friendData1);
					friendname1 = (TextView) view.findViewById(R.id.fr_user_01);
					friendname1.setText(fname1);
					view.findViewById(R.id.firend_01).setVisibility(View.VISIBLE);

					view.findViewById(R.id.fr_next_02).setOnClickListener(friendData2);
					friendname2 = (TextView) view.findViewById(R.id.fr_user_02);
					friendname2.setText(fname2);
					view.findViewById(R.id.firend_02).setVisibility(View.VISIBLE);

					view.findViewById(R.id.fr_next_03).setOnClickListener(friendData3);
					friendname3 = (TextView) view.findViewById(R.id.fr_user_03);
					friendname3.setText(fname3);
					view.findViewById(R.id.firend_03).setVisibility(View.VISIBLE);

					view.findViewById(R.id.fr_next_04).setOnClickListener(friendData4);
					friendname4 = (TextView) view.findViewById(R.id.fr_user_04);
					friendname4.setText(fname4);
					view.findViewById(R.id.firend_04).setVisibility(View.VISIBLE);
				}if(fnumber==5){
					view.findViewById(R.id.fr_next_01).setOnClickListener(friendData1);
					friendname1 = (TextView) view.findViewById(R.id.fr_user_01);
					friendname1.setText(fname1);
					view.findViewById(R.id.firend_01).setVisibility(View.VISIBLE);

					view.findViewById(R.id.fr_next_02).setOnClickListener(friendData2);
					friendname2 = (TextView) view.findViewById(R.id.fr_user_02);
					friendname2.setText(fname2);
					view.findViewById(R.id.firend_02).setVisibility(View.VISIBLE);

					view.findViewById(R.id.fr_next_03).setOnClickListener(friendData3);
					friendname3 = (TextView) view.findViewById(R.id.fr_user_03);
					friendname3.setText(fname3);
					view.findViewById(R.id.firend_03).setVisibility(View.VISIBLE);

					view.findViewById(R.id.fr_next_04).setOnClickListener(friendData4);
					friendname4 = (TextView) view.findViewById(R.id.fr_user_04);
					friendname4.setText(fname4);
					view.findViewById(R.id.firend_04).setVisibility(View.VISIBLE);

					view.findViewById(R.id.fr_next_05).setOnClickListener(friendData5);
					friendname5 = (TextView) view.findViewById(R.id.fr_user_05);
					friendname5.setText(fname5);
					view.findViewById(R.id.firend_05).setVisibility(View.VISIBLE);
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

	public static FriendTopFragment newInstance(String name,String session,int fnumber) {
		Bundle args = new Bundle();
		args.putString("name", name);
		args.putString("session",session);
		args.putInt("fnumber",fnumber);
		FriendTopFragment fragment = new FriendTopFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public static FriendTopFragment newInstance(String name,String session,int fnumber,String fname1/*,String fname2*/) {
		Bundle args = new Bundle();
		args.putString("name", name);
		args.putString("session",session);
		args.putString("fname1",fname1);
		args.putInt("fnumber",fnumber);
		FriendTopFragment fragment = new FriendTopFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public static FriendTopFragment newInstance(String name,String session,int fnumber,String fname1,String fname2) {
		Bundle args = new Bundle();
		args.putString("name", name);
		args.putString("session",session);
		args.putInt("fnumber",fnumber);
		args.putString("fname1",fname1);
		args.putString("fname2",fname2);
		FriendTopFragment fragment = new FriendTopFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public static FriendTopFragment newInstance(String name,String session,int fnumber,String fname1,String fname2,String fname3) {
		Bundle args = new Bundle();
		args.putString("name", name);
		args.putString("session",session);
		args.putString("fname1",fname1);
		args.putString("fname2",fname2);
		args.putString("fname3",fname3);
		args.putInt("fnumber",fnumber);
		FriendTopFragment fragment = new FriendTopFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public static FriendTopFragment newInstance(String name,String session,int fnumber,String fname1,String fname2,String fname3,String fname4) {
		Bundle args = new Bundle();
		args.putString("name", name);
		args.putString("session",session);
		args.putInt("fnumber",fnumber);
		args.putString("fname1",fname1);
		args.putString("fname2",fname2);
		args.putString("fname3",fname3);
		args.putString("fname4",fname4);
		FriendTopFragment fragment = new FriendTopFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public static FriendTopFragment newInstance(String name,String session,int fnumber,String fname1,String fname2,String fname3,String fname4,String fname5) {
		Bundle args = new Bundle();
		args.putString("name", name);
		args.putString("session",session);
		args.putInt("fnumber",fnumber);
		args.putString("fname1",fname1);
		args.putString("fname2",fname2);
		args.putString("fname3",fname3);
		args.putString("fname4",fname4);
		args.putString("fname5",fname5);
		FriendTopFragment fragment = new FriendTopFragment();
		fragment.setArguments(args);
		return fragment;
	}
}