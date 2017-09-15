package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.adapter.FriendTopAdapter;
import com.example.aa.itravel.fragment.FriendTopFragment;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.User;
import com.google.gson.Gson;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.aa.itravel.R.id.bottombar;
import static com.example.aa.itravel.R.id.button_friend;


/**
 * Created by aa on 2017/9/5.
 */

@ContentView(R.layout.friend)
public class Friend_activity extends AppCompatActivity {
    private Context mContext;


    @ViewInject(R.id.title_bar_name)
    private TextView textView;
    @ViewInject(R.id.iv_right)
    private ImageView right_button;
    String session;
   // @ViewInject(R.id.title_bar_name)
   // private TextView textView;


    private ViewPager vp;
    private TabLayout tabLayout;
    private FriendTopAdapter FtAdapter;
    private List<Fragment> fragments = new ArrayList<>();


    String path = Network.URL+"showfriends";
    //String path1 = Network.URL+"entertopic";
    //String theme_1;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static List<User> friend_list =new ArrayList<User>();

    String friendname_01;
    String friendname_02;
    String friendname_03;
    String friendname_04;
    String friendname_05;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.friend);
        mContext =this;
        x.view().inject(this);
        textView.setText("好友消息");
        right_button.setImageDrawable(getResources().getDrawable(R.drawable.add));

        Bundle bundle = this.getIntent().getExtras();
            /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionID");


        //设置当前页面 首页 字体为红色
        Fragment exFragment = (Fragment)getSupportFragmentManager().findFragmentById(bottombar);
        Button home =(Button) exFragment.getView().findViewById(button_friend);
        home.setTextColor(Color.parseColor("#f75b47"));

        vp = (ViewPager) findViewById(R.id.FriendTop_viewpage);
        tabLayout = (TabLayout) findViewById(R.id.FriendTop_tab);

