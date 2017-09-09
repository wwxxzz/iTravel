package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.adapter.FriendTopAdapter;
import com.example.aa.itravel.fragment.FriendTopFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.example.aa.itravel.R.id.bottombar;
import static com.example.aa.itravel.R.id.button_friend;


/**
 * Created by aa on 2017/9/5.
 */

@ContentView(R.layout.friend)
public class Friend_activity extends FragmentActivity {
    private Context mContext;

    @ViewInject(R.id.title_bar_name)
    private TextView textView;

    private ViewPager vp;
    private TabLayout tabLayout;
    private FriendTopAdapter FtAdapter;
    private List<Fragment> fragments = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.friend);
        mContext =this;
        x.view().inject(this);
        textView.setText("好友消息");

        //设置当前页面 首页 字体为红色
        Fragment exFragment = (Fragment)getSupportFragmentManager().findFragmentById(bottombar);
        Button home =(Button) exFragment.getView().findViewById(button_friend);
        home.setTextColor(Color.parseColor("#f75b47"));

        vp = (ViewPager) findViewById(R.id.FriendTop_viewpage);
        tabLayout = (TabLayout) findViewById(R.id.FriendTop_tab);

        FtAdapter = new FriendTopAdapter(getSupportFragmentManager());
        fragments.add(FriendTopFragment.newInstance("0"));
        fragments.add(FriendTopFragment.newInstance("1"));
        fragments.add(FriendTopFragment.newInstance("2"));
        FtAdapter.setFragments(fragments);
        vp.setAdapter(FtAdapter);
        //设置tabLayout
        tabLayout.setupWithViewPager(vp);
        //设置文字的颜色
        tabLayout.setTabTextColors(Color.GRAY, Color.BLUE);
        //设置下划线的颜色
        tabLayout.setSelectedTabIndicatorColor(Color.BLUE);
    }

    @Event(value = {R.id.button_home,R.id.button_message})
    private void event(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_message:
                intent = new Intent(mContext, Message_activity.class);
                startActivity(intent);
                break;
            case R.id.button_home:
                intent = new Intent(mContext, Home_activity.class);
                startActivity(intent);
                break;
        }
    }
}
