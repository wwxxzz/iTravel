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
@ContentView(R.layout.home)
public class Home_activity extends Activity{
        //private Button friendBt;
        //private Button messageBt;
        private Context mContext;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //setContentView(R.layout.home);
            mContext =this;
            x.view().inject(this);
        }
    @Event(value = {R.id.bt_friend,R.id.bt_message,R.id.prefence})
    private void event(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.bt_friend:
                 intent = new Intent(mContext,Friend_activity.class);
                startActivity(intent);
                break;
            case R.id.bt_message:
                intent = new Intent(mContext,Message_activity.class);
                startActivity(intent);
                break;
            case R.id.prefence:
                intent = new Intent(mContext,Preference_activity.class);
                startActivity(intent);
                break;
        }
    }
}
