package com.example.aa.itravel.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by Ynez on 2017/9/7.
 */
@ContentView(R.layout.map_fragment)
public class map_fragment extends Activity{
    private Context mContext;
    /*
    各种国家图片小构件,未点亮/点亮
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        x.view().inject(this);
    }

    private void lightupMap() {//参数为国家编号
        //点亮地图
    }
}
