package com.example.aa.itravel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.app.Activity;

/**
 * Created by aa on 2017/9/5.
 */

public class Message_activity extends Activity {
    private Button friendBt;
    private Button homeBt;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message);

        mContext =this;
        friendBt = (Button) findViewById(R.id.bt_friend);
        homeBt = (Button) findViewById(R.id.bt_home);

        friendBt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(mContext,Friend_activity.class);
                startActivity(intent);
            }
        });

        homeBt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(mContext,Home_activity.class);
                startActivity(intent);
            }
        });


    }
}
