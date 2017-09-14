package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.MessageEntityWithBLOBs;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.Result;
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

@ContentView(R.layout.activity_send_message)
public class SendMessageActivity extends AppCompatActivity {
    private Context mContext;

    @ViewInject(R.id.title_bar_name)
    private TextView titlebar;
    @ViewInject(R.id.msg_addLocation)
    private TextView addlocation;
    @ViewInject(R.id.iv_right)
    private ImageView right_icon;
    @ViewInject(R.id.msg_editText)
    private EditText new_msg;
    @ViewInject(R.id.msg_addImage)
    private ImageView new_image;
    @ViewInject(R.id.radioButton1)
    private CheckBox type1;
    @ViewInject(R.id.radioButton2)
    private CheckBox type2;
    @ViewInject(R.id.radioButton3)
    private CheckBox type3;
    @ViewInject(R.id.radioButton4)
    private CheckBox type4;
    @ViewInject(R.id.radioButton5)
    private CheckBox type5;
    @ViewInject(R.id.radioButton6)
    private CheckBox type6;
    @ViewInject(R.id.radioButton7)
    private CheckBox type7;
    @ViewInject(R.id.radioButton8)
    private CheckBox type8;
    @ViewInject(R.id.radioButton9)
    private CheckBox type9;
    @ViewInject(R.id.radioButton10)
    private CheckBox type10;
    @ViewInject(R.id.radioButton11)
    private CheckBox type11;
    @ViewInject(R.id.radioButton12)
    private CheckBox type12;
    @ViewInject(R.id.radioButton13)
    private CheckBox type13;

    String session;
    Integer type = 0;
    String path = Network.URL+"newmessage";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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
                    Toast.makeText(SendMessageActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                     Intent intent= new Intent(mContext, Message_activity.class);
                    intent.putExtra("sessionID", session);
                    startActivity(intent);
                     finish();
                }else if(back.equals("existed")){
                    Toast.makeText(SendMessageActivity.this,"失效", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext =this;
        x.view().inject(this);
         /*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
            /*获取Bundle中的数据，注意类型和key*/
        session = bundle.getString("sessionID");
        //setContentView(R.layout.activity_send_message);
        titlebar.setText("发表动态");

        right_icon.setImageDrawable(getResources().getDrawable(R.drawable.tick));;
        addlocation.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
    }

   @Event(value={R.id.radioButton1,R.id.radioButton2,R.id.radioButton3,R.id.radioButton4,R.id.radioButton5,
           R.id.radioButton6,R.id.radioButton7,R.id.radioButton8,R.id.radioButton9,R.id.radioButton10,
           R.id.radioButton11,R.id.radioButton12,R.id.radioButton13})
   private void e1(View v){
       switch (v.getId()){
           case R.id.radioButton1:
               type = 1;
               break;
           case R.id.radioButton2:
               type = 2;
               break;
           case R.id.radioButton3:
               type = 3;
               break;
           case R.id.radioButton4:
               type = 4;
               break;
           case R.id.radioButton5:
               type = 5;
               break;
           case R.id.radioButton6:
               type = 6;
               break;
           case R.id.radioButton7:
               type = 7;
               break;
           case R.id.radioButton8:
               type = 8;
               break;
           case R.id.radioButton9:
               type = 9;
               break;
           case R.id.radioButton10:
               type = 10;
               break;
           case R.id.radioButton11:
               type = 11;
               break;
           case R.id.radioButton12:
               type = 12;
               break;
           case R.id.radioButton13:
               type = 13;
               break;
       }
       //Toast.makeText(SendMessageActivity.this,String.valueOf(type), Toast.LENGTH_LONG).show();
   }
    @Event(value = {R.id.msg_addLocation,R.id.msg_addImage})
    private void event(View view){
        switch (view.getId()){
            case R.id.msg_addLocation:
                addlocation.setText("中国江苏南京");
                break;
            case R.id.msg_addImage:
               // new_image.setImageDrawable(getResources().getDrawable(R.drawable.img1));
                break;
        }
    }




    @Event(value={R.id.iv_right})
    private void event1(View v){
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    //回调
                    MessageEntityWithBLOBs mess = new MessageEntityWithBLOBs();
                    mess.setMessagecontent(new_msg.getText().toString());
                    mess.setMessagetype(type);
                    Gson gson = new GsonBuilder().create();
                    String content = gson.toJson(mess);

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
