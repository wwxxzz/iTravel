package com.example.aa.itravel.activity;

/**
 * Created by admin on 2017/9/13.
 */

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.ChatEntity;
import com.example.aa.itravel.tools.MessageBuffer;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ViewInject;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatDemoActivity extends Activity {
	private Context mContext;
	private Button sendButton = null;
	private EditText contentEditText = null;
	private ListView chatListView = null;
	private TextView titlebar = null;
	private List<MessageBuffer> chatList = null;
	private List<MessageBuffer> chatList1 = new ArrayList<MessageBuffer>();
	private ChatAdapter chatAdapter = null;
    String session;
	String friendname;
	String path = Network.URL+ "sendmessage";
	String path1 = Network.URL + "refresh";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		mContext = this;
		/*获取Intent中的Bundle对象*/
		Bundle bundle = this.getIntent().getExtras();
        /*获取Bundle中的数据，注意类型和key*/
		session = bundle.getString("sessionID");
		friendname=bundle.get("friendName").toString();
		titlebar = (TextView) this.findViewById(R.id.title_bar_name);
		titlebar.setText("与"+friendname+"聊天中");
		contentEditText = (EditText) this.findViewById(R.id.et_content);
		sendButton = (Button) this.findViewById(R.id.btn_send);
		chatListView = (ListView) this.findViewById(R.id.listview);
		chatList = new ArrayList<MessageBuffer>();
		chatAdapter = new ChatAdapter(this,chatList);
		showMessage();
		chatListView.setAdapter(chatAdapter);
		sendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!contentEditText.getText().toString().equals("")) {
					//发送消息
					send();
				}else {
					Toast.makeText(ChatDemoActivity.this, "Content is empty", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void showMessage(){
		Log.i("TEST","进入函数");
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {
					Request request = new Request.Builder()
							.addHeader("cookie",session)
							.url(path1)//.post(body)
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
				Log.i("dasa","进入xianhi");
				String qq = (String) msg.obj;
				System.out.println(qq);
				Gson gson = new Gson();
				Type type = new TypeToken<ArrayList<MessageBuffer>>(){}.getType();
				chatList1 = gson.fromJson(qq,type);
				System.out.println("转化");

				for(int i = 0;i<chatList1.size();i++){
					MessageBuffer chatEntity = new MessageBuffer();
					if(chatList1.get(i).getMessagebtype()==2&&chatList1.get(i).getFromusername().equals(friendname)){
						chatEntity.setComeMsg(true);
						chatEntity.setMessagebcontent(chatList1.get(i).getMessagebcontent());
						chatEntity.setSendtime(chatList1.get(i).getSendtime());
					}else if(chatList1.get(i).getMessagebtype()==2&&!chatList1.get(i).getFromusername().equals(friendname)){
						chatEntity.setComeMsg(false);
						chatEntity.setMessagebcontent(chatList1.get(i).getMessagebcontent());
						chatEntity.setSendtime(chatList1.get(i).getSendtime());
					}
					chatList.add(chatEntity);
				}
			}
		}
	};

	private void send(){
		final MessageBuffer chatEntity = new MessageBuffer();
		//chatEntity.setSendtime("2012-09-20 15:16:34");
		chatEntity.setMessagebcontent(contentEditText.getText().toString());
		chatEntity.setComeMsg(false);
		chatEntity.setTousername(friendname);

		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {
					Gson gson = new GsonBuilder().create();
					String content = gson.toJson(chatEntity);

					RequestBody body = RequestBody.create(JSON, content);
					System.out.println(content);
					Request request = new Request.Builder().addHeader("cookie",session)
							.url(path).post(body)
							.build();
					OkHttpClient okhttpc = new OkHttpClient();
					Call call = okhttpc.newCall(request);
					response = call.execute();
					if (response.isSuccessful()) {
						//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
						//showHandler.obtainMessage(1, response.body().string()).sendToTarget();
						System.out.println(response.body().string());
					} else {
						throw new IOException("Unexpected code:" + response);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

		chatList.add(chatEntity);
		chatAdapter.notifyDataSetChanged();
		chatListView.setSelection(chatList.size() - 1);
		contentEditText.setText("");
	}


	private  class ChatAdapter extends BaseAdapter{
		private Context context = null;
		private List<MessageBuffer> chatList = null;
		private LayoutInflater inflater = null;
		private int COME_MSG = 0;
		private int TO_MSG = 1;

		public ChatAdapter(Context context,List<MessageBuffer> chatList){
			this.context = context;
			this.chatList = chatList;
			inflater = LayoutInflater.from(this.context);
		}

		@Override
		public int getCount() {
			return chatList.size();
		}

		@Override
		public Object getItem(int position) {
			return chatList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			// 区别两种view的类型，标注两个不同的变量来分别表示各自的类型
			MessageBuffer entity = chatList.get(position);
			if (entity.isComeMsg())
			{
				return COME_MSG;
			}else{
				return TO_MSG;
			}
		}

		@Override
		public int getViewTypeCount() {
			// 这个方法默认返回1，如果希望listview的item都是一样的就返回1，我们这里有两种风格，返回2
			return 2;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ChatHolder chatHolder = null;
			if (convertView == null) {
				chatHolder = new ChatHolder();
				if (chatList.get(position).isComeMsg()) {
					convertView = inflater.inflate(R.layout.chat_from_item, null);
				}else {
					convertView = inflater.inflate(R.layout.chat_to_item, null);
				}
				chatHolder.timeTextView = (TextView) convertView.findViewById(R.id.tv_time);
				chatHolder.contentTextView = (TextView) convertView.findViewById(R.id.tv_content);
				chatHolder.userImageView = (ImageView) convertView.findViewById(R.id.iv_user_image);
				convertView.setTag(chatHolder);
			}else {
				chatHolder = (ChatHolder)convertView.getTag();
			}
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
			chatHolder.timeTextView.setText(str);
			chatHolder.contentTextView.setText(chatList.get(position).getMessagebcontent());
			//chatHolder.userImageView.setImageResource(chatList.get(position).getUserImage());
			return convertView;
		}

		private class ChatHolder{
			private TextView timeTextView;
			private ImageView userImageView;
			private TextView contentTextView;
		}
	}


}


