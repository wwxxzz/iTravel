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

public class Friend_activity extends Activity{
    private Button homeBt;
    private Button messageBt;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend);

        mContext =this;
        homeBt = (Button) findViewById(R.id.bt_home);
        messageBt = (Button) findViewById(R.id.bt_message);

        homeBt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(mContext,Home_activity.class);
                startActivity(intent);
            }
        });

        messageBt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(mContext,Message_activity.class);
                startActivity(intent);
            }
        });


    }
}
