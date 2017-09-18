package com.example.aa.itravel.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.adapter.CollectionAdapter;
import com.example.aa.itravel.fragment.CollectionFragment;
import com.example.aa.itravel.tools.MessageEntityWithBLOBs;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_collection)
public class Collection_activity extends AppCompatActivity {
	String TAG = "COL_TO_Activity";
	@ViewInject(R.id.title_bar_name)
    private TextView title;
	private static MessageEntityWithBLOBs mMessage = new MessageEntityWithBLOBs();
    private ViewPager vp;
    private TabLayout tabLayout;
    private CollectionAdapter clAdapter;
    private List<Fragment> fragments = new ArrayList<>();
	//s用来保存sessionid     发送refresh请求
	String session;
	/*String path = Network.URL+"showcollectionoftopic";
	String path1 = Network.URL+"entertopic";
	String theme_1;

	String showcollectionofmsgpath=Network.URL+"showcollection";*/

	/*public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static List<Topic> topic_list =new ArrayList<Topic>();
	private static List<MessageEntityWithBLOBs> msg_list =new ArrayList<MessageEntityWithBLOBs>();
	private Handler MessageHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==1){
				Log.i(TAG,"进入");
				String qq = (String) msg.obj;
				Log.i(TAG, qq);
				Gson gson = new Gson();
				Type type = new TypeToken<ArrayList<MessageEntityWithBLOBs>>(){}.getType();
				msg_list = gson.fromJson(qq,type);
				Log.i("LIST",msg_list.toString());
				mMessage.setUsername(msg_list.get(0).getUsername());
				mMessage.setMessagetime(msg_list.get(0).getMessagetime());
				mMessage.setMessagecontent(msg_list.get(0).getMessagecontent());

			}
		}
	};*/
	/*private Handler TopicHandler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.what==1){
					Log.i(TAG,"进入");
					String qq = (String) msg.obj;
					Log.i(TAG, qq);
					Gson gson = new Gson();
					Type type = new TypeToken<ArrayList<Topic>>(){}.getType();
					topic_list = gson.fromJson(qq,type);
					theme_1 = topic_list.get(0).getTheme();
					Topic to = new Topic();
					to.setTheme(theme_1);
					to.setDate(topic_list.get(0).getDate());

				}
			}
	};*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        vp = (ViewPager) findViewById(R.id.cl_viewpage);
        tabLayout = (TabLayout) findViewById(R.id.cl_tab);
        title.setText("查看收藏");
        /*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
            /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionID");
		clAdapter = new CollectionAdapter(getSupportFragmentManager());
		fragments.add(CollectionFragment.newInstance("0",session));
		fragments.add(CollectionFragment.newInstance("1",session));
		clAdapter.setFragments(fragments);
		vp.setAdapter(clAdapter);
		//设置tabLayout
		tabLayout.setupWithViewPager(vp);
		//设置文字的颜色
		tabLayout.setTabTextColors(Color.GRAY, Color.parseColor("#f75b47"));
		//设置下划线的颜色
		tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f75b47"));
    }
	/*public void showCollectionTopic(){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
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
						TopicHandler.obtainMessage(1, response.body().string()).sendToTarget();
					} else {
						throw new IOException("Unexpected code:" + response);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	public void showcollectionMessage(){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Request request = new Request.Builder().addHeader("cookie",session).url(showcollectionofmsgpath).build();
					OkHttpClient okhttpc = new OkHttpClient();
					Call call = okhttpc.newCall(request);
					Response response = call.execute();
					Log.i(TAG,"响应成功");
					if (response.isSuccessful()) {
						Log.i(TAG,"响应成功");
						//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
						MessageHandler.obtainMessage(1, response.body().string()).sendToTarget();
					} else {
						throw new IOException("Unexpected code:" + response);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}*/
}
