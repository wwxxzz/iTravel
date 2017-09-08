package com.example.aa.itravel.adapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aa.itravel.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.*;

import okhttp3.MediaType;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.aa.itravel.tools.User;
import com.google.gson.*;

public class ServerTestActivity extends AppCompatActivity {
	String TAG = "MainActivity";
	OkHttpClient client = new OkHttpClient();
	String path = "http://223.3.82.239:8080/iTravel_Server/AndroidLoginController/login";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("TEST","Oncreate");
		setContentView(R.layout.activity_server_test);

		Button button = (Button) findViewById(R.id.btn_test);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)  {
				Log.i("TEST", "btn_onclick");
				new Thread(){
					@Override
					public void run()
					{
						//把网络访问的代码放在这里
						try {

							User user = new User();
							user.setUsername("root");
							user.setUserpassword("1234");
							Gson gson = new GsonBuilder().create();
							String content =gson.toJson(user);


							RequestBody body = RequestBody.create(JSON,content);

							Request request = new Request.Builder()
									.url(path)
									.post(body)
									.build();
							OkHttpClient okhttpc = new OkHttpClient();
							Call call = okhttpc.newCall(request);
							Response response = call.execute();
							System.out.println(response.body().string());


						} catch (IOException e) {
							e.printStackTrace();
						}
/*
						RequestBody body = new FormBody.Builder()
								.add("username","root")
								.add("password","1234")
								.build();

						Request request = new Request.Builder()
								.url(path)
								.post(body)
								.build();
						OkHttpClient okhttpc = new OkHttpClient();
						Call call = okhttpc.newCall(request);
						try {
							Response response = call.execute();
							System.out.println(response.body().string());
						} catch (IOException e) {
							e.printStackTrace();
						}

*/
					}
				}.start();

			}
		});
	}
}
