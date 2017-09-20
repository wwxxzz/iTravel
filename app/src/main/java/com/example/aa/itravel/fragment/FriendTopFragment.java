package com.example.aa.itravel.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.activity.AddNewFriendActivity;
import com.example.aa.itravel.activity.ShowFriendInfo;
import com.example.aa.itravel.tools.MessageBuffer;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@SuppressLint("ValidFragment")
public class FriendTopFragment extends Fragment {
	private String name;
	private int fnumber;
	LayoutInflater inf;
	private FriendData1 friendData1 = new FriendData1();
	private FriendData2 friendData2 = new FriendData2();
	private FriendData3 friendData3 = new FriendData3();
	private FriendData4 friendData4 = new FriendData4();
	private FriendData5 friendData5 = new FriendData5();
	private NewFriend newFriend = new NewFriend();


	private Context mContext;
	String TAG = "SHOW_FRIEND_LIST_Activity";
	//s用来保存sessionid     发送refresh请求
	String session;
	Response response;
	OkHttpClient client = new OkHttpClient();

	String path = Network.URL + "showfriends";
	String shownoticepath = Network.URL + "refreshinform";

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public final static int TYPE_LIKE = 0;
	public final static int TYPE_COMMENT = 1;
	public final static int TYPE_TRANSFER = 2;
	public final static int TYPE_COLLECT = 3;

	public Integer friendID;

	private static List<TextView> no_content = new ArrayList<TextView>();
	private static List<TextView> no_time = new ArrayList<TextView>();
	private static List<RelativeLayout> notice = new ArrayList<RelativeLayout>();

	private static List<TextView> friend_name =new ArrayList<TextView>();
	private static List<ImageView> friend_photo=new ArrayList<ImageView>();
	private static List<User> friend_list = new ArrayList<User>();
	private static List<RelativeLayout> friend = new ArrayList<RelativeLayout>();



