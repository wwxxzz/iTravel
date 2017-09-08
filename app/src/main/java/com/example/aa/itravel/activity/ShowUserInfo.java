package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
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
import java.util.List;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 2017/9/8.
 */
@ContentView(R.layout.userinfo)
public class ShowUserInfo extends Activity {
	private Context mContext;
	String TAG = "SHOW_USER_INFO_Activity";
	//s用来保存sessionid     发送refresh请求
	String session;
	Response response;
	OkHttpClient client = new OkHttpClient();
	String path = "http://223.3.82.239:8080/iTravel_Server_SSM/AndroidService/personalinfo";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	@ViewInject(R.id.showname)
	private TextView user_name;
	@ViewInject(R.id.showlocation)
	private TextView user_location;
	@ViewInject(R.id.showoccupation)
	private TextView user_career;
	@ViewInject(R.id.showemail)
	private TextView user_email;
	@ViewInject(R.id.showphone)
	private TextView user_phone;
	@ViewInject(R.id.showbirthday)
	private TextView user_birth;
	@ViewInject(R.id.showsex)
	private TextView user_sex;
	@ViewInject(R.id.toolbar)
	private Toolbar toolbar;
	@ViewInject(R.id.title_bar_name)
	private  TextView titlebar;
	@ViewInject(R.id.iv_right)
	private ImageView right_icon;

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
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				user_birth.setText(df.format(re.getUserbirth()));
				user_sex.setText(re.getUsersex());
			}

		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG,"onCreate");
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.login);
		x.view().inject(this);

		/*获取Intent中的Bundle对象*/
		Bundle bundle = this.getIntent().getExtras();

		mContext = this;
		titlebar.setText("个人资料");
		right_icon.setImageResource(R.drawable.edit);
        /*获取Bundle中的数据，注意类型和key*/
		session = bundle.getString("sessionId");
		Log.i(TAG,session);
		new Thread(runnable).start();  //启动子线程
	}


	//新线程进行网络请求
	Runnable runnable = new Runnable(){
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
					mmHandler.obtainMessage(1, response.body().string()).sendToTarget();
				} else {
					throw new IOException("Unexpected code:" + response);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	@Event(value={R.id.iv_right})
	private void event(View view){
		Intent intent = new Intent(mContext,ChangeUserInfo.class);
		intent.putExtra("sessionId", session);
		startActivity(intent);
		finish();
	}
}
