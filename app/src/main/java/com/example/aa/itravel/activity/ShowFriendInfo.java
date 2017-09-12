package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by aa on 2017/9/12.
 */

@ContentView(R.layout.friend_info)
public class ShowFriendInfo extends Activity{

    private Context mContext;


    @ViewInject(R.id.showname)
    private TextView friend_name;
    @ViewInject(R.id.showlocation)
    private TextView friend_location;
    @ViewInject(R.id.showoccupation)
    private TextView friend_career;
    @ViewInject(R.id.showemail)
    private TextView friend_email;
    @ViewInject(R.id.showphone)
    private TextView friend_phone;
    @ViewInject(R.id.showbirthday)
    private TextView friend_birth;
    @ViewInject(R.id.showsex)
    private TextView friend_sex;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.title_bar_name)
    private  TextView titlebar;
    @ViewInject(R.id.iv_right)
    private ImageView right_icon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.forgetpwd);
        x.view().inject(this);
        mContext =this;
        titlebar.setText("好友资料");
        right_icon.setImageResource(R.drawable.ic_chat);

    }

    @Event(value = R.id.iv_right)
    private void event(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_right:
                intent = new Intent(mContext, Message_activity.class);
                startActivity(intent);
                break;
        }
    }

}
