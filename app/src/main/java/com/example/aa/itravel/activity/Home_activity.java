package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

;

/**
 * Created by aa on 2017/9/5.
 */
@ContentView(R.layout.home)
public class Home_activity extends FragmentActivity {
        //private Button friendBt;
        //private Button messageBt;
        private Context mContext;

    @ViewInject(R.id.title_bar_name)
    private TextView textView;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //setContentView(R.layout.home);
            mContext =this;
            x.view().inject(this);

            textView.setText("首页推荐");

            /*FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tt =fm.beginTransaction();
            tt.replace(R.id.fragment_container,new Banner());
            tt.commit();*/

        }
    @Event(value = {R.id.bt_friend,R.id.bt_message})
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
        }
    }
}
