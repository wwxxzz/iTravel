package com.example.aa.itravel.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.MessageEntityWithBLOBs;
import com.example.aa.itravel.tools.MessageType;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.PreferredType;
import com.example.aa.itravel.tools.User;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.aa.itravel.R.id.bottombar;
import static com.example.aa.itravel.R.id.button_home;

//import org.xutils.view.annotation.ContentView;
;

/**
 * Created by aa on 2017/9/5.
 */
@ContentView(R.layout.home_main)
public class Home_activity extends AppCompatActivity {

    private Context mContext;
    private ViewPager view_pager;
    private LinearLayout ll_dotGroup;
    //private TextView newsTitle;
    //存储图片
    private int imgResIds[] = new int[]{R.drawable.topic4,R.drawable.topic2,R.drawable.topic3,R.drawable.topic1};
    //存储目录
    //private String textview[] = new String[]{"门前大桥下","游过一群鸭","快来快来数一数","二四六七八","233333"};

    //记录当前滚动的位置
    private int curIndex =0;
    PicsAdapter picsAdapter;

    //@ViewInject(R.id.title_bar_name)
    //private TextView textView;

    String TAG = "HOME_Activity";
    //s用来保存sessionid     发送refresh请求
    String session;
    String user_photo;
    List<Integer> msgid_list=new ArrayList<Integer>();
    @ViewInject(R.id.iv_photo)
    private ImageView userphoto;
    @ViewInject(R.id.tv_name)
    private TextView username;
    @ViewInject(R.id.tv_bq1)
    private TextView bq1;
    @ViewInject(R.id.tv_bq2)
    private TextView bq2;
    @ViewInject(R.id.tv_bq3)
    private TextView bq3;
    @ViewInject(R.id.tv_bq4)
    private TextView bq4;
    @ViewInject(R.id.tv_bq5)
    private TextView bq5;

    @ViewInject(R.id.tv_username1)
    private TextView uname1;
    @ViewInject(R.id.tv_username2)
    private TextView uname2;
    @ViewInject(R.id.tv_username3)
    private TextView uname3;
    @ViewInject(R.id.tv_username4)
    private TextView uname4;
    @ViewInject(R.id.tv_username5)
    private TextView uname5;
    @ViewInject(R.id.tv_username6)
    private TextView uname6;

    @ViewInject(R.id.tv_topicComment1)
    private TextView msgcontent1;
    @ViewInject(R.id.tv_topicComment2)
    private TextView msgcontent2;
    @ViewInject(R.id.tv_topicComment3)
    private TextView msgcontent3;
    @ViewInject(R.id.tv_topicComment4)
    private TextView msgcontent4;
    @ViewInject(R.id.tv_topicComment5)
    private TextView msgcontent5;
    @ViewInject(R.id.tv_topicComment6)
    private TextView msgcontent6;

    @ViewInject(R.id.relativeLayout1)
    private RelativeLayout recommend1;
    @ViewInject(R.id.relativeLayout2)
    private RelativeLayout recommend2;
    @ViewInject(R.id.relativeLayout3)
    private RelativeLayout recommend3;
    @ViewInject(R.id.relativeLayout4)
    private RelativeLayout recommend4;
    @ViewInject(R.id.relativeLayout5)
    private RelativeLayout recommend5;
    @ViewInject(R.id.relativeLayout6)
    private RelativeLayout recommend6;
//    @ViewInject(R.id.me_view1)
//    private RelativeLayout view1;
//    @ViewInject(R.id.me_view2)
//    private RelativeLayout view2;
//    @ViewInject(R.id.me_view3)
//    private RelativeLayout view3;
//    @ViewInject(R.id.me_view4)
//    private RelativeLayout view4;
//    @ViewInject(R.id.me_view5)
//    private RelativeLayout view5;
//    @ViewInject(R.id.me_view6)
//    private RelativeLayout view6;
    String me_photo1;
    String me_photo2;
    String me_photo3;
    String me_photo4;
    String me_photo5;
    String me_photo6;