	private static List<MessageBuffer> notice_list = new ArrayList<MessageBuffer>();
	private Handler shownoticeHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			notice_list.clear();
			if (msg.what == 1) {
				String qq = (String) msg.obj;
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
				Type type = new TypeToken<List<MessageBuffer>>() {}.getType();
				notice_list = gson.fromJson(qq, type);
				Log.i("NOTICE",notice_list.toString());
				if (notice_list!= null&&!notice_list.isEmpty())
					for (int i = 0; i < notice_list.size(); i++) {
						Log.i("UI", "更新成功");
						Log.i("NOTICE",notice.toString());
						notice.get(i).setVisibility(View.VISIBLE);
						Log.i("NOTICE",no_content.toString());
						no_content.get(i).setText(notice_list.get(i).getMessagebcontent());
						Log.i("NOTICE",no_time.toString());
						no_time.get(i).setText(notice_list.get(i).getSendtime());
					}
			}
		}
	};

	//查看第一个好友资料
	public class FriendData1 implements OnClickListener {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID", session);
			intent.putExtra("friendname",friend_name.get(0).getText().toString());
			startActivity(intent);

		}
	}

	//查看第二个好友资料
	public class FriendData2 implements OnClickListener {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID", session);
			intent.putExtra("friendname", friend_name.get(1).getText().toString());
			startActivity(intent);

		}
	}

	//查看第三个好友资料
	public class FriendData3 implements OnClickListener {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID", session);
			intent.putExtra("friendname", friend_name.get(2).getText().toString());
			startActivity(intent);

		}
	}

	//查看第四个好友资料
	public class FriendData4 implements OnClickListener {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID", session);
			intent.putExtra("friendname", friend_name.get(3).getText().toString());
			startActivity(intent);

		}
	}

	//查看第五个好友资料
	public class FriendData5 implements OnClickListener {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), ShowFriendInfo.class);
			intent.putExtra("sessionID", session);
			intent.putExtra("friendname", friend_name.get(4).getText().toString());
			startActivity(intent);

		}
	}


	public class NewFriend implements OnClickListener {

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), AddNewFriendActivity.class);
			intent.putExtra("sessionID", session);

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

	}
    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            shownotice();
            showFriendRequest();
			Log.i("Hidden","success");
        }
    }

	@Override
	public void onStart() {
		super.onStart();
		shownotice();
		showFriendRequest();
		Log.i("START","success");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		notice.clear();
		no_content.clear();
		no_time.clear();
		notice_list.clear();
	}*/

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
		View view;
		switch (name) {
			case "1":
				view = inflater.inflate(R.layout.friendlist_fragment, null);
				view.findViewById(R.id.new_friend).setOnClickListener(newFriend);
				//查看好友资料
				view.findViewById(R.id.fr_next_01).setOnClickListener(friendData1);
				view.findViewById(R.id.fr_next_02).setOnClickListener(friendData2);
				view.findViewById(R.id.fr_next_03).setOnClickListener(friendData3);
				view.findViewById(R.id.fr_next_04).setOnClickListener(friendData4);
				view.findViewById(R.id.fr_next_05).setOnClickListener(friendData5);
				//显示好友
				friend.add((RelativeLayout) view.findViewById(R.id.firend_01));
				friend.add((RelativeLayout) view.findViewById(R.id.firend_02));
				friend.add((RelativeLayout) view.findViewById(R.id.firend_03));
				friend.add((RelativeLayout) view.findViewById(R.id.firend_04));
				friend.add((RelativeLayout) view.findViewById(R.id.firend_05));
				friend_name.add((TextView) view.findViewById(R.id.fr_user_01));
				friend_name.add((TextView) view.findViewById(R.id.fr_user_02));
				friend_name.add((TextView) view.findViewById(R.id.fr_user_03));
				friend_name.add((TextView) view.findViewById(R.id.fr_user_04));
				friend_name.add((TextView) view.findViewById(R.id.fr_user_05));
				friend_photo.add((ImageView) view.findViewById(R.id.fr_head_01));
				friend_photo.add((ImageView) view.findViewById(R.id.fr_head_02));
				friend_photo.add((ImageView) view.findViewById(R.id.fr_head_03));
				friend_photo.add((ImageView) view.findViewById(R.id.fr_head_04));
				friend_photo.add((ImageView) view.findViewById(R.id.fr_head_05));
				showFriendRequest();
				break;

			default:
				view = inflater.inflate(R.layout.notice_fragment, null);
				if(notice.isEmpty()){
					notice.add((RelativeLayout) view.findViewById(R.id.notice1));
					notice.add((RelativeLayout) view.findViewById(R.id.notice2));
					notice.add((RelativeLayout) view.findViewById(R.id.notice3));
					notice.add((RelativeLayout) view.findViewById(R.id.notice4));
					notice.add((RelativeLayout) view.findViewById(R.id.notice5));
					notice.add((RelativeLayout) view.findViewById(R.id.notice6));
					notice.add((RelativeLayout) view.findViewById(R.id.notice7));
				}
				if(no_content.isEmpty()){
					no_content.add((TextView) view.findViewById(R.id.no_content1));
					no_content.add((TextView) view.findViewById(R.id.no_content2));
					no_content.add((TextView) view.findViewById(R.id.no_content3));
					no_content.add((TextView) view.findViewById(R.id.no_content4));
					no_content.add((TextView) view.findViewById(R.id.no_content5));
					no_content.add((TextView) view.findViewById(R.id.no_content6));
					no_content.add((TextView) view.findViewById(R.id.no_content7));
				}
				if(no_time.isEmpty()) {
					no_time.add((TextView) view.findViewById(R.id.no_time1));
					no_time.add((TextView) view.findViewById(R.id.no_time2));
					no_time.add((TextView) view.findViewById(R.id.no_time3));
					no_time.add((TextView) view.findViewById(R.id.no_time4));
					no_time.add((TextView) view.findViewById(R.id.no_time5));
					no_time.add((TextView) view.findViewById(R.id.no_time6));
					no_time.add((TextView) view.findViewById(R.id.no_time7));
				}
				Log.i("CV","success");
				Log.i("NOTICE",notice.toString());
				shownotice();
				break;
		}
		return view;
	}

	public static FriendTopFragment newInstance(String name,String s) {
		Bundle args = new Bundle();
		args.putString("name", name);
		args.putString("session",s);
		FriendTopFragment fragment = new FriendTopFragment();
		fragment.setArguments(args);
		return fragment;
	}



	public void shownotice() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//回调
					MessageBuffer mNotice = new MessageBuffer();
					mNotice.setMessagebtype(3);
					Gson gson = new GsonBuilder().create();
					String content = gson.toJson(mNotice);

					RequestBody body = RequestBody.create(JSON, content);

					Request request = new Request.Builder()
							.addHeader("cookie", session)
							.url(shownoticepath)
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
						shownoticeHandler.obtainMessage(1, response.body().string()).sendToTarget();
					} else {
						throw new IOException("Unexpected code:" + response);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void showFriendRequest(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

					//RequestBody body = RequestBody.create(JSON, content);
                    Request request = new Request.Builder().addHeader("cookie",session).url(path)./*post(body).*/build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    //Log.i(TAG,"响应成功");
                    if (response.isSuccessful()) {
                        //Log.i(TAG,"响应成功");
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

	private Handler mHandler = new Handler(){

		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Log.i("ONEMESSAGE", "进入");
				String qq = (String) msg.obj;
				Log.i("ONEMESSAGE", qq);
				Gson gson = new Gson();
				Type type = new TypeToken<List<User>>() {}.getType();
				friend_list = gson.fromJson(qq, type);
				if (friend_list!= null&&!friend_list.isEmpty()&&friend_list.size()<6)
					for (int i = 1; i <= friend_list.size(); i++) {
						friend_name.get(i-1).setText(friend_list.get(i-1).getUsername());

						String fre_photo=friend_list.get(i-1).getUserphoto();
						System.out.println("wenjianming  "+fre_photo);
						switch (i){
							case 1:getUserImage1(fre_photo);break;
							case 2:getUserImage2(fre_photo);break;
							case 3:getUserImage3(fre_photo);break;
							case 4:getUserImage4(fre_photo);break;
							case 5:getUserImage5(fre_photo);break;
							default:break;
						}
						friend.get(i-1).setVisibility(View.VISIBLE);
					}
			}
		}
	};

	//显示头像
	public void getUserImage1(final String userphoto1){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {
					URL url = new URL(Network.IMGURL + userphoto1);
					Bitmap pp = BitmapFactory.decodeStream(url.openStream());
					android.os.Message msg = new android.os.Message();
					//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
					photoHandler.obtainMessage(1, pp).sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	public void getUserImage2(final String userphoto2){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {
					URL url = new URL(Network.IMGURL + userphoto2);
					Bitmap pp = BitmapFactory.decodeStream(url.openStream());
					android.os.Message msg = new android.os.Message();
					//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
					photoHandler.obtainMessage(2, pp).sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	public void getUserImage3(final String userphoto3){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {
					URL url = new URL(Network.IMGURL + userphoto3);
					Bitmap pp = BitmapFactory.decodeStream(url.openStream());
					android.os.Message msg = new android.os.Message();
					//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
					photoHandler.obtainMessage(3, pp).sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	public void getUserImage4(final String userphoto4){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {
					URL url = new URL(Network.IMGURL + userphoto4);
					Bitmap pp = BitmapFactory.decodeStream(url.openStream());
					android.os.Message msg = new android.os.Message();
					//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
					photoHandler.obtainMessage(4, pp).sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	public void getUserImage5(final String userphoto5){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {
					URL url = new URL(Network.IMGURL + userphoto5);
					Bitmap pp = BitmapFactory.decodeStream(url.openStream());
					android.os.Message msg = new android.os.Message();
					//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
					photoHandler.obtainMessage(5, pp).sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private Handler photoHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Bitmap bmp = (Bitmap) msg.obj;
				friend_photo.get(0).setImageBitmap(bmp);
			}
			if (msg.what == 2) {
				Bitmap bmp = (Bitmap) msg.obj;
				friend_photo.get(1).setImageBitmap(bmp);
			}
			if (msg.what == 3) {
				Bitmap bmp = (Bitmap) msg.obj;
				friend_photo.get(2).setImageBitmap(bmp);
			}
			if (msg.what == 4) {
				Bitmap bmp = (Bitmap) msg.obj;
				friend_photo.get(3).setImageBitmap(bmp);
			}
			if (msg.what == 5) {
				Bitmap bmp = (Bitmap) msg.obj;
				friend_photo.get(4).setImageBitmap(bmp);
			}
		}
	};

}