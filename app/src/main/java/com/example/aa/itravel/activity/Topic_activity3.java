package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.CommentEntityWithBLOBs;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.Result;
import com.example.aa.itravel.tools.Topic;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@ContentView(R.layout.activity_topic)
public class Topic_activity3 extends Activity {
    private Context mContext;

    String TAG = "TOPIC3_Activity";
    //s用来保存sessionid     发送refresh请求
    String session;
    String path = Network.URL+ "gettopic3";
    String path1 =Network.URL+ "entertopic";
    String path2 = Network.URL+"newcollectionfortopic";
    String path3 = Network.URL+"topicifcollected";
    String path4 = Network.URL+"likecomment";
    int idd = 0;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @ViewInject(R.id.title_bar_name)
    private TextView textView;
    @ViewInject(R.id.iv_right)
    private ImageView right_icon;
    @ViewInject(R.id.img_comment)
    private ImageView comment_img;
    @ViewInject(R.id.tv_topic)
    private TextView topic_theme;
    @ViewInject(R.id.tv_topic_con)
    private TextView topic_content;
    @ViewInject(R.id.tv_username1)
    private  TextView user1_name;
    @ViewInject(R.id.tv_username2)
    private  TextView user2_name;
    @ViewInject(R.id.tv_username3)
    private  TextView user3_name;
    @ViewInject(R.id.tv_username4)
    private  TextView user4_name;
    @ViewInject(R.id.tv_username5)
    private  TextView user5_name;
    @ViewInject(R.id.tv_username6)
    private  TextView user6_name;
    @ViewInject(R.id.tv_topicComment1)
    private TextView user1_comment;
    @ViewInject(R.id.tv_topicComment2)
    private TextView user2_comment;
    @ViewInject(R.id.tv_topicComment3)
    private TextView user3_comment;
    @ViewInject(R.id.tv_topicComment4)
    private TextView user4_comment;
    @ViewInject(R.id.tv_topicComment5)
    private TextView user5_comment;
    @ViewInject(R.id.tv_topicComment6)
    private TextView user6_comment;
    @ViewInject(R.id.tv_likenumber1)
    private TextView user1_like;
    @ViewInject(R.id.tv_likenumber2)
    private TextView user2_like;
    @ViewInject(R.id.tv_likenumber3)
    private TextView user3_like;
    @ViewInject(R.id.tv_likenumber4)
    private TextView user4_like;
    @ViewInject(R.id.tv_likenumber5)
    private TextView user5_like;
    @ViewInject(R.id.tv_likenumber6)
    private TextView user6_like;
    //	@ViewInject(R.id.topic_id)
//	private TextView topicID;
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
    @ViewInject(R.id.iv_like1)
    private ImageView like1;
    @ViewInject(R.id.iv_like2)
    private ImageView like2;
    @ViewInject(R.id.iv_like3)
    private ImageView like3;
    @ViewInject(R.id.iv_like4)
    private ImageView like4;
    @ViewInject(R.id.iv_like5)
    private ImageView like5;
    @ViewInject(R.id.iv_like6)
    private ImageView like6;
    String user_photo1;
    String user_photo2;
    String user_photo3;
    String user_photo4;
    String user_photo5;
    String user_photo6;
    int comid1;
    int comid2;
    int comid3;
    int comid4;
    int comid5;
    int comid6;
    @ViewInject(R.id.image_topic)
    private ImageView topic_pic;
    String topic_img;
    public Integer topicID;
    private static List<CommentEntityWithBLOBs> com_list =new ArrayList<CommentEntityWithBLOBs>();