    @ViewInject(R.id.image_photo1)
    private ImageView photo1;
    @ViewInject(R.id.image_photo2)
    private ImageView photo2;
    @ViewInject(R.id.image_photo3)
    private ImageView photo3;
    @ViewInject(R.id.image_photo4)
    private ImageView photo4;
    @ViewInject(R.id.image_photo5)
    private ImageView photo5;
    @ViewInject(R.id.image_photo6)
    private ImageView photo6;

    private static List<PreferredType> pre_list =new ArrayList<PreferredType>();
    private static List<MessageEntityWithBLOBs> msg_list = new ArrayList<MessageEntityWithBLOBs>();
    String path = Network.URL+"showpreference";
    String path1 = Network.URL+"showrecommend";
    String path2 = Network.URL+ "personalinfo";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private int bqnumber=1;

    //设置标签颜色
    private void bqColor(int bq){
        switch (bq){
            case 1:
                bq1.setTextColor(Color.parseColor("#f75b47"));
                bq2.setTextColor(Color.parseColor("#595959"));
                bq3.setTextColor(Color.parseColor("#595959"));
                bq4.setTextColor(Color.parseColor("#595959"));
                bq5.setTextColor(Color.parseColor("#595959"));
                break;
            case 2:
                bq1.setTextColor(Color.parseColor("#595959"));
                bq2.setTextColor(Color.parseColor("#f75b47"));
                bq3.setTextColor(Color.parseColor("#595959"));
                bq4.setTextColor(Color.parseColor("#595959"));
                bq5.setTextColor(Color.parseColor("#595959"));
                break;
            case 3:
                bq1.setTextColor(Color.parseColor("#595959"));
                bq2.setTextColor(Color.parseColor("#595959"));
                bq3.setTextColor(Color.parseColor("#f75b47"));
                bq4.setTextColor(Color.parseColor("#595959"));
                bq5.setTextColor(Color.parseColor("#595959"));
                break;
            case 4:
                bq1.setTextColor(Color.parseColor("#595959"));
                bq2.setTextColor(Color.parseColor("#595959"));
                bq3.setTextColor(Color.parseColor("#595959"));
                bq4.setTextColor(Color.parseColor("#f75b47"));
                bq5.setTextColor(Color.parseColor("#595959"));
                break;
            case 5:
                bq1.setTextColor(Color.parseColor("#595959"));
                bq2.setTextColor(Color.parseColor("#595959"));
                bq3.setTextColor(Color.parseColor("#595959"));
                bq4.setTextColor(Color.parseColor("#595959"));
                bq5.setTextColor(Color.parseColor("#f75b47"));
                break;
            default:
                break;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.home_main);
        mContext =this;
        x.view().inject(this);

        setViewPager();

        //textView.setText("首页推荐");


        Bundle bundle = this.getIntent().getExtras();
            /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionID");

        //设置当前页面 首页 字体为红色
        Fragment exFragment = (Fragment)getSupportFragmentManager().findFragmentById(bottombar);
        Button home =(Button) exFragment.getView().findViewById(button_home);
        home.setTextColor(Color.parseColor("#f75b47"));


            /*FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tt =fm.beginTransaction();
            tt.replace(R.id.bottombar,new BottomBar());
            tt.commit();*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //监听drawer拉出、隐藏
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        showUserInfo();
        showPreference();

//        //如果偏好列表不空，就默认显示第一个偏好的内容
//        //如果列表为空，就显示美食标签的内容
//        if(pre_list!=null){
//            showRecommend(1);
//            //showRecommend(pre_list.get(0).getTypeid());
//        }else{
//            showRecommend(1);
//        }

        showRecommend(1);
        bqColor(bqnumber);


    }
    // @Event(value = {R.id.bt_friend,R.id.bt_message,R.id.prefence,R.id.footprint})


    //标题栏右侧
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.addfriend:
                intent = new Intent(mContext,AddNewFriendActivity.class);
                intent.putExtra("sessionID",session);
                startActivity(intent);
                break;
            case R.id.newmessage:
                intent = new Intent(mContext,SendMessageActivity.class);
                intent.putExtra("sessionID",session);
                startActivity(intent);
                break;
            default:break;
        }
        return true;

    }


    //点击事件
    @Event(value = {R.id.btn_exit,R.id.button_friend,R.id.button_message,R.id.bt_entertopic,R.id.bt_info,R.id.bt_footprint,
            R.id.bt_collection,R.id.bt_preference,R.id.tv_bq1,R.id.tv_bq2,R.id.tv_bq3,R.id.tv_bq4,R.id.tv_bq5})
    private void event(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.button_friend:
                intent = new Intent(mContext,Friend_activity.class);
                intent.putExtra("sessionID",session);
                startActivity(intent);
                break;
            case R.id.button_message:
                intent = new Intent(mContext,Message_activity.class);
                intent.putExtra("sessionID", session);
                startActivity(intent);
                break;
            case R.id.bt_entertopic:
                switch (curIndex){
                    case 0:
                        intent = new Intent(mContext,Topic_activity.class);
                        intent.putExtra("sessionID", session);
                        Log.i(TAG,"进入话题1");
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(mContext,Topic_activity2.class);
                        intent.putExtra("sessionID", session);
                        Log.i(TAG,"进入话题2");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(mContext,Topic_activity3.class);
                        intent.putExtra("sessionID", session);
                        Log.i(TAG,"进入话题3");
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(mContext,Topic_activity4.class);
                        intent.putExtra("sessionID", session);
                        Log.i(TAG,"进入话题4");
                        startActivity(intent);
                        break;
                }
                break;
            case R.id.btn_exit:
                System.out.println("退出");
                AlertDialog exitDialog=new AlertDialog.Builder(Home_activity.this).setTitle("系  统  提  示")//设置对话框标题
                        .setMessage("确 定 要 退 出 登 录 吗 ？")//设置显示的内容
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {//添加确定按钮
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                        Home_activity.this.finish();
                                        Intent i = new Intent(mContext,Login_activity.class);
                                        startActivity(i);
                                    }
                                }).setNegativeButton("返回",
                                new DialogInterface.OnClickListener() {//添加返回按钮
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {//响应事件
                                        Log.i("alertdialog"," 请保存数据！");
                                    }
                                }).create();//在按键响应事件中显示此对话框
                exitDialog.show();
                break;
            case R.id.bt_info:
                intent = new Intent(mContext,ShowUserInfo.class);
                intent.putExtra("sessionID", session);
                startActivity(intent);
                break;
            case R.id.bt_footprint:
                intent = new Intent(mContext,FootPrintTestActivity.class);
                intent.putExtra("sessionID",session);
                startActivity(intent);
                break;
            case R.id.bt_collection:
                intent = new Intent(mContext,Collection_activity.class);
                intent.putExtra("sessionID", session);
                startActivity(intent);
                break;
            case R.id.bt_preference:
                intent = new Intent(mContext,Preference_activity.class);
                intent.putExtra("sessionID", session);
                startActivity(intent);
                break;
            case R.id.tv_bq1:
                System.out.println("点击了第1个标签");
                bqnumber=1;
                bqColor(bqnumber);
                if(pre_list!=null){
                    showRecommend(pre_list.get(0).getTypeid());
                }else{
                    showRecommend(1);}
                break;
            case R.id.tv_bq2:
                System.out.println("点击了第2个标签");
                bqnumber=2;
                bqColor(bqnumber);
                if(pre_list!=null){
                    showRecommend(pre_list.get(1).getTypeid());
                }else{
                    showRecommend(2);}
                break;
            case R.id.tv_bq3:
                System.out.println("点击了第3个标签");
                bqnumber=3;
                bqColor(bqnumber);
                if(pre_list!=null){
                    showRecommend(pre_list.get(2).getTypeid());
                }else{
                    showRecommend(3);}
                break;
            case R.id.tv_bq4:
                System.out.println("点击了第4个标签");
                bqnumber=4;
                bqColor(bqnumber);
                if(pre_list!=null){
                    showRecommend(pre_list.get(3).getTypeid());
                }else{
                    showRecommend(4);}
                break;
            case R.id.tv_bq5:
                System.out.println("点击了第5个标签");
                bqnumber=5;
                bqColor(bqnumber);
                if(pre_list!=null){
                    showRecommend(pre_list.get(4).getTypeid());
                }else{
                    showRecommend(5);
                }
                break;
        }
    }

@Event(value = {R.id.relativeLayout1,R.id.relativeLayout2,R.id.relativeLayout3,R.id.relativeLayout4,R.id.relativeLayout5,R.id.relativeLayout6})
private void msgevent(View view){
    Intent intent;
    switch (view.getId()){
        case R.id.relativeLayout1:
            intent=new Intent(mContext,SingleMessageActivity.class);
            intent.putExtra("sessionID",session);
            intent.putExtra("messageID",msgid_list.get(0));
            startActivity(intent);
            break;
        case R.id.relativeLayout2:
            intent=new Intent(mContext,SingleMessageActivity.class);
            intent.putExtra("sessionID",session);
            intent.putExtra("messageID",msgid_list.get(1));
            startActivity(intent);
            break;
        case R.id.relativeLayout3:
            intent=new Intent(mContext,SingleMessageActivity.class);
            intent.putExtra("sessionID",session);
            intent.putExtra("messageID",msgid_list.get(2));
            startActivity(intent);
            break;
        case R.id.relativeLayout4:
            intent=new Intent(mContext,SingleMessageActivity.class);
            intent.putExtra("sessionID",session);
            intent.putExtra("messageID",msgid_list.get(3));
            startActivity(intent);
            break;
        case R.id.relativeLayout5:
            intent=new Intent(mContext,SingleMessageActivity.class);
            intent.putExtra("sessionID",session);
            intent.putExtra("messageID",msgid_list.get(4));
            startActivity(intent);
            break;
        case R.id.relativeLayout6:
            intent=new Intent(mContext,SingleMessageActivity.class);
            intent.putExtra("sessionID",session);
            intent.putExtra("messageID",msgid_list.get(5));
            startActivity(intent);
            break;
    }
}
    //显示推荐动态
    public void showRecommend(final int type){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    MessageType mt = new MessageType();
                    mt.setTypeid(type);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(mt);

                    RequestBody body = RequestBody.create(JSON, content);
                    Request request = new Request.Builder()
                            .addHeader("cookie",session)
                            .url(path1).post(body)
                            .build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    response = call.execute();

                    if (response.isSuccessful()) {
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui

                        rrHandler.obtainMessage(1, response.body().string()).sendToTarget();
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

    private Handler rrHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<MessageEntityWithBLOBs>>(){}.getType();
                msg_list = gson.fromJson(qq,type);
                int mnumber;
                if(msg_list!=null){
                    mnumber=msg_list.size();
                    for(int i=0;i<msg_list.size();i++){
                        msgid_list.add(msg_list.get(i).getMessageid());
                    }
                    switch (mnumber){
                        case 0:
                            recommend1.setVisibility(View.GONE);
                            recommend2.setVisibility(View.GONE);
                            recommend3.setVisibility(View.GONE);
                            recommend4.setVisibility(View.GONE);
                            recommend5.setVisibility(View.GONE);
                            recommend6.setVisibility(View.GONE);
                            break;
                        case 1:
                            recommend1.setVisibility(View.VISIBLE);
                            uname1.setText(msg_list.get(0).getUsername());
                            msgcontent1.setText(msg_list.get(0).getMessagecontent());
                            me_photo1 = msg_list.get(0).getUserimage();
                            getUserImage1(me_photo1);
                            break;
                        case 2:
                            recommend1.setVisibility(View.VISIBLE);
                            recommend2.setVisibility(View.VISIBLE);
                            uname1.setText(msg_list.get(0).getUsername());
                            msgcontent1.setText(msg_list.get(0).getMessagecontent());
                            uname2.setText(msg_list.get(1).getUsername());
                            msgcontent2.setText(msg_list.get(1).getMessagecontent());
                            me_photo1 = msg_list.get(0).getUserimage();
                            getUserImage1(me_photo1);
                            me_photo2 = msg_list.get(1).getUserimage();
                            getUserImage2(me_photo2);
                            break;
                        case 3:
                            recommend1.setVisibility(View.VISIBLE);
                            recommend2.setVisibility(View.VISIBLE);
                            recommend3.setVisibility(View.VISIBLE);
                            uname1.setText(msg_list.get(0).getUsername());
                            msgcontent1.setText(msg_list.get(0).getMessagecontent());
                            uname2.setText(msg_list.get(1).getUsername());
                            msgcontent2.setText(msg_list.get(1).getMessagecontent());
                            uname3.setText(msg_list.get(2).getUsername());
                            msgcontent3.setText(msg_list.get(2).getMessagecontent());
                            me_photo1 = msg_list.get(0).getUserimage();
                            getUserImage1(me_photo1);
                            me_photo2 = msg_list.get(1).getUserimage();
                            getUserImage2(me_photo2);
                            me_photo3 = msg_list.get(2).getUserimage();
                            getUserImage3(me_photo3);
                            break;
                        case 4:
                            recommend1.setVisibility(View.VISIBLE);
                            recommend2.setVisibility(View.VISIBLE);
                            recommend3.setVisibility(View.VISIBLE);
                            recommend4.setVisibility(View.VISIBLE);
                            uname1.setText(msg_list.get(0).getUsername());
                            msgcontent1.setText(msg_list.get(0).getMessagecontent());
                            uname2.setText(msg_list.get(1).getUsername());
                            msgcontent2.setText(msg_list.get(1).getMessagecontent());
                            uname3.setText(msg_list.get(2).getUsername());
                            msgcontent3.setText(msg_list.get(2).getMessagecontent());
                            uname4.setText(msg_list.get(3).getUsername());
                            msgcontent4.setText(msg_list.get(3).getMessagecontent());
                            me_photo1 = msg_list.get(0).getUserimage();
                            getUserImage1(me_photo1);
                            me_photo2 = msg_list.get(1).getUserimage();
                            getUserImage2(me_photo2);
                            me_photo3 = msg_list.get(2).getUserimage();
                            getUserImage3(me_photo3);
                            me_photo4 = msg_list.get(3).getUserimage();
                            getUserImage4(me_photo4);
                            break;
                        case 5:
                            recommend1.setVisibility(View.VISIBLE);
                            recommend2.setVisibility(View.VISIBLE);
                            recommend3.setVisibility(View.VISIBLE);
                            recommend4.setVisibility(View.VISIBLE);
                            recommend5.setVisibility(View.VISIBLE);
                            uname1.setText(msg_list.get(0).getUsername());
                            msgcontent1.setText(msg_list.get(0).getMessagecontent());
                            uname2.setText(msg_list.get(1).getUsername());
                            msgcontent2.setText(msg_list.get(1).getMessagecontent());
                            uname3.setText(msg_list.get(2).getUsername());
                            msgcontent3.setText(msg_list.get(2).getMessagecontent());
                            uname4.setText(msg_list.get(3).getUsername());
                            msgcontent4.setText(msg_list.get(3).getMessagecontent());
                            uname5.setText(msg_list.get(4).getUsername());
                            msgcontent5.setText(msg_list.get(4).getMessagecontent());
                            me_photo1 = msg_list.get(0).getUserimage();
                            getUserImage1(me_photo1);
                            me_photo2 = msg_list.get(1).getUserimage();
                            getUserImage2(me_photo2);
                            me_photo3 = msg_list.get(2).getUserimage();
                            getUserImage3(me_photo3);
                            me_photo4 = msg_list.get(3).getUserimage();
                            getUserImage4(me_photo4);
                            me_photo5 = msg_list.get(4).getUserimage();
                            getUserImage5(me_photo5);
                            break;
                        case 6:
                            recommend1.setVisibility(View.VISIBLE);
                            recommend2.setVisibility(View.VISIBLE);
                            recommend3.setVisibility(View.VISIBLE);
                            recommend4.setVisibility(View.VISIBLE);
                            recommend5.setVisibility(View.VISIBLE);
                            recommend6.setVisibility(View.VISIBLE);
                            uname1.setText(msg_list.get(0).getUsername());
                            msgcontent1.setText(msg_list.get(0).getMessagecontent());
                            uname2.setText(msg_list.get(1).getUsername());
                            msgcontent2.setText(msg_list.get(1).getMessagecontent());
                            uname3.setText(msg_list.get(2).getUsername());
                            msgcontent3.setText(msg_list.get(2).getMessagecontent());
                            uname4.setText(msg_list.get(3).getUsername());
                            msgcontent4.setText(msg_list.get(3).getMessagecontent());
                            uname5.setText(msg_list.get(4).getUsername());
                            msgcontent5.setText(msg_list.get(4).getMessagecontent());
                            uname6.setText(msg_list.get(5).getUsername());
                            msgcontent6.setText(msg_list.get(5).getMessagecontent());
                            me_photo1 = msg_list.get(0).getUserimage();
                            getUserImage1(me_photo1);
                            me_photo2 = msg_list.get(1).getUserimage();
                            getUserImage2(me_photo2);
                            me_photo3 = msg_list.get(2).getUserimage();
                            getUserImage3(me_photo3);
                            me_photo4 = msg_list.get(3).getUserimage();
                            getUserImage4(me_photo4);
                            me_photo5 = msg_list.get(4).getUserimage();
                            getUserImage5(me_photo5);
                            me_photo6 = msg_list.get(5).getUserimage();
                            getUserImage6(me_photo6);
                            break;
                        default:
                            recommend1.setVisibility(View.VISIBLE);
                            recommend2.setVisibility(View.VISIBLE);
                            recommend3.setVisibility(View.VISIBLE);
                            recommend4.setVisibility(View.VISIBLE);
                            recommend5.setVisibility(View.VISIBLE);
                            recommend6.setVisibility(View.VISIBLE);
                            uname1.setText(msg_list.get(0).getUsername());
                            msgcontent1.setText(msg_list.get(0).getMessagecontent());
                            uname2.setText(msg_list.get(1).getUsername());
                            msgcontent2.setText(msg_list.get(1).getMessagecontent());
                            uname3.setText(msg_list.get(2).getUsername());
                            msgcontent3.setText(msg_list.get(2).getMessagecontent());
                            uname4.setText(msg_list.get(3).getUsername());
                            msgcontent4.setText(msg_list.get(3).getMessagecontent());
                            uname5.setText(msg_list.get(4).getUsername());
                            msgcontent5.setText(msg_list.get(4).getMessagecontent());
                            uname6.setText(msg_list.get(5).getUsername());
                            msgcontent6.setText(msg_list.get(5).getMessagecontent());
                            me_photo1 = msg_list.get(0).getUserimage();
                            getUserImage1(me_photo1);
                            me_photo2 = msg_list.get(1).getUserimage();
                            getUserImage2(me_photo2);
                            me_photo3 = msg_list.get(2).getUserimage();
                            getUserImage3(me_photo3);
                            me_photo4 = msg_list.get(3).getUserimage();
                            getUserImage4(me_photo4);
                            me_photo5 = msg_list.get(4).getUserimage();
                            getUserImage5(me_photo5);
                            me_photo6 = msg_list.get(5).getUserimage();
                            getUserImage6(me_photo6);
                            break;
                    }
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
    public void getUserImage6(final String userphoto6){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    URL url = new URL(Network.IMGURL + userphoto6);
                    Bitmap pp = BitmapFactory.decodeStream(url.openStream());
                    android.os.Message msg = new android.os.Message();
                    //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                    photoHandler.obtainMessage(6, pp).sendToTarget();
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
            if (msg.what == 6) {
                Bitmap bmp = (Bitmap) msg.obj;
                photo6.setImageBitmap(bmp);
            }
        }
    };

    //显示标签
    public void showPreference(){
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
                        mmHandler.obtainMessage(1, response.body().string()).sendToTarget();
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

    private Handler mmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;

                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<PreferredType>>(){}.getType();
                pre_list = gson.fromJson(qq,type);
//                System.out.println(pre_list.size());
                int pnumber;
                if(pre_list!=null){
                    pnumber=pre_list.size();
                    switch (pnumber){
                        case 1:
                            bq1.setVisibility(View.VISIBLE);
                            bq1.setText(prename(pre_list.get(0).getTypeid()));
                            break;
                        case 2:
                            bq1.setVisibility(View.VISIBLE);
                            bq2.setVisibility(View.VISIBLE);
                            bq1.setText(prename(pre_list.get(0).getTypeid()));
                            bq2.setText(prename(pre_list.get(1).getTypeid()));
                            break;
                        case 3:
                            bq1.setVisibility(View.VISIBLE);
                            bq2.setVisibility(View.VISIBLE);
                            bq3.setVisibility(View.VISIBLE);
                            bq1.setText(prename(pre_list.get(0).getTypeid()));
                            bq2.setText(prename(pre_list.get(1).getTypeid()));
                            bq3.setText(prename(pre_list.get(2).getTypeid()));
                            break;
                        case 4:
                            bq1.setVisibility(View.VISIBLE);
                            bq2.setVisibility(View.VISIBLE);
                            bq3.setVisibility(View.VISIBLE);
                            bq4.setVisibility(View.VISIBLE);
                            bq1.setText(prename(pre_list.get(0).getTypeid()));
                            bq2.setText(prename(pre_list.get(1).getTypeid()));
                            bq3.setText(prename(pre_list.get(2).getTypeid()));
                            bq4.setText(prename(pre_list.get(3).getTypeid()));
                            break;
                        case 5:
                            bq1.setVisibility(View.VISIBLE);
                            bq2.setVisibility(View.VISIBLE);
                            bq3.setVisibility(View.VISIBLE);
                            bq4.setVisibility(View.VISIBLE);
                            bq5.setVisibility(View.VISIBLE);
                            bq1.setText(prename(pre_list.get(0).getTypeid()));
                            bq2.setText(prename(pre_list.get(1).getTypeid()));
                            bq3.setText(prename(pre_list.get(2).getTypeid()));
                            bq4.setText(prename(pre_list.get(3).getTypeid()));
                            bq5.setText(prename(pre_list.get(4).getTypeid()));
                            break;
                        default:
                            bq1.setVisibility(View.VISIBLE);
                            bq2.setVisibility(View.VISIBLE);
                            bq3.setVisibility(View.VISIBLE);
                            bq4.setVisibility(View.VISIBLE);
                            bq5.setVisibility(View.VISIBLE);
                            bq1.setText(prename(pre_list.get(0).getTypeid()));
                            bq2.setText(prename(pre_list.get(1).getTypeid()));
                            bq3.setText(prename(pre_list.get(2).getTypeid()));
                            bq4.setText(prename(pre_list.get(3).getTypeid()));
                            bq5.setText(prename(pre_list.get(4).getTypeid()));
                            break;
                    }
                }else {
                    bq1.setVisibility(View.VISIBLE);
                    bq2.setVisibility(View.VISIBLE);
                    bq3.setVisibility(View.VISIBLE);
                    bq4.setVisibility(View.VISIBLE);
                    bq5.setVisibility(View.VISIBLE);
                    bq1.setText("美食");
                    bq2.setText("住宿");
                    bq3.setText("购物");
                    bq4.setText("自驾");
                    bq5.setText("古镇");
                }
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






    //轮播图
    private void setViewPager() {

        //newsTitle=(TextView)findViewById(R.id.NewsTitle);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        ll_dotGroup = (LinearLayout) findViewById(R.id.dotgroup);

        PicsAdapter picsAdapter = new PicsAdapter(); // 创建适配器
        picsAdapter.setData(imgResIds);
        view_pager.setAdapter(picsAdapter); // 设置适配器

        view_pager.setOnPageChangeListener(new MyPageChangeListener()); //设置页面切换监听器

        initPoints(imgResIds.length); //初始化图片小圆点
        startAutoScroll(); // 开启自动播放
    }

    // 初始化图片轮播的小圆点和目录
    private void initPoints(int count) {
        for (int i = 0; i < count; i++) {

            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    20, 20);
            params.setMargins(0, 0, 20, 0);
            iv.setLayoutParams(params);

            iv.setImageResource(R.drawable.point_normal);

            ll_dotGroup.addView(iv);

        }
        ((ImageView) ll_dotGroup.getChildAt(curIndex))
                .setImageResource(R.drawable.point_focus);

        //newsTitle.setText(textview[curIndex]);
    }

    // 自动播放
    private void startAutoScroll() {
        ScheduledExecutorService scheduledExecutorService = Executors
                .newSingleThreadScheduledExecutor();
        // 每隔4秒钟切换一张图片
        //scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5,
        //4, TimeUnit.SECONDS);
    }

    // 切换图片任务
    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int count = picsAdapter.getCount();
                    view_pager.setCurrentItem((curIndex + 1) % count);
                }
            });
        }
    }

    // 定义ViewPager控件页面切换监听器
    class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            ImageView imageView1 = (ImageView) ll_dotGroup.getChildAt(position);
            ImageView imageView2 = (ImageView) ll_dotGroup.getChildAt(curIndex);
            if (imageView1 != null) {
                imageView1.setImageResource(R.drawable.point_focus);
            }
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.point_normal);
            }
            curIndex = position;
            //newsTitle.setText(textview[curIndex]);

        }


        boolean b = false;

        @Override
        public void onPageScrollStateChanged(int state) {
            //这段代码可不加，主要功能是实现切换到末尾后返回到第一张
            switch (state) {
                case 1:// 手势滑动
                    b = false;
                    break;
                case 2:// 界面切换中
                    b = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (view_pager.getCurrentItem() == view_pager.getAdapter()
                            .getCount() - 1 && !b) {
                        view_pager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (view_pager.getCurrentItem() == 0 && !b) {
                        view_pager.setCurrentItem(view_pager.getAdapter()
                                .getCount() - 1);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    // 定义ViewPager控件适配器
    class PicsAdapter extends PagerAdapter {

        private List<ImageView> views = new ArrayList<ImageView>();

        @Override
        public int getCount() {
            if (views == null) {
                return 0;
            }
            return views.size();
        }

        public void setData(int[] imgResIds) {
            for (int i = 0; i < imgResIds.length; i++) {
                ImageView iv = new ImageView(Home_activity.this);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                iv.setLayoutParams(params);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                //设置ImageView的属性
                iv.setImageResource(imgResIds[i]);
                views.add(iv);
            }
        }

        public Object getItem(int position) {
            if (position < getCount())
                return views.get(position);
            return null;
        }


        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }


        @Override
        public void destroyItem(View container, int position, Object object) {

            if (position < views.size())
                ((ViewPager) container).removeView(views.get(position));
        }


        @Override
        public int getItemPosition(Object object) {
            return views.indexOf(object);
        }


        @Override
        public Object instantiateItem(View container, int position) {
            if (position < views.size()) {
                final ImageView imageView = views.get(position);
                ((ViewPager) container).addView(imageView);
                return views.get(position);
            }
            return null;
        }

    }
    public void showUserInfo(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().addHeader("cookie",session).url(path2).build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i(TAG,"响应成功");
                    if (response.isSuccessful()) {
                        Log.i(TAG,"响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        uHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    private Handler uHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                User re = gson.fromJson(qq, User.class);
                username.setText(re.getUsername());
                user_photo = re.getUserphoto();
                System.out.println(user_photo);
                getImage(user_photo);
            }

        }

    };
    public void getImage(String userphoto){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    URL url = new URL(Network.IMGURL + user_photo);
                    Bitmap pp = BitmapFactory.decodeStream(url.openStream());
                    Message msg = new Message();
//					msg.what = 1;
//					msg.obj = pp;
                    //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                    imgHandler.obtainMessage(1, pp).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private Handler imgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Bitmap bmp = (Bitmap) msg.obj;
                userphoto.setImageBitmap(bmp);
            }

        }
    };
}
