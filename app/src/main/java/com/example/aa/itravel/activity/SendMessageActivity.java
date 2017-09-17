package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.aa.itravel.tools.UploadUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@ContentView(R.layout.activity_send_message)
public class SendMessageActivity extends AppCompatActivity {
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
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
    File f;
    String filename;
    String session;
    Integer type = 0;
    String path = Network.URL+"newmessage";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
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
        new_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });

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
                    mess.setMessageimage(filename);
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
//    @Event(value={R.id.msg_addImage})
//    private void addImg(View v){
//        selectPicture();
//    }

    /**
     * 从相册选择照片（不裁切）
     */
    private void selectPicture() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);//Pick an item from the data
        intent.setType("image/*");//从所有图片中进行选择
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {//从相册选择照片不裁切
            try {
                Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                System.out.println(selectedImage.toString());
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);  //获取照片路径
                cursor.close();
                System.out.println(picturePath);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                new_image.setImageBitmap(bitmap);
                f = new File(picturePath);
                //从左到右参数依次是文件、图片类型、本Activity的类名.this 以及处理请求的handler
                System.out.println(f.getName());
                filename = f.getName();
                new Thread(new UploadUtil(f,"jpg",SendMessageActivity.this,uplHandler)).start();
                //从左到右依次是 文件名 以及处理请求的handler
                //new Thread(new GetImage(f.getName(),imgHandler)).start();
            } catch (Exception e) {
                // TODO Auto-generatedcatch block
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private Handler uplHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String qq = (String) msg.obj;
                Log.i("SEND", qq);
                System.out.println("1234"+qq);
            }
        }
    };
}
