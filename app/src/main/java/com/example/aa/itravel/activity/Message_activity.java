package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

	Integer msgid01;
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

				msgid01=mess_list.get(0).getMessageid();

				content1.setText(mess_list.get(0).getMessagecontent());
				//time1.setText(mess_list.get(0).getMessagetime());
				com_num1.setText(String.valueOf(mess_list.get(0).getCommitnumber()));
				like_num1.setText(String.valueOf(mess_list.get(0).getLikenumber()));
                user1.setText(mess_list.get(0).getUsername());
				content2.setText(mess_list.get(1).getMessagecontent());
				//time2.setText(mess_list.get(1).getMessagetime());
				com_num2.setText(String.valueOf(mess_list.get(1).getCommitnumber()));
				like_num2.setText(String.valueOf(mess_list.get(1).getLikenumber()));
				user2.setText(mess_list.get(1).getUsername());
				content3.setText(mess_list.get(2).getMessagecontent());
				//time3.setText(mess_list.get(2).getMessagetime());
				com_num3.setText(String.valueOf(mess_list.get(2).getCommitnumber()));
				like_num3.setText(String.valueOf(mess_list.get(2).getLikenumber()));
				user3.setText(mess_list.get(2).getUsername());
			}

		}
	};

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


    @Event(value = {R.id.button_friend, R.id.button_home,R.id.msg_01,R.id.iv_right})
    private void event(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_friend:
                intent = new Intent(mContext, Friend_activity.class);
                startActivity(intent);
                break;
            case R.id.button_home:
                intent = new Intent(mContext, Home_activity.class);
                startActivity(intent);
                break;
            case R.id.msg_01:
                intent=new Intent(mContext,SingleMessageActivity.class);
				intent.putExtra("sessionID", session);
				intent.putExtra("messageID",msgid01);
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