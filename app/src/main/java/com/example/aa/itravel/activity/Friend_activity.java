package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by aa on 2017/9/5.
 */
@ContentView(R.layout.friend)
public class Friend_activity extends Activity{
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.friend);
        mContext =this;
        x.view().inject(this);
    }
//    @Event(value = {R.id.bt_home,R.id.bt_message})
//    private void event(View view) {
//        Intent intent;
//        switch (view.getId()) {
//            case R.id.bt_message:
//                intent = new Intent(mContext, Message_activity.class);
//                startActivity(intent);
//                break;
//            case R.id.bt_home:
//                intent = new Intent(mContext, Home_activity.class);
//                startActivity(intent);
//                break;
//        }
//    }
}
