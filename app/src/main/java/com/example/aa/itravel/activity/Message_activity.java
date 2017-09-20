package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.aa.itravel.R.id.bottombar;
import static com.example.aa.itravel.R.id.button_message;

/**
 * Created by aa on 2017/9/5.
 */
@ContentView(R.layout.message)
public class Message_activity extends FragmentActivity {
    private Context mContext;
    @ViewInject(R.id.title_bar_name)
    private TextView textView;
    @ViewInject(R.id.iv_right)
    private ImageView right_icon;
	@ViewInject(R.id.tr_user01)
	private TextView user1;
	@ViewInject(R.id.tr_textdata01)
	private TextView content1;
	@ViewInject(R.id.tr_time01)
	private TextView time1;
	@ViewInject(R.id.tr_commentnum01)
	private TextView com_num1;
	@ViewInject(R.id.tr_likenum01)
	private TextView like_num1;
	@ViewInject(R.id.tr_user02)
	private TextView user2;
	@ViewInject(R.id.tr_textdata02)
	private TextView content2;
	@ViewInject(R.id.tr_time02)
	private TextView time2;
	@ViewInject(R.id.tr_commentnum02)
	private TextView com_num2;
	@ViewInject(R.id.tr_likenum02)
	private TextView like_num2;
	@ViewInject(R.id.tr_user03)
	private TextView user3;
	@ViewInject(R.id.tr_textdata03)
	private TextView content3;
	@ViewInject(R.id.tr_time03)
	private TextView time3;
	@ViewInject(R.id.tr_commentnum03)
	private TextView com_num3;
	@ViewInject(R.id.tr_likenum03)
	private TextView like_num3;
	@ViewInject(R.id.tr_user04)
	private TextView user4;
	@ViewInject(R.id.tr_textdata04)
	private TextView content4;
	@ViewInject(R.id.tr_time04)
	private TextView time4;
	@ViewInject(R.id.tr_commentnum04)
	private TextView com_num4;
	@ViewInject(R.id.tr_likenum04)
	private TextView like_num4;
	@ViewInject(R.id.tr_user05)
	private TextView user5;
	@ViewInject(R.id.tr_textdata05)
	private TextView content5;
	@ViewInject(R.id.tr_time05)
	private TextView time5;
	@ViewInject(R.id.tr_commentnum05)
	private TextView com_num5;
	@ViewInject(R.id.tr_likenum05)
	private TextView like_num5;
    @ViewInject(R.id.me_type_1)
	private CheckBox type1;
	@ViewInject(R.id.me_type_2)
	private CheckBox type2;
	@ViewInject(R.id.me_type_3)
	private CheckBox type3;
	@ViewInject(R.id.me_type_4)
	private CheckBox type4;
	@ViewInject(R.id.me_type_5)
	private CheckBox type5;
	@ViewInject(R.id.ic_head01)
	private ImageView photo1;
	@ViewInject(R.id.ic_head02)
	private ImageView photo2;
	@ViewInject(R.id.ic_head03)
	private ImageView photo3;
	@ViewInject(R.id.ic_head04)
	private ImageView photo4;
	@ViewInject(R.id.ic_head05)
	private ImageView photo5;
	@ViewInject(R.id.tr_imagedata01)
	private ImageView pic1;
	@ViewInject(R.id.tr_imagedata02)
	private ImageView pic2;
	@ViewInject(R.id.tr_imagedata03)
	private ImageView pic3;
	@ViewInject(R.id.tr_imagedata04)
	private ImageView pic4;
	@ViewInject(R.id.tr_imagedata05)
	private ImageView pic5;
	Integer msgid01;
	Integer msgid02;
	Integer msgid03;
	Integer msgid04;
	Integer msgid05;
	String me_photo1;
	String me_photo2;
	String me_photo3;
	String me_photo4;
	String me_photo5;
	String me_pic1;
	String me_pic2;
	String me_pic3;
	String me_pic4;
	String me_pic5;
    String session;
	String path = Network.URL + "showfriendmessage";
	private static List<Message> msg_list= new ArrayList<Message>();
	private static List<MessageEntityWithBLOBs> mess_list =new ArrayList<MessageEntityWithBLOBs>();

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg){
			if(msg.what==1){
				Log.i("MESSAGE","进入");
				String qq = (String) msg.obj;
				Log.i("MESSAGE", qq);
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				Type type = new TypeToken<ArrayList<MessageEntityWithBLOBs>>(){}.getType();
				mess_list = gson.fromJson(qq,type);
				System.out.println(mess_list.size());
				if(mess_list.size()>= 5){
					findViewById(R.id.msg_01).setVisibility(View.VISIBLE);
					findViewById(R.id.msg_02).setVisibility(View.VISIBLE);
					findViewById(R.id.msg_03).setVisibility(View.VISIBLE);
					findViewById(R.id.msg_04).setVisibility(View.VISIBLE);
					findViewById(R.id.msg_05).setVisibility(View.VISIBLE);
					findViewById(R.id.nomore).setVisibility(View.VISIBLE);
					msgid01=mess_list.get(0).getMessageid();
					msgid02=mess_list.get(1).getMessageid();
					msgid03=mess_list.get(2).getMessageid();
					msgid04=mess_list.get(3).getMessageid();
					msgid05=mess_list.get(4).getMessageid();

					content1.setText(mess_list.get(0).getMessagecontent());
					time1.setText(mess_list.get(0).getMessagetime());
					com_num1.setText(String.valueOf(mess_list.get(0).getCommitnumber()));
					like_num1.setText(String.valueOf(mess_list.get(0).getLikenumber()));
					user1.setText(mess_list.get(0).getUsername());
					type1.setText(prename(mess_list.get(0).getMessagetype()));
					content2.setText(mess_list.get(1).getMessagecontent());
					time2.setText(mess_list.get(1).getMessagetime());
					com_num2.setText(String.valueOf(mess_list.get(1).getCommitnumber()));
					like_num2.setText(String.valueOf(mess_list.get(1).getLikenumber()));
					user2.setText(mess_list.get(1).getUsername());
					type2.setText(prename(mess_list.get(1).getMessagetype()));
					content3.setText(mess_list.get(2).getMessagecontent());
					time3.setText(mess_list.get(2).getMessagetime());
					com_num3.setText(String.valueOf(mess_list.get(2).getCommitnumber()));
					like_num3.setText(String.valueOf(mess_list.get(2).getLikenumber()));
					user3.setText(mess_list.get(2).getUsername());
					type3.setText(prename(mess_list.get(2).getMessagetype()));
					content4.setText(mess_list.get(3).getMessagecontent());
					time4.setText(mess_list.get(3).getMessagetime());
					com_num4.setText(String.valueOf(mess_list.get(3).getCommitnumber()));
					like_num4.setText(String.valueOf(mess_list.get(3).getLikenumber()));
					user4.setText(mess_list.get(3).getUsername());
					type4.setText(prename(mess_list.get(3).getMessagetype()));
					content5.setText(mess_list.get(4).getMessagecontent());
					time5.setText(mess_list.get(4).getMessagetime());
					com_num5.setText(String.valueOf(mess_list.get(4).getCommitnumber()));
					like_num5.setText(String.valueOf(mess_list.get(4).getLikenumber()));
					user5.setText(mess_list.get(4).getUsername());
					type5.setText(prename(mess_list.get(4).getMessagetype()));
					me_photo1 = mess_list.get(0).getUserimage();
					System.out.println(me_photo1);
					me_photo2 = mess_list.get(1).getUserimage();
					me_photo3 = mess_list.get(2).getUserimage();
					me_photo4 = mess_list.get(3).getUserimage();
					me_photo5 = mess_list.get(4).getUserimage();
					me_pic1 = mess_list.get(0).getMessageimage();
					System.out.println(me_pic1);
					me_pic2 = mess_list.get(1).getMessageimage();
					me_pic3 = mess_list.get(2).getMessageimage();
					me_pic4 = mess_list.get(3).getMessageimage();
					me_pic5 = mess_list.get(4).getMessageimage();
					getImage1(me_pic1);
					getImage2(me_pic2);
					getImage3(me_pic3);
					getImage4(me_pic4);
					getImage5(me_pic5);
					getUserImage1(me_photo1);
					getUserImage2(me_photo2);
					getUserImage3(me_photo3);
					getUserImage4(me_photo4);
					getUserImage5(me_photo5);
				}else if(mess_list.size() == 0){
					Toast.makeText(Message_activity.this,"暂无好友动态哦~", Toast.LENGTH_LONG).show();
				}else if(mess_list.size()==1){
					findViewById(R.id.msg_01).setVisibility(View.VISIBLE);
					findViewById(R.id.nomore).setVisibility(View.VISIBLE);
					msgid01=mess_list.get(0).getMessageid();
					content1.setText(mess_list.get(0).getMessagecontent());
					time1.setText(mess_list.get(0).getMessagetime());
					com_num1.setText(String.valueOf(mess_list.get(0).getCommitnumber()));
					like_num1.setText(String.valueOf(mess_list.get(0).getLikenumber()));
					user1.setText(mess_list.get(0).getUsername());
					type1.setText(prename(mess_list.get(0).getMessagetype()));
					me_pic1 = mess_list.get(0).getMessageimage();
					me_photo1 = mess_list.get(0).getUserimage();
					getImage1(me_pic1);
					getUserImage1(me_photo1);
				}else if(mess_list.size()==2){
					findViewById(R.id.msg_01).setVisibility(View.VISIBLE);
					findViewById(R.id.msg_02).setVisibility(View.VISIBLE);
					findViewById(R.id.nomore).setVisibility(View.VISIBLE);
					msgid01=mess_list.get(0).getMessageid();
					msgid02=mess_list.get(1).getMessageid();
					content1.setText(mess_list.get(0).getMessagecontent());
					time1.setText(mess_list.get(0).getMessagetime());
					com_num1.setText(String.valueOf(mess_list.get(0).getCommitnumber()));
					like_num1.setText(String.valueOf(mess_list.get(0).getLikenumber()));
					user1.setText(mess_list.get(0).getUsername());
					type1.setText(prename(mess_list.get(0).getMessagetype()));
					content2.setText(mess_list.get(1).getMessagecontent());
					time2.setText(mess_list.get(1).getMessagetime());
					com_num2.setText(String.valueOf(mess_list.get(1).getCommitnumber()));
					like_num2.setText(String.valueOf(mess_list.get(1).getLikenumber()));
					user2.setText(mess_list.get(1).getUsername());
					type2.setText(prename(mess_list.get(1).getMessagetype()));
					me_pic1 = mess_list.get(0).getMessageimage();
					me_pic2 = mess_list.get(1).getMessageimage();
					me_photo1 = mess_list.get(0).getUserimage();
					me_photo2 = mess_list.get(1).getUserimage();
					getImage1(me_pic1);
					getImage2(me_pic2);
					getUserImage1(me_photo1);
					getUserImage2(me_photo2);
				}else if(mess_list.size()==3){
					findViewById(R.id.msg_01).setVisibility(View.VISIBLE);
					findViewById(R.id.msg_02).setVisibility(View.VISIBLE);
					findViewById(R.id.msg_03).setVisibility(View.VISIBLE);
					findViewById(R.id.nomore).setVisibility(View.VISIBLE);
					msgid01=mess_list.get(0).getMessageid();
					msgid02=mess_list.get(1).getMessageid();
					msgid03=mess_list.get(2).getMessageid();
					content1.setText(mess_list.get(0).getMessagecontent());
					time1.setText(mess_list.get(0).getMessagetime());
					com_num1.setText(String.valueOf(mess_list.get(0).getCommitnumber()));
					like_num1.setText(String.valueOf(mess_list.get(0).getLikenumber()));
					user1.setText(mess_list.get(0).getUsername());
					type1.setText(prename(mess_list.get(0).getMessagetype()));
					content2.setText(mess_list.get(1).getMessagecontent());
					time2.setText(mess_list.get(1).getMessagetime());
					com_num2.setText(String.valueOf(mess_list.get(1).getCommitnumber()));
					like_num2.setText(String.valueOf(mess_list.get(1).getLikenumber()));
					user2.setText(mess_list.get(1).getUsername());
					type2.setText(prename(mess_list.get(1).getMessagetype()));
					content3.setText(mess_list.get(2).getMessagecontent());
					time3.setText(mess_list.get(2).getMessagetime());
					com_num3.setText(String.valueOf(mess_list.get(2).getCommitnumber()));
					like_num3.setText(String.valueOf(mess_list.get(2).getLikenumber()));
					user3.setText(mess_list.get(2).getUsername());
					type3.setText(prename(mess_list.get(2).getMessagetype()));
					me_pic1 = mess_list.get(0).getMessageimage();
					me_pic2 = mess_list.get(1).getMessageimage();
					me_pic3 = mess_list.get(2).getMessageimage();
					me_photo1 = mess_list.get(0).getUserimage();
					me_photo2 = mess_list.get(1).getUserimage();
					me_photo3 = mess_list.get(2).getUserimage();
					getImage1(me_pic1);
					getImage2(me_pic2);
					getImage3(me_pic3);
					getUserImage1(me_photo1);
					getUserImage2(me_photo2);
					getUserImage3(me_photo3);
				}else if(mess_list.size()==4){
					findViewById(R.id.msg_01).setVisibility(View.VISIBLE);
					findViewById(R.id.msg_02).setVisibility(View.VISIBLE);
					findViewById(R.id.msg_03).setVisibility(View.VISIBLE);
					findViewById(R.id.msg_04).setVisibility(View.VISIBLE);
					findViewById(R.id.nomore).setVisibility(View.VISIBLE);
					msgid01=mess_list.get(0).getMessageid();
					msgid02=mess_list.get(1).getMessageid();
					msgid03=mess_list.get(2).getMessageid();
					msgid04=mess_list.get(3).getMessageid();
					content1.setText(mess_list.get(0).getMessagecontent());
					time1.setText(mess_list.get(0).getMessagetime());
					com_num1.setText(String.valueOf(mess_list.get(0).getCommitnumber()));
					like_num1.setText(String.valueOf(mess_list.get(0).getLikenumber()));
					user1.setText(mess_list.get(0).getUsername());
					type1.setText(prename(mess_list.get(0).getMessagetype()));
					content2.setText(mess_list.get(1).getMessagecontent());
					time2.setText(mess_list.get(1).getMessagetime());
					com_num2.setText(String.valueOf(mess_list.get(1).getCommitnumber()));
					like_num2.setText(String.valueOf(mess_list.get(1).getLikenumber()));
					user2.setText(mess_list.get(1).getUsername());
					type2.setText(prename(mess_list.get(1).getMessagetype()));
					content3.setText(mess_list.get(2).getMessagecontent());
					time3.setText(mess_list.get(2).getMessagetime());
					com_num3.setText(String.valueOf(mess_list.get(2).getCommitnumber()));
					like_num3.setText(String.valueOf(mess_list.get(2).getLikenumber()));
					user3.setText(mess_list.get(2).getUsername());
					type3.setText(prename(mess_list.get(2).getMessagetype()));
					content4.setText(mess_list.get(3).getMessagecontent());
					time4.setText(mess_list.get(3).getMessagetime());
					com_num4.setText(String.valueOf(mess_list.get(3).getCommitnumber()));
					like_num4.setText(String.valueOf(mess_list.get(3).getLikenumber()));
					user4.setText(mess_list.get(3).getUsername());
					type4.setText(prename(mess_list.get(3).getMessagetype()));
					me_pic1 = mess_list.get(0).getMessageimage();
					me_pic2 = mess_list.get(1).getMessageimage();
					me_pic3 = mess_list.get(2).getMessageimage();
					me_pic4 = mess_list.get(3).getMessageimage();
					me_photo1 = mess_list.get(0).getUserimage();
					me_photo2 = mess_list.get(1).getUserimage();
					me_photo3 = mess_list.get(2).getUserimage();
					me_photo4 = mess_list.get(3).getUserimage();
					getImage1(me_pic1);
					getImage2(me_pic2);
					getImage3(me_pic3);
					getImage4(me_pic4);
					getUserImage1(me_photo1);
					getUserImage2(me_photo2);
					getUserImage3(me_photo3);
					getUserImage4(me_photo4);
				}
			}
		}
	};
	public void getImage1(final String userphoto1){
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
					System.out.println("进入handler");
					imgHandler.obtainMessage(1, pp).sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
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
	public void getImage2(final String userphoto2) {
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
					imgHandler.obtainMessage(2, pp).sendToTarget();
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
	public void getImage3(final String userphoto3) {
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
					imgHandler.obtainMessage(3, pp).sendToTarget();
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
	public void getImage4(final String userphoto4) {
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
					imgHandler.obtainMessage(4, pp).sendToTarget();
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
	public void getImage5(final String userphoto5) {
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
					imgHandler.obtainMessage(5, pp).sendToTarget();
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
	private Handler imgHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				System.out.println("第一个");
				Bitmap bmp = (Bitmap) msg.obj;
				pic1.setImageBitmap(bmp);
				pic1.setVisibility(View.VISIBLE);
			}
			if (msg.what == 2) {
				Bitmap bmp = (Bitmap) msg.obj;
				pic2.setImageBitmap(bmp);
				pic2.setVisibility(View.VISIBLE);
			}
			if (msg.what == 3) {
				Bitmap bmp = (Bitmap) msg.obj;
				pic3.setImageBitmap(bmp);
				pic3.setVisibility(View.VISIBLE);
			}
			if (msg.what == 4) {
				Bitmap bmp = (Bitmap) msg.obj;
				pic4.setImageBitmap(bmp);
				pic4.setVisibility(View.VISIBLE);
			}
			if (msg.what == 5) {
				Bitmap bmp = (Bitmap) msg.obj;
				pic5.setImageBitmap(bmp);
				pic5.setVisibility(View.VISIBLE);
			}
		}
	};
	private Handler photoHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Bitmap bmp = (Bitmap) msg.obj;
				photo1.setImageBitmap(bmp);
			}
			if (msg.what == 2) {
				Bitmap bmp = (Bitmap) msg.obj;
				photo2.setImageBitmap(bmp);
			}
			if (msg.what == 3) {
				Bitmap bmp = (Bitmap) msg.obj;
				photo3.setImageBitmap(bmp);
			}
			if (msg.what == 4) {
				Bitmap bmp = (Bitmap) msg.obj;
				photo4.setImageBitmap(bmp);
			}
			if (msg.what == 5) {
				Bitmap bmp = (Bitmap) msg.obj;
				photo5.setImageBitmap(bmp);
			}
		}
	};
	private String prename(int id){
		String name="";
		switch (id){
			case 1:
				name="美食";
				break;
			case 2:
				name="住宿";
				break;
			case 3:
				name="购物";
				break;
			case 4:
				name="自驾";
				break;
			case 5:
				name="古镇";
				break;
			case 6:
				name="山地";
				break;
			case 7:
				name="邮轮";
				break;
			case 8:
				name="穷游";
				break;
			case 9:
				name="摄影";
				break;
			case 10:
				name="国外";
				break;
			case 11:
				name="国内";
				break;
			case 12:
				name="情侣";
				break;
			case 13:
				name="其他";
				break;
		}
		return name;
	}
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.message);
        mContext = this;
        x.view().inject(this);
         /*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
            /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionID");
	    showMessage();
        textView.setText("好友动态");
        right_icon.setImageDrawable(getResources().getDrawable(R.drawable.add));
        //设置当前页面 首页 字体为红色
        Fragment exFragment = (Fragment) getSupportFragmentManager().findFragmentById(bottombar);
        Button home = (Button) exFragment.getView().findViewById(button_message);
        home.setTextColor(Color.parseColor("#f75b47"));
    }


    @Event(value = {R.id.button_friend, R.id.button_home,R.id.msg_01,R.id.msg_02,R.id.msg_03,R.id.msg_04,R.id.msg_05,R.id.iv_right})
    private void event(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_friend:
                intent = new Intent(mContext, Friend_activity.class);
				intent.putExtra("sessionID", session);
                startActivity(intent);
				finish();
                break;
            case R.id.button_home:
                intent = new Intent(mContext, Home_activity.class);
				intent.putExtra("sessionID", session);
                startActivity(intent);
				finish();
                break;
            case R.id.msg_01:
                intent=new Intent(mContext,SingleMessageActivity.class);
				intent.putExtra("sessionID", session);
				intent.putExtra("messageID",msgid01);
                startActivity(intent);
                break;
	        case R.id.msg_02:
		        intent=new Intent(mContext,SingleMessageActivity.class);
		        intent.putExtra("sessionID", session);
		        intent.putExtra("messageID",msgid02);
		        startActivity(intent);
		        break;
	        case R.id.msg_03:
		        intent=new Intent(mContext,SingleMessageActivity.class);
		        intent.putExtra("sessionID", session);
		        intent.putExtra("messageID",msgid03);
		        startActivity(intent);
		        break;
	        case R.id.msg_04:
		        intent=new Intent(mContext,SingleMessageActivity.class);
		        intent.putExtra("sessionID", session);
		        intent.putExtra("messageID",msgid04);
		        startActivity(intent);
		        break;
	        case R.id.msg_05:
		        intent=new Intent(mContext,SingleMessageActivity.class);
		        intent.putExtra("sessionID", session);
		        intent.putExtra("messageID",msgid05);
		        startActivity(intent);
		        break;
            case R.id.iv_right:
                intent=new Intent(mContext,SendMessageActivity.class);
                intent.putExtra("sessionID", session);
                startActivity(intent);
                break;
        }
    }
    public void showMessage(){
	    //新建一个线程，用于得到服务器响应的参数
	    new Thread(new Runnable() {
		    @Override
		    public void run() {
			    Response response = null;
			    try {
				    Request request = new Request.Builder()
						    .addHeader("cookie",session)
						    .url(path)
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

}