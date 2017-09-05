package com.example.aa.itravel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aa on 2017/9/5.
 */

public class Home_activity extends Activity{

        private Button friendBt;
        private Button messageBt;
        private Context mContext;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.home);

            mContext =this;
            friendBt = (Button) findViewById(R.id.bt_friend);
            messageBt = (Button) findViewById(R.id.bt_message);

            friendBt.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    Intent intent = new Intent(mContext,Friend_activity.class);
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
