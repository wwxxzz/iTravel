package com.example.aa.itravel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aa on 2017/9/4.
 */

public class Resetpwd extends AppCompatActivity{

    private Button bt_over;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpwd);
        mContext =this;

        bt_over = (Button)findViewById(R.id.bt_over);
        bt_over.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                Intent intent = new Intent(mContext,Home_activity.class);
                startActivity(intent);
            }

        });

    }
}
