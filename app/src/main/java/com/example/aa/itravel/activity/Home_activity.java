package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static com.example.aa.itravel.R.id.bottombar;
import static com.example.aa.itravel.R.id.button_home;

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


            //设置当前页面 首页 字体为红色
            Fragment exFragment = (Fragment)getSupportFragmentManager().findFragmentById(bottombar);
            Button home =(Button) exFragment.getView().findViewById(button_home);
            home.setTextColor(Color.parseColor("#f75b47"));


            /*FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tt =fm.beginTransaction();
            tt.replace(R.id.bottombar,new BottomBar());
            tt.commit();*/

        }
   // @Event(value = {R.id.bt_friend,R.id.bt_message,R.id.prefence,R.id.footprint})


   @Event(value = {R.id.button_friend,R.id.button_message})
    private void event(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.button_friend:
                intent = new Intent(mContext,Friend_activity.class);
                startActivity(intent);
                break;
            case R.id.button_message:
                intent = new Intent(mContext,Message_activity.class);
                startActivity(intent);
                break;
        }
    }
}
