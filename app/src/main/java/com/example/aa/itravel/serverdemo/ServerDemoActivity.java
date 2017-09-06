package com.example.aa.itravel.serverdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aa.itravel.R;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ServerDemoActivity extends AppCompatActivity {
	String TAG = "MainActivity";
	OkHttpClient client = new OkHttpClient();
	String path = "http://223.3.67.54:8080/iTravel_Server/AndroidLoginController/login";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_demo);

		Button button = (Button) findViewById(R.id.button4);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("TEST", "btn_onclick");
				new Thread(){
					@Override
					public void run()
					{
						//把网络访问的代码放在这里
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
					}
				}.start();

			}
		});
	}
}