    private Handler mmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Topic top = gson.fromJson(qq, Topic.class);
                topic_theme.setText(top.getTheme());
                topic_content.setText(top.getTopiccontent());
                topicID = top.getTopicid();
//				topicID.setText(top.getTopicid());
                topic_img = top.getTopicimg();
                getTopicImg(topic_img);
                Log.i(TAG,"进入函数");
                showComment();
                showCollection();

            }
        }
    };
    private Handler lcHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                switch(idd)
                {
                    case 1:
                        user1_like.setText(String.valueOf(com_list.get(0).getLikenumber()+1));
                        like1.setClickable(false);
                        break;
                    case 2:
                        user2_like.setText(String.valueOf(com_list.get(1).getLikenumber()+1));
                        like2.setClickable(false);
                        break;
                    case 3:
                        user3_like.setText(String.valueOf(com_list.get(2).getLikenumber()+1));
                        like3.setClickable(false);
                        break;
                    case 4:
                        user4_like.setText(String.valueOf(com_list.get(3).getLikenumber()+1));
                        like4.setClickable(false);
                        break;
                    case 5:
                        user5_like.setText(String.valueOf(com_list.get(4).getLikenumber()+1));
                        like5.setClickable(false);
                        break;
                    case 6:
                        user6_like.setText(String.valueOf(com_list.get(5).getLikenumber()+1));
                        like6.setClickable(false);
                        break;
                }
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Result rs = gson.fromJson(qq, Result.class);
                String back = rs.getResult();
                System.out.println(rs.getResult());
                if(back.equals("true") ){
                    Log.i(TAG,"即将跳转");
                    Toast.makeText(Topic_activity3.this,"点赞成功", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Topic_activity3.this,"可能网有点卡", Toast.LENGTH_LONG).show();
                }
                Log.i(TAG,"进入函数");


            }
        }
    };
    public void getTopicImg(final String userphoto){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    URL url = new URL(Network.IMGURL + userphoto);
                    Bitmap pp = BitmapFactory.decodeStream(url.openStream());
                    android.os.Message msg = new android.os.Message();
                    //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                    System.out.println("进入handler");
                    topicHandler.obtainMessage(1, pp).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private Handler topicHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                Bitmap bmp = (Bitmap) msg.obj;
                topic_pic.setImageBitmap(bmp);
            }
        }
    };
    private Handler cHandler = new Handler(){
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
                    right_icon.setImageResource(R.drawable.star_c);
                    Toast.makeText(Topic_activity3.this,"收藏成功", Toast.LENGTH_SHORT).show();
                }else{
                    right_icon.setImageResource(R.drawable.star_c);
                    Toast.makeText(Topic_activity3.this,"已收藏", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    private Handler dHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Gson gson = new Gson();
                Result re = gson.fromJson(qq, Result.class);
                String back = re.getResult();
                System.out.println(re.getResult());
                if(back.equals("true") ){
                    right_icon.setImageResource(R.drawable.star_c);
                }else{
                }
            }
        }
    };
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                Log.i(TAG,"进入");
                String qq = (String) msg.obj;
                Log.i(TAG, qq);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<CommentEntityWithBLOBs>>(){}.getType();
                com_list = gson.fromJson(qq,type);
                if(com_list!=null){
                    if(com_list.size()==1){
                        findViewById(R.id.relativeLayout1).setVisibility(View.VISIBLE);
                        user1_name.setText(com_list.get(0).getCommentatorname());
                        user1_comment.setText(com_list.get(0).getCommentcontent());
                        if(com_list.get(0).getLikenumber()==null){
                            user1_like.setText(String.valueOf(0));
                        }else{
                            user1_like.setText(String.valueOf(com_list.get(0).getLikenumber()));
                        }
                        user_photo1 = com_list.get(0).getCommentatorimg();
                        comid1 = com_list.get(0).getCommentid();
                        getComImg1(user_photo1);
                    }else if(com_list.size()==2){
                        findViewById(R.id.relativeLayout1).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout2).setVisibility(View.VISIBLE);
                        user1_name.setText(com_list.get(0).getCommentatorname());
                        user2_name.setText(com_list.get(1).getCommentatorname());
                        user1_comment.setText(com_list.get(0).getCommentcontent());
                        user2_comment.setText(com_list.get(1).getCommentcontent());
                        comid1 = com_list.get(0).getCommentid();
                        comid2 = com_list.get(1).getCommentid();
                        if(com_list.get(0).getLikenumber()==null){
                            user1_like.setText(String.valueOf(0));
                        }else{
                            user1_like.setText(String.valueOf(com_list.get(0).getLikenumber()));
                        }
                        if(com_list.get(1).getLikenumber()==null){
                            user2_like.setText(String.valueOf(0));
                        }else{
                            user2_like.setText(String.valueOf(com_list.get(1).getLikenumber()));
                        }
                        user_photo1 = com_list.get(0).getCommentatorimg();
                        getComImg1(user_photo1);
                        user_photo2 = com_list.get(1).getCommentatorimg();
                        getComImg2(user_photo2);
                    }else if(com_list.size()==3){
                        findViewById(R.id.relativeLayout1).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout2).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout3).setVisibility(View.VISIBLE);
                        user1_name.setText(com_list.get(0).getCommentatorname());
                        user2_name.setText(com_list.get(1).getCommentatorname());
                        user3_name.setText(com_list.get(2).getCommentatorname());
                        user1_comment.setText(com_list.get(0).getCommentcontent());
                        user2_comment.setText(com_list.get(1).getCommentcontent());
                        user3_comment.setText(com_list.get(2).getCommentcontent());
                        comid1 = com_list.get(0).getCommentid();
                        comid2 = com_list.get(1).getCommentid();
                        comid3 = com_list.get(2).getCommentid();
                        if(com_list.get(0).getLikenumber()==null){
                            user1_like.setText(String.valueOf(0));
                        }else{
                            user1_like.setText(String.valueOf(com_list.get(0).getLikenumber()));
                        }
                        if(com_list.get(1).getLikenumber()==null){
                            user2_like.setText(String.valueOf(0));
                        }else{
                            user2_like.setText(String.valueOf(com_list.get(1).getLikenumber()));
                        }
                        if(com_list.get(2).getLikenumber()==null){
                            user3_like.setText(String.valueOf(0));
                        }else{
                            user3_like.setText(String.valueOf(com_list.get(2).getLikenumber()));
                        }
                        user_photo1 = com_list.get(0).getCommentatorimg();
                        getComImg1(user_photo1);
                        user_photo2 = com_list.get(1).getCommentatorimg();
                        getComImg2(user_photo2);
                        user_photo3 = com_list.get(2).getCommentatorimg();
                        getComImg3(user_photo3);
                    }else if(com_list.size()==4){
                        findViewById(R.id.relativeLayout1).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout2).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout3).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout4).setVisibility(View.VISIBLE);
                        user1_name.setText(com_list.get(0).getCommentatorname());
                        user2_name.setText(com_list.get(1).getCommentatorname());
                        user3_name.setText(com_list.get(2).getCommentatorname());
                        user4_name.setText(com_list.get(3).getCommentatorname());
                        user1_comment.setText(com_list.get(0).getCommentcontent());
                        user2_comment.setText(com_list.get(1).getCommentcontent());
                        user3_comment.setText(com_list.get(2).getCommentcontent());
                        user4_comment.setText(com_list.get(3).getCommentcontent());
                        comid1 = com_list.get(0).getCommentid();
                        comid2 = com_list.get(1).getCommentid();
                        comid3 = com_list.get(2).getCommentid();
                        comid4= com_list.get(3).getCommentid();
                        if(com_list.get(0).getLikenumber()==null){
                            user1_like.setText(String.valueOf(0));
                        }else{
                            user1_like.setText(String.valueOf(com_list.get(0).getLikenumber()));
                        }
                        if(com_list.get(1).getLikenumber()==null){
                            user2_like.setText(String.valueOf(0));
                        }else{
                            user2_like.setText(String.valueOf(com_list.get(1).getLikenumber()));
                        }
                        if(com_list.get(2).getLikenumber()==null){
                            user3_like.setText(String.valueOf(0));
                        }else{
                            user3_like.setText(String.valueOf(com_list.get(2).getLikenumber()));
                        }
                        if(com_list.get(3).getLikenumber()==null){
                            user4_like.setText(String.valueOf(0));
                        }else{
                            user4_like.setText(String.valueOf(com_list.get(3).getLikenumber()));
                        }
                        user_photo1 = com_list.get(0).getCommentatorimg();
                        getComImg1(user_photo1);
                        user_photo2 = com_list.get(1).getCommentatorimg();
                        getComImg2(user_photo2);
                        user_photo3 = com_list.get(2).getCommentatorimg();
                        getComImg3(user_photo3);
                        user_photo4 = com_list.get(3).getCommentatorimg();
                        getComImg4(user_photo4);
                    }else if(com_list.size()==5){
                        findViewById(R.id.relativeLayout1).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout2).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout3).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout4).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout5).setVisibility(View.VISIBLE);
                        user1_name.setText(com_list.get(0).getCommentatorname());
                        user2_name.setText(com_list.get(1).getCommentatorname());
                        user3_name.setText(com_list.get(2).getCommentatorname());
                        user4_name.setText(com_list.get(3).getCommentatorname());
                        user5_name.setText(com_list.get(4).getCommentatorname());
                        user1_comment.setText(com_list.get(0).getCommentcontent());
                        user2_comment.setText(com_list.get(1).getCommentcontent());
                        user3_comment.setText(com_list.get(2).getCommentcontent());
                        user4_comment.setText(com_list.get(3).getCommentcontent());
                        user5_comment.setText(com_list.get(4).getCommentcontent());
                        comid1 = com_list.get(0).getCommentid();
                        comid2 = com_list.get(1).getCommentid();
                        comid3 = com_list.get(2).getCommentid();
                        comid4= com_list.get(3).getCommentid();
                        comid5 = com_list.get(4).getCommentid();
                        if(com_list.get(0).getLikenumber()==null){
                            user1_like.setText(String.valueOf(0));
                        }else{
                            user1_like.setText(String.valueOf(com_list.get(0).getLikenumber()));
                        }
                        if(com_list.get(1).getLikenumber()==null){
                            user2_like.setText(String.valueOf(0));
                        }else{
                            user2_like.setText(String.valueOf(com_list.get(1).getLikenumber()));
                        }
                        if(com_list.get(2).getLikenumber()==null){
                            user3_like.setText(String.valueOf(0));
                        }else{
                            user3_like.setText(String.valueOf(com_list.get(2).getLikenumber()));
                        }
                        if(com_list.get(3).getLikenumber()==null){
                            user4_like.setText(String.valueOf(0));
                        }else{
                            user4_like.setText(String.valueOf(com_list.get(3).getLikenumber()));
                        }
                        if(com_list.get(4).getLikenumber()==null){
                            user5_like.setText(String.valueOf(0));
                        }else{
                            user5_like.setText(String.valueOf(com_list.get(4).getLikenumber()));
                        }
                        user_photo1 = com_list.get(0).getCommentatorimg();
                        getComImg1(user_photo1);
                        user_photo2 = com_list.get(1).getCommentatorimg();
                        getComImg2(user_photo2);
                        user_photo3 = com_list.get(2).getCommentatorimg();
                        getComImg3(user_photo3);
                        user_photo4 = com_list.get(3).getCommentatorimg();
                        getComImg4(user_photo4);
                        user_photo5 = com_list.get(4).getCommentatorimg();
                        getComImg5(user_photo5);
                    }else if(com_list.size()==6){
                        findViewById(R.id.relativeLayout1).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout2).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout3).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout4).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout5).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout6).setVisibility(View.VISIBLE);
                        user1_name.setText(com_list.get(0).getCommentatorname());
                        user2_name.setText(com_list.get(1).getCommentatorname());
                        user3_name.setText(com_list.get(2).getCommentatorname());
                        user4_name.setText(com_list.get(3).getCommentatorname());
                        user5_name.setText(com_list.get(4).getCommentatorname());
                        user6_name.setText(com_list.get(5).getCommentatorname());
                        user1_comment.setText(com_list.get(0).getCommentcontent());
                        user2_comment.setText(com_list.get(1).getCommentcontent());
                        user3_comment.setText(com_list.get(2).getCommentcontent());
                        user4_comment.setText(com_list.get(3).getCommentcontent());
                        user5_comment.setText(com_list.get(4).getCommentcontent());
                        user6_comment.setText(com_list.get(5).getCommentcontent());
                        comid1 = com_list.get(0).getCommentid();
                        comid2 = com_list.get(1).getCommentid();
                        comid3 = com_list.get(2).getCommentid();
                        comid4= com_list.get(3).getCommentid();
                        comid5 = com_list.get(4).getCommentid();
                        comid6= com_list.get(5).getCommentid();
                        if(com_list.get(0).getLikenumber()==null){
                            user1_like.setText(String.valueOf(0));
                        }else{
                            user1_like.setText(String.valueOf(com_list.get(0).getLikenumber()));
                        }
                        if(com_list.get(1).getLikenumber()==null){
                            user2_like.setText(String.valueOf(0));
                        }else{
                            user2_like.setText(String.valueOf(com_list.get(1).getLikenumber()));
                        }
                        if(com_list.get(2).getLikenumber()==null){
                            user3_like.setText(String.valueOf(0));
                        }else{
                            user3_like.setText(String.valueOf(com_list.get(2).getLikenumber()));
                        }
                        if(com_list.get(3).getLikenumber()==null){
                            user4_like.setText(String.valueOf(0));
                        }else{
                            user4_like.setText(String.valueOf(com_list.get(3).getLikenumber()));
                        }
                        if(com_list.get(4).getLikenumber()==null){
                            user5_like.setText(String.valueOf(0));
                        }else{
                            user5_like.setText(String.valueOf(com_list.get(4).getLikenumber()));
                        }
                        if(com_list.get(5).getLikenumber()==null){
                            user6_like.setText(String.valueOf(0));
                        }else{
                            user6_like.setText(String.valueOf(com_list.get(5).getLikenumber()));
                        }
                        user_photo1 = com_list.get(0).getCommentatorimg();
                        getComImg1(user_photo1);
                        user_photo2 = com_list.get(1).getCommentatorimg();
                        getComImg2(user_photo2);
                        user_photo3 = com_list.get(2).getCommentatorimg();
                        getComImg3(user_photo3);
                        user_photo4 = com_list.get(3).getCommentatorimg();
                        getComImg4(user_photo4);
                        user_photo5 = com_list.get(4).getCommentatorimg();
                        getComImg5(user_photo5);
                        user_photo6 = com_list.get(5).getCommentatorimg();
                        getComImg6(user_photo6);
                    }else if(com_list.size()> 6){
                        findViewById(R.id.relativeLayout1).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout2).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout3).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout4).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout5).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayout6).setVisibility(View.VISIBLE);
                        user1_name.setText(com_list.get(0).getCommentatorname());
                        user2_name.setText(com_list.get(1).getCommentatorname());
                        user3_name.setText(com_list.get(2).getCommentatorname());
                        user4_name.setText(com_list.get(3).getCommentatorname());
                        user5_name.setText(com_list.get(4).getCommentatorname());
                        user6_name.setText(com_list.get(5).getCommentatorname());
                        user1_comment.setText(com_list.get(0).getCommentcontent());
                        user2_comment.setText(com_list.get(1).getCommentcontent());
                        user3_comment.setText(com_list.get(2).getCommentcontent());
                        user4_comment.setText(com_list.get(3).getCommentcontent());
                        user5_comment.setText(com_list.get(4).getCommentcontent());
                        user6_comment.setText(com_list.get(5).getCommentcontent());
                        if(com_list.get(0).getLikenumber()==null){
                            user1_like.setText(String.valueOf(0));
                        }else{
                            user1_like.setText(String.valueOf(com_list.get(0).getLikenumber()));
                        }
                        if(com_list.get(1).getLikenumber()==null){
                            user2_like.setText(String.valueOf(0));
                        }else{
                            user2_like.setText(String.valueOf(com_list.get(1).getLikenumber()));
                        }
                        if(com_list.get(2).getLikenumber()==null){
                            user3_like.setText(String.valueOf(0));
                        }else{
                            user3_like.setText(String.valueOf(com_list.get(2).getLikenumber()));
                        }
                        if(com_list.get(3).getLikenumber()==null){
                            user4_like.setText(String.valueOf(0));
                        }else{
                            user4_like.setText(String.valueOf(com_list.get(3).getLikenumber()));
                        }
                        if(com_list.get(4).getLikenumber()==null){
                            user5_like.setText(String.valueOf(0));
                        }else{
                            user5_like.setText(String.valueOf(com_list.get(4).getLikenumber()));
                        }
                        if(com_list.get(5).getLikenumber()==null){
                            user6_like.setText(String.valueOf(0));
                        }else{
                            user6_like.setText(String.valueOf(com_list.get(5).getLikenumber()));
                        }
                        user_photo1 = com_list.get(0).getCommentatorimg();
                        getComImg1(user_photo1);
                        user_photo2 = com_list.get(1).getCommentatorimg();
                        getComImg2(user_photo2);
                        user_photo3 = com_list.get(2).getCommentatorimg();
                        getComImg3(user_photo3);
                        user_photo4 = com_list.get(3).getCommentatorimg();
                        getComImg4(user_photo4);
                        user_photo5 = com_list.get(4).getCommentatorimg();
                        getComImg5(user_photo5);
                        user_photo6 = com_list.get(5).getCommentatorimg();
                        getComImg6(user_photo6);
                    }
                }else{
                    Toast.makeText(Topic_activity3.this,"没有话题评论", Toast.LENGTH_LONG).show();
                }
            }

        }
    };

    public void getComImg1(final String userphoto1){
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
                    photoHandler.obtainMessage(1, pp).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void getComImg2(final String userphoto2){
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
                    System.out.println("进入handler");
                    photoHandler.obtainMessage(2, pp).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void getComImg3(final String userphoto3){
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
                    System.out.println("进入handler");
                    photoHandler.obtainMessage(3, pp).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void getComImg4(final String userphoto4){
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
                    System.out.println("进入handler");
                    photoHandler.obtainMessage(4, pp).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void getComImg5(final String userphoto5){
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
                    System.out.println("进入handler");
                    photoHandler.obtainMessage(5, pp).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void getComImg6(final String userphoto6){
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
                    System.out.println("进入handler");
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        mContext = this;
        textView.setText("话题1");
        Log.i(TAG,"这是话题1");
        right_icon.setImageResource(R.drawable.star_co);
        //	comment_img.setImageResource(R.drawable.topic_comment);
         /*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
            /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionID");
        Log.i(TAG,session);
        showTopic();
    }
    public void showTopic(){
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
                        mmHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void showComment(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //回调
                    Topic topi = new Topic();
                    topi.setTopicid(topicID);

                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(topi);

                    RequestBody body = RequestBody.create(JSON, content);
                    Request request = new Request.Builder().addHeader("cookie",session).post(body).url(path1).build();
                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i(TAG,"响应成功");
                    if (response.isSuccessful()) {
                        Log.i(TAG,"响应成功");
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

    @Event(value={R.id.img_comment})
    private void event(View v){
        Log.i(TAG,"点击成功");
        Intent intent = new Intent(mContext,PushCommit.class);
        intent.putExtra("sessionID", session);
        intent.putExtra("topicId",topicID);
        intent.putExtra("Index",1);
        startActivity(intent);
        finish();
    }
    @Event(value={R.id.iv_like1,R.id.iv_like2,R.id.iv_like3,R.id.iv_like4,R.id.iv_like5,R.id.iv_like6})
    private void event2(View v)
    {

        switch(v.getId())
        {
            case R.id.iv_like1:
                idd = 1;
                break;
            case R.id.iv_like2:
                idd = 2;
                break;
            case R.id.iv_like3:
                idd = 3;
                break;
            case R.id.iv_like4:
                idd = 4;
                break;
            case R.id.iv_like5:
                idd = 5;
                break;
            case R.id.iv_like6:
                idd = 6;
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CommentEntityWithBLOBs ce  = new CommentEntityWithBLOBs();
                    switch(idd)
                    {
                        case 1:
                            ce.setCommentid(comid1);
                            break;
                        case 2:
                            ce.setCommentid(comid2);
                            break;
                        case 3:
                            ce.setCommentid(comid3);
                            break;
                        case 4:
                            ce.setCommentid(comid4);
                            break;
                        case 5:
                            ce.setCommentid(comid5);
                            break;
                        case 6:
                            ce.setCommentid(comid6);
                            break;
                    }

                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(ce);

                    RequestBody body = RequestBody.create(JSON, content);

                    Request request = new Request.Builder()
                            .addHeader("cookie", session)
                            .url(path4)
                            .post(body)
                            .build();

                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i(TAG, "响应成功");
                    if (response.isSuccessful()) {
                        Log.i(TAG, "响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        lcHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Event(value={R.id.iv_right})
    private void event1(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //回调
                    Topic top = new Topic();
                    top.setTopicid(topicID);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(top);

                    RequestBody body = RequestBody.create(JSON, content);

                    Request request = new Request.Builder()
                            .addHeader("cookie", session)
                            .url(path2)
                            .post(body)
                            .build();

                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i(TAG, "响应成功");
                    if (response.isSuccessful()) {
                        Log.i(TAG, "响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        cHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void showCollection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //回调
                    Topic top = new Topic();
                    top.setTopicid(topicID);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(top);
                    RequestBody body = RequestBody.create(JSON, content);
                    Log.i("TOPIC","显示收藏");
                    Request request = new Request.Builder()
                            .addHeader("cookie", session)
                            .post(body)
                            .url(path3)
                            .build();

                    OkHttpClient okhttpc = new OkHttpClient();
                    Call call = okhttpc.newCall(request);
                    Response response = call.execute();
                    Log.i(TAG, "响应成功");
                    if (response.isSuccessful()) {
                        Log.i(TAG, "响应成功");
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        //System.out.println(response.body().string());
                        dHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
