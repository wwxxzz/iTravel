package com.example.aa.itravel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aa on 2017/9/3.
 */

public class Register_activity extends AppCompatActivity {

    private Button registerButton;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mContext =this;
        registerButton = (Button) findViewById(R.id.bt_register);

        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(mContext,Home_activity.class);
                startActivity(intent);
            }
        });


    }
}
