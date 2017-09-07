package com.example.aa.itravel.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by Ynez on 2017/9/7.
 */
@ContentView(R.layout.activity_trend)
public class Trend_activity extends AppCompatActivity {
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.findpwd);
        mContext =this;
        x.view().inject(this);
    }

}
