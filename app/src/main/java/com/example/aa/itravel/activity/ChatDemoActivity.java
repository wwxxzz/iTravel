package com.example.aa.itravel.activity;

/**
 * Created by admin on 2017/9/13.
 */

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

import org.xutils.view.annotation.ViewInject;

public class ChatDemoActivity extends Activity {
	private Context mContext;
	private Button sendButton = null;
	private EditText contentEditText = null;
	private ListView chatListView = null;
	private TextView titlebar = null;
	private List<ChatEntity> chatList = null;
	private ChatAdapter chatAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		mContext = this;
		titlebar = (TextView) this.findViewById(R.id.title_bar_name);
		titlebar.setText("聊天中");
		contentEditText = (EditText) this.findViewById(R.id.et_content);
		sendButton = (Button) this.findViewById(R.id.btn_send);
		chatListView = (ListView) this.findViewById(R.id.listview);
		chatList = new ArrayList<ChatEntity>();
		ChatEntity chatEntity = null;
		for (int i = 0; i < 2; i++) {
			chatEntity = new ChatEntity();
			if (i % 2 == 0) {
				chatEntity.setComeMsg(false);
				chatEntity.setContent("Hello");
				chatEntity.setChatTime("2012-09-20 15:12:32");
			}else {
				chatEntity.setComeMsg(true);
				chatEntity.setContent("Hello,nice to meet you!");
				chatEntity.setChatTime("2012-09-20 15:13:32");
			}
			chatList.add(chatEntity);
		}

		chatAdapter = new ChatAdapter(this,chatList);
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



	private void send(){
		ChatEntity chatEntity = new ChatEntity();
		chatEntity.setChatTime("2012-09-20 15:16:34");
		chatEntity.setContent(contentEditText.getText().toString());
		chatEntity.setComeMsg(false);
		chatList.add(chatEntity);
		chatAdapter.notifyDataSetChanged();
		chatListView.setSelection(chatList.size() - 1);
		contentEditText.setText("");
	}

	private  class ChatAdapter extends BaseAdapter{
		private Context context = null;
		private List<ChatEntity> chatList = null;
		private LayoutInflater inflater = null;
		private int COME_MSG = 0;
		private int TO_MSG = 1;

		public ChatAdapter(Context context,List<ChatEntity> chatList){
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
			ChatEntity entity = chatList.get(position);
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
			chatHolder.timeTextView.setText(chatList.get(position).getChatTime());
			chatHolder.contentTextView.setText(chatList.get(position).getContent());
			chatHolder.userImageView.setImageResource(chatList.get(position).getUserImage());
			return convertView;
		}

		private class ChatHolder{
			private TextView timeTextView;
			private ImageView userImageView;
			private TextView contentTextView;
		}
	}
}
