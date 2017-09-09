package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.Result;
import com.example.aa.itravel.tools.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 2017/9/8.
 */
@ContentView(R.layout.changeinfo)
public class ChangeUserInfo extends Activity {
	String TAG = "CHANGE_INFO_Activity";
	String session;
	OkHttpClient client = new OkHttpClient();
	String path1 = "http://223.3.88.189:8080/iTravel_Server_SSM/AndroidService/personalinfo";
	String path = "http://223.3.88.189:8080/iTravel_Server_SSM/AndroidService/editpersonalinfo";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	@ViewInject(R.id.changename)
	private EditText user_name;
	@ViewInject(R.id.changelocation)
	private EditText user_location;
	@ViewInject(R.id.changeoccupation)
	private EditText user_career;
	@ViewInject(R.id.changeemail)
	private EditText user_email;
	@ViewInject(R.id.changephone)
	private EditText user_phone;
	@ViewInject(R.id.changebirthday)
	private EditText user_birth;
	@ViewInject(R.id.changesex)
	private EditText user_sex;
	@ViewInject(R.id.toolbar)
	private Toolbar toolbar;
	@ViewInject(R.id.title_bar_name)
	private  TextView titlebar;
	@ViewInject(R.id.iv_right)
	private ImageView right_icon;

	private Context mContext;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==1){
				String qq = (String) msg.obj;
				Log.i(TAG, qq);
				Gson gson = new Gson();
				Result re = gson.fromJson(qq, Result.class);
				String back = re.getResult();
				System.out.println(re.getResult());
				if(back.equals("true") ){
					Log.i(TAG,"修改成功");
					//Log.i(TAG,"sessionId"+s);
					Toast.makeText(ChangeUserInfo.this,"修改成功，即将跳转", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent = new Intent(mContext, ShowUserInfo.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("sessionId", session);
					startActivity(intent);
					finish();
				}else {
					//Toast.makeText(Login_activity.this,"用户名或密码错误", Toast.LENGTH_LONG).show();
				}

			}

		}
	};
	private Handler mmHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==1){
				Log.i(TAG,"进入");
				//		Toast.makeText(ShowUserInfo.this,"成功", Toast.LENGTH_SHORT);
				String qq = (String) msg.obj;
				Log.i(TAG, qq);
				Gson gson = new Gson();
				User re = gson.fromJson(qq, User.class);
				user_name.setText(re.getUsername());
				user_location.setText(re.getUserlocation());
				user_career.setText(re.getUsercareer());
				user_email.setText(re.getUseremail());
				user_phone.setText(re.getUsertel());
				user_birth.setText(re.getUserbirth());
				user_sex.setText(re.getUsersex());
			}

		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.login);
		x.view().inject(this);
        /*获取Intent中的Bundle对象*/
		Bundle bundle = this.getIntent().getExtras();
		mContext = this;
		titlebar.setText("修改资料");
		right_icon.setImageResource(R.drawable.save);

		session = bundle.getString("sessionId");
		Log.i("CHANGE",session);
		new Thread(runnable).start();  //启动子线程
	}


	@Event(value={R.id.iv_right})
	private void event(View v){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			String username = user_name.getText().toString();
			String userlocation = user_location.getText().toString();
			String usercareer = user_career.getText().toString();
			String useremail = user_email.getText().toString();
			String userphone = user_phone.getText().toString();
			String userbirth = user_birth.getText().toString();
			String usersex = user_sex.getText().toString();

			@Override
			public void run() {
				Response response = null;
				try {
					//回调
					User user = new User();
					user.setUsertel(userphone);
					user.setUsername(username);
					user.setUserlocation(userlocation);
					user.setUsercareer(usercareer);
					user.setUseremail(useremail);
					user.setUsersex(usersex);
					user.setUserbirth(userbirth);

					Gson gson = new GsonBuilder().create();
					String content = gson.toJson(user);

					RequestBody body = RequestBody.create(JSON, content);

					Request request = new Request.Builder().addHeader("cookie",session).post(body).url(path).build();
					OkHttpClient okhttpc = new OkHttpClient();
					Call call = okhttpc.newCall(request);
					response = call.execute();
					if (response.isSuccessful()) {
						//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
						mHandler.obtainMessage(1, response.body().string()).sendToTarget();
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

	//新线程进行网络请求
	Runnable runnable = new Runnable(){
		@Override
		public void run() {
			try {
				Request request = new Request.Builder().addHeader("cookie",session).url(path1).build();
				OkHttpClient okhttpc = new OkHttpClient();
				Call call = okhttpc.newCall(request);
				Response response = call.execute();
				Log.i(TAG,"响应成功");
				if (response.isSuccessful()) {
					Log.i(TAG,"响应成功");
					//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
					mmHandler.obtainMessage(1, response.body().string()).sendToTarget();
				} else {
					throw new IOException("Unexpected code:" + response);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
}
