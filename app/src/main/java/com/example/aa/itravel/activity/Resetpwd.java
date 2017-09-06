package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by aa on 2017/9/4.
 */
@ContentView(R.layout.findpwd)
public class Resetpwd extends AppCompatActivity{
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.findpwd);
        mContext =this;
        x.view().inject(this);
    }
    @Event(value = R.id.bt_over)
    private void event(View view){
        Toast.makeText(Resetpwd.this,"成功修改密码",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext,Home_activity.class);
        startActivity(intent);
    }
}