//        FtAdapter = new FriendTopAdapter(getSupportFragmentManager());
//        fragments.add(FriendTopFragment.newInstance("0",session));
//        fragments.add(FriendTopFragment.newInstance("1",session));
//        fragments.add(FriendTopFragment.newInstance("2",session));
//        FtAdapter.setFragments(fragments);
//        vp.setAdapter(FtAdapter);
//        //设置tabLayout
//        tabLayout.setupWithViewPager(vp);
//        //设置文字的颜色
//        tabLayout.setTabTextColors(Color.GRAY, Color.BLUE);
//        //设置下划线的颜色
//        tabLayout.setSelectedTabIndicatorColor(Color.BLUE);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        //setSupportActionBar(toolbar);

        showFriendRequest();


    }

    @Event(value = {R.id.button_home,R.id.button_message,R.id.iv_right })
    private void event(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_message:
                intent = new Intent(mContext, Message_activity.class);
                intent.putExtra("sessionID", session);
                startActivity(intent);
                break;
            case R.id.button_home:
                intent = new Intent(mContext, Home_activity.class);
                intent.putExtra("sessionID",session);
                startActivity(intent);
                break;
            case R.id.iv_right:
                intent =new Intent(mContext,AddNewFriendActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void showFriendRequest(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().addHeader("cookie",session).url(path).build();
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
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                //Log.i(TAG,"进入");
                String qq = (String) msg.obj;
                //Log.i(TAG, qq);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<User>>(){}.getType();
                friend_list = gson.fromJson(qq,type);
                int fnumber=friend_list.size();
                if(fnumber==1){
                    friendname_01 = friend_list.get(0).getUsername();
                    User friend1 = new User();
                    friend1.setUsername(friendname_01);
                    fragments.add(FriendTopFragment.newInstance("0",session,fnumber,friendname_01));
                    fragments.add(FriendTopFragment.newInstance("1",session,fnumber,friendname_01));
                }if(fnumber==2){
                    friendname_01 = friend_list.get(0).getUsername();
                    User friend1 = new User();
                    friend1.setUsername(friendname_01);
                    friendname_02 = friend_list.get(1).getUsername();
                    User friend2 = new User();
                    friend2.setUsername(friendname_02);
                    fragments.add(FriendTopFragment.newInstance("0",session,fnumber,friendname_01,friendname_02));
                    fragments.add(FriendTopFragment.newInstance("1",session,fnumber,friendname_01,friendname_02));
                    fragments.add(FriendTopFragment.newInstance("2",session,fnumber,friendname_01,friendname_02));
                }if(fnumber==3){
                    friendname_01 = friend_list.get(0).getUsername();
                    User friend1 = new User();
                    friend1.setUsername(friendname_01);
                    friendname_02 = friend_list.get(1).getUsername();
                    User friend2 = new User();
                    friend2.setUsername(friendname_02);
                    friendname_03 = friend_list.get(2).getUsername();
                    User friend3 = new User();
                    friend3.setUsername(friendname_03);
                    fragments.add(FriendTopFragment.newInstance("0",session,fnumber,friendname_01,friendname_02,friendname_03));
                    fragments.add(FriendTopFragment.newInstance("1",session,fnumber,friendname_01,friendname_02,friendname_03));
                    fragments.add(FriendTopFragment.newInstance("2",session,fnumber,friendname_01,friendname_02,friendname_03));
                }if(fnumber==4){
                    friendname_01 = friend_list.get(0).getUsername();
                    User friend1 = new User();
                    friend1.setUsername(friendname_01);
                    friendname_02 = friend_list.get(1).getUsername();
                    User friend2 = new User();
                    friend2.setUsername(friendname_02);
                    friendname_03 = friend_list.get(2).getUsername();
                    User friend3 = new User();
                    friend3.setUsername(friendname_03);
                    friendname_04 = friend_list.get(3).getUsername();
                    User friend4 = new User();
                    friend4.setUsername(friendname_04);
                    fragments.add(FriendTopFragment.newInstance("0",session,fnumber,friendname_01,friendname_02,friendname_03,friendname_04));
                    fragments.add(FriendTopFragment.newInstance("1",session,fnumber,friendname_01,friendname_02,friendname_03,friendname_04));
                    fragments.add(FriendTopFragment.newInstance("2",session,fnumber,friendname_01,friendname_02,friendname_03,friendname_04));
                }if(fnumber==5){
                    friendname_01 = friend_list.get(0).getUsername();
                    User friend1 = new User();
                    friend1.setUsername(friendname_01);
                    friendname_02 = friend_list.get(1).getUsername();
                    User friend2 = new User();
                    friend2.setUsername(friendname_02);
                    friendname_03 = friend_list.get(2).getUsername();
                    User friend3 = new User();
                    friend3.setUsername(friendname_03);
                    friendname_04 = friend_list.get(3).getUsername();
                    User friend4 = new User();
                    friend4.setUsername(friendname_04);
                    friendname_05 = friend_list.get(4).getUsername();
                    User friend5 = new User();
                    friend5.setUsername(friendname_05);
                    fragments.add(FriendTopFragment.newInstance("0",session,fnumber,friendname_01,friendname_02,friendname_03,friendname_04,friendname_05));
                    fragments.add(FriendTopFragment.newInstance("1",session,fnumber,friendname_01,friendname_02,friendname_03,friendname_04,friendname_05));
                    fragments.add(FriendTopFragment.newInstance("2",session,fnumber,friendname_01,friendname_02,friendname_03,friendname_04,friendname_05));

                }if(fnumber==0){
                fragments.add(FriendTopFragment.newInstance("0",session,fnumber));
                fragments.add(FriendTopFragment.newInstance("1",session,fnumber));
                fragments.add(FriendTopFragment.newInstance("2",session,fnumber));
                }




                FtAdapter = new FriendTopAdapter(getSupportFragmentManager());
//                fragments.add(FriendTopFragment.newInstance("0",session,friendname_01/*,friendname_02*/));
//                fragments.add(FriendTopFragment.newInstance("1",session,friendname_01/*,friendname_02*/));
//                fragments.add(FriendTopFragment.newInstance("2",session,friendname_01/*,friendname_02*/));
                FtAdapter.setFragments(fragments);
                vp.setAdapter(FtAdapter);
                //设置tabLayout
                tabLayout.setupWithViewPager(vp);
                //设置文字的颜色
                tabLayout.setTabTextColors(Color.GRAY, Color.BLUE);
                //设置下划线的颜色
                tabLayout.setSelectedTabIndicatorColor(Color.BLUE);

            }
        }
    };
}
