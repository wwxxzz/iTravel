package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_push_commit)
public class PushCommit extends Activity {
	private Context mContext;

	@ViewInject(R.id.title_bar_name)
	private TextView titlebar;
	@ViewInject(R.id.iv_right)
	private ImageView right_icon;
	//@ViewInject(R.id.iv_left)
	//private ImageView left_icon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("OUSH","评论");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext =this;
		x.view().inject(this);
		titlebar.setText("发表评论");
		right_icon.setImageResource(R.drawable.tick);
		//left_icon.setImageResource(R.drawable.back);
	}
}
