package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.CommentEntityWithBLOBs;
import com.example.aa.itravel.tools.Result;
import com.example.aa.itravel.tools.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@ContentView(R.layout.activity_push_commit)
public class PushCommit extends Activity {
	private Context mContext;
	String TAG = "PUSHCOMMENT_Activity";
	//s用来保存sessionid     发送refresh请求
	String session;
	String path = "http://223.3.74.248:8080/iTravel_Server_SSM/AndroidService/newcomment";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	@ViewInject(R.id.title_bar_name)
	private TextView titlebar;
	@ViewInject(R.id.iv_right)
	private ImageView right_icon;
	@ViewInject(R.id.iv_left)
	private ImageView left_icon;
	@ViewInject(R.id.editText)
	private EditText com_content;
	public Integer ID;
	public Integer index;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==1){
				String qq = (String) msg.obj;
				Log.i("COMMENT", qq);
				Gson gson = new Gson();
				Result re = gson.fromJson(qq, Result.class);
				String back = re.getResult();
				System.out.println(re.getResult());
				if(back.equals("true") ){
					Toast.makeText(PushCommit.this,"评论成功",Toast.LENGTH_SHORT).show();

					if(index == 1){
						Intent intent = new Intent(mContext,Topic_activity.class);
						intent.putExtra("sessionID", session);
						startActivity(intent);
						finish();
					}else if(index == 2){
						Intent intent = new Intent(mContext,Topic_activity2.class);
						intent.putExtra("sessionID", session);
						startActivity(intent);
						finish();
					}else if(index == 3){
						Intent intent = new Intent(mContext,Topic_activity3.class);
						intent.putExtra("sessionID", session);
						startActivity(intent);
						finish();
					}else if(index == 4){
						Intent intent = new Intent(mContext,Topic_activity4.class);
						intent.putExtra("sessionID", session);
						startActivity(intent);
						finish();
					}
				}else if(back.equals("existed")){
					Toast.makeText(PushCommit.this,"失效", Toast.LENGTH_LONG).show();
				}

			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("OUSH","评论");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext =this;
		x.view().inject(this);
		titlebar.setText("发表评论");
		right_icon.setImageResource(R.drawable.tick);
		left_icon.setImageResource(R.drawable.back);
		 /*获取Intent中的Bundle对象*/
		Bundle bundle = this.getIntent().getExtras();
            /*获取Bundle中的数据，注意类型和key*/
		session = bundle.getString("sessionID");
		ID = bundle.getInt("topicId");
		index = bundle.getInt("Index");
	}
	@Event(value={R.id.iv_right})
	private void event(View v){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {

					//回调
					CommentEntityWithBLOBs comm = new CommentEntityWithBLOBs();
					comm.setTopicid(ID);
					comm.setCommentcontent(com_content.toString());
					Gson gson = new GsonBuilder().create();
					String content = gson.toJson(comm);

					RequestBody body = RequestBody.create(JSON, content);

					Request request = new Request.Builder()
							.addHeader("cookie",session)
							.url(path)
							.post(body)
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
