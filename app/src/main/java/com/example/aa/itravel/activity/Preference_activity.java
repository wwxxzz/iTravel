package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.PreferredType;
import com.example.aa.itravel.tools.Result;
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
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ynez on 2017/9/7.
 */
@ContentView(R.layout.activity_preference)
public class Preference_activity extends Activity {
    private Context mContext;
    @ViewInject(R.id.title_bar_name)
    private TextView title;

   // private static List<CheckBox> checkBoxs=new ArrayList<CheckBox>();
    private static List<PreferredType> preferred_types = new ArrayList<PreferredType>();
    private static List<PreferredType> pre_list =new ArrayList<PreferredType>();
    @ViewInject(R.id.cb_food)
    private CheckBox cb1;
    @ViewInject(R.id.cb_living)
    private CheckBox cb2;
    @ViewInject(R.id.cb_shopping)
    private CheckBox cb3;
    @ViewInject(R.id.cb_selfdriving)
    private CheckBox cb4;
    @ViewInject(R.id.cb_town)
    private CheckBox cb5;
    @ViewInject(R.id.cb_mountain)
    private CheckBox cb6;
    @ViewInject(R.id.cb_boating)
    private CheckBox cb7;
    @ViewInject(R.id.cb_savemoney)
    private CheckBox cb8;
    @ViewInject(R.id.cb_photographing)
    private CheckBox cb9;
    @ViewInject(R.id.cb_abord)
    private CheckBox cb10;
    @ViewInject(R.id.cb_domestic)
    private CheckBox cb11;
    @ViewInject(R.id.cb_couple)
    private CheckBox cb12;
    @ViewInject(R.id.cb_others)
    private CheckBox cb13;

    String TAG = "SET_PREFERED_TYPE";
    //s用来保存sessionid     发送refresh请求
    String session;
    Response response;
    OkHttpClient client = new OkHttpClient();
    String path = "http://223.3.88.189:8080/iTravel_Server_SSM/AndroidService/editpreference";
    String path1 = "http://223.3.88.189:8080/iTravel_Server_SSM/AndroidService/showpreference";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Handler mmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;

                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<PreferredType>>(){}.getType();
                pre_list = gson.fromJson(qq,type);
                System.out.println(pre_list.size());
                for(int i=0; i<pre_list.size(); i++){
                    if(pre_list.get(i).getTypeid()== 1){
                        cb1.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 2){
                        cb2.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 3){
                        cb3.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 4){
                        cb4.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 5){
                        cb5.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 6){
                        cb6.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 7){
                        cb7.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 8){
                        cb8.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 9){
                        cb9.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 10){
                        cb10.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 11){
                        cb11.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 12){
                        cb12.setChecked(true);
                    }else if(pre_list.get(i).getTypeid()== 13){
                        cb13.setChecked(true);
                    }
                }
            }

        }
    };
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
                    Toast.makeText(Preference_activity.this,"保存成功", Toast.LENGTH_SHORT).show();
                }

            }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_preference);
        mContext = this;
        x.view().inject(this);
        title.setText("偏好设置");

		/*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
         /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionId");
        Log.i(TAG,session);
        showPreference();
    }

    @Event(value = R.id.bt_savePreference)
    private void event(View view){
        //PreferenceAdapter
        if(cb1.isChecked()){
            PreferredType pt1 = new PreferredType();
            pt1.setTypeid(1);
            preferred_types.add(pt1);
        }
        if(cb2.isChecked()){
            PreferredType pt2 = new PreferredType();
            pt2.setTypeid(2);
            preferred_types.add(pt2);
        }
        if(cb3.isChecked()){
            PreferredType pt3 = new PreferredType();
            pt3.setTypeid(3);
            preferred_types.add(pt3);
        }
        if(cb4.isChecked()){
            PreferredType pt4 = new PreferredType();
            pt4.setTypeid(4);
            preferred_types.add(pt4);
        }
        if(cb5.isChecked()){
            PreferredType pt5 = new PreferredType();
            pt5.setTypeid(5);
            preferred_types.add(pt5);
        }
        if(cb6.isChecked()){
            PreferredType pt6 = new PreferredType();
            pt6.setTypeid(6);
            preferred_types.add(pt6);
        }
        if(cb7.isChecked()){
            PreferredType pt7 = new PreferredType();
            pt7.setTypeid(7);
            preferred_types.add(pt7);
        }
        if(cb8.isChecked()){
            PreferredType pt8 = new PreferredType();
            pt8.setTypeid(8);
            preferred_types.add(pt8);
        }
        if(cb9.isChecked()){
            PreferredType pt9 = new PreferredType();
            pt9.setTypeid(9);
            preferred_types.add(pt9);
        }
        if(cb10.isChecked()){
            PreferredType pt10 = new PreferredType();
            pt10.setTypeid(10);
            preferred_types.add(pt10);
        }
        if(cb11.isChecked()){
            PreferredType pt11 = new PreferredType();
            pt11.setTypeid(11);
            preferred_types.add(pt11);
        }
        if(cb12.isChecked()){
            PreferredType pt12 = new PreferredType();
            pt12.setTypeid(12);
            preferred_types.add(pt12);
        }
        if(cb13.isChecked()){
            PreferredType pt13 = new PreferredType();
            pt13.setTypeid(13);
            preferred_types.add(pt13);
        }

        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(preferred_types);

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

    public void showPreference(){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    Request request = new Request.Builder()
                            .addHeader("cookie",session)
                            .url(path1)
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

}
