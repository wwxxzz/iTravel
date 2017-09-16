package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.Network;
import com.example.aa.itravel.tools.Result;
import com.example.aa.itravel.tools.User;
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

/**
 * Created by admin on 2017/9/8.
 */
@ContentView(R.layout.changeinfo)
public class ChangeUserInfo extends Activity {

	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	protected static Uri tempUri;
	private ImageView iv_personal_icon;

	@ViewInject(R.id.btn_change)
	private Button change;
	@ViewInject(R.id.im_photo)
	private ImageView image;

	String TAG = "CHANGE_INFO_Activity";
	String session;
	OkHttpClient client = new OkHttpClient();
	String path1 = Network.URL+ "personalinfo";
	String path = Network.URL+"editpersonalinfo";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	@ViewInject(R.id.changename)
	private EditText user_name;
	@ViewInject(R.id.changelocation)
	private EditText user_location;
	@ViewInject(R.id.changeoccupation)
	private EditText user_career;
	@ViewInject(R.id.changeemail)
	private EditText user_email;
	@ViewInject(R.id.changephone)
	private EditText user_phone;
	@ViewInject(R.id.changebirthday)
	private EditText user_birth;
	@ViewInject(R.id.changesex)
	private EditText user_sex;
	@ViewInject(R.id.toolbar)
	private Toolbar toolbar;
	@ViewInject(R.id.title_bar_name)
	private  TextView titlebar;
	@ViewInject(R.id.iv_right)
	private ImageView right_icon;

	private Context mContext;
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
					Log.i(TAG,"修改成功");
					//Log.i(TAG,"sessionId"+s);
					Toast.makeText(ChangeUserInfo.this,"修改成功，即将跳转", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent = new Intent(mContext, ShowUserInfo.class);
					intent.putExtra("sessionID", session);
					startActivity(intent);
					finish();
				}else {
					//Toast.makeText(Login_activity.this,"用户名或密码错误", Toast.LENGTH_LONG).show();
				}

			}

		}
	};
	private Handler mmHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==1){
				Log.i(TAG,"进入");
				//		Toast.makeText(ShowUserInfo.this,"成功", Toast.LENGTH_SHORT);
				String qq = (String) msg.obj;
				Log.i(TAG, qq);
				Gson gson = new Gson();
				User re = gson.fromJson(qq, User.class);
				user_name.setText(re.getUsername());
				user_location.setText(re.getUserlocation());
				user_career.setText(re.getUsercareer());
				user_email.setText(re.getUseremail());
				user_phone.setText(re.getUsertel());
				user_birth.setText(re.getUserbirth());
				user_sex.setText(re.getUsersex());
			}

		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.login);
		x.view().inject(this);
        /*获取Intent中的Bundle对象*/
		Bundle bundle = this.getIntent().getExtras();
		mContext = this;
		titlebar.setText("修改资料");
		right_icon.setImageResource(R.drawable.save);

		session = bundle.getString("sessionID");
		Log.i("CHANGE",session);
		new Thread(runnable).start();  //启动子线程

		/*Button btn_change = (Button) findViewById(R.id.btn_change);
		iv_personal_icon = (ImageView) findViewById(R.id.im_photo);
		btn_change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showChoosePicDialog();
			}
		});*/


		change.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectPicture();
			}
		});



	}

	/**
	 * 从相册选择照片（不裁切）
	 */
	private void selectPicture() {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.setAction(Intent.ACTION_PICK);//Pick an item from the data
		intent.setType("image/*");//从所有图片中进行选择
		startActivityForResult(intent, 1);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode==RESULT_OK) {//从相册选择照片不裁切
			try {
				Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor =getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);  //获取照片路径
				cursor.close();
				Bitmap bitmap= BitmapFactory.decodeFile(picturePath);
				image.setImageBitmap(bitmap);
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




	@Event(value={R.id.iv_right})
	private void event(View v){
		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			String username = user_name.getText().toString();
			String userlocation = user_location.getText().toString();
			String usercareer = user_career.getText().toString();
			String useremail = user_email.getText().toString();
			String userphone = user_phone.getText().toString();
			String userbirth = user_birth.getText().toString();
			String usersex = user_sex.getText().toString();

			@Override
			public void run() {
				Response response = null;
				try {
					//回调
					User user = new User();
					user.setUsertel(userphone);
					user.setUsername(username);
					user.setUserlocation(userlocation);
					user.setUsercareer(usercareer);
					user.setUseremail(useremail);
					user.setUsersex(usersex);
					user.setUserbirth(userbirth);

					Gson gson = new GsonBuilder().create();
					String content = gson.toJson(user);

					RequestBody body = RequestBody.create(JSON, content);

					Request request = new Request.Builder().addHeader("cookie",session).post(body).url(path).build();
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

	//新线程进行网络请求
	Runnable runnable = new Runnable(){
		@Override
		public void run() {
			try {
				Request request = new Request.Builder().addHeader("cookie",session).url(path1).build();
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

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};



	/**
	 * 显示修改头像的对话框
	 */
	/*protected void showChoosePicDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("设置头像");
		String[] items = { "选择本地照片", "拍照" };
		builder.setNegativeButton("取消", null);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case CHOOSE_PICTURE: // 选择本地照片
						Intent openAlbumIntent = new Intent(
								Intent.ACTION_GET_CONTENT);
						openAlbumIntent.setType("image/*");
						startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
						break;
					case TAKE_PICTURE: // 拍照
						Intent openCameraIntent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						tempUri = Uri.fromFile(new File(Environment
								.getExternalStorageDirectory(), "image.jpg"));
						// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
						openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
						startActivityForResult(openCameraIntent, TAKE_PICTURE);
						break;
				}
			}
		});
		builder.create().show();
	}*/


	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // 如果返回码是可以用的
			switch (requestCode) {
				case TAKE_PICTURE:
					startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
					break;
				case CHOOSE_PICTURE:
					startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
					break;
				case CROP_SMALL_PICTURE:
					if (data != null) {
						setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
					}
					break;
			}
		}
	}
*/
	/**
	 * 裁剪图片方法实现
	 *
	 * @param uri
	 */
	/*protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		//intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);

	}*/

	/**
	 * 保存裁剪之后的图片数据
	 *
	 * @param
	 *
	 * @param //picdata
	 */
	/*protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
			iv_personal_icon.setImageBitmap(photo);
			//uploadPic(photo);
		}
	}

	private void uploadPic(Bitmap bitmap) {
		// 上传至服务器
		// ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
		// 注意这里得到的图片已经是圆形图片了
		// bitmap是没有做个圆形处理的，但已经被裁剪了

		String imagePath = Utils.savePhoto(bitmap, Environment
				.getExternalStorageDirectory().getAbsolutePath(), String
				.valueOf(System.currentTimeMillis()));
		Log.e("imagePath", imagePath+"");
		if(imagePath != null){
			// 拿着imagePath上传了
			// ...
		}
	}*/

}
