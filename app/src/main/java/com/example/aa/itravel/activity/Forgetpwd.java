package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by aa on 2017/9/4.
 */
@ContentView(R.layout.forgetpwd)
public class Forgetpwd extends AppCompatActivity{

    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.forgetpwd);
        x.view().inject(this);
        mContext =this;
    }
    @Event(value = R.id.bt_resetPwd)
    private void resetPwd(View view){
        Intent intent = new Intent(mContext,Resetpwd.class);
        startActivity(intent);
    }

}
