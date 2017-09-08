package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_footprint)
public class Footprint_activity extends Activity {
    private Context mContext;
    @ViewInject(R.id.title_bar_name)
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_footprint);
        mContext = this;
        x.view().inject(this);
        title.setText("足迹");
        
    }
    @Event(value = R.id.lightup)
    private void event(View view){
        //获取当前地点信息
        //通过map_fragment.lightupMap()点亮地图
        Toast.makeText(Footprint_activity.this,"点亮",Toast.LENGTH_SHORT).show();
        //通过FootprintAdapter存储用户足迹信息
        //更新界面
    }
}
