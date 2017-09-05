package com.example.aa.itravel.xutils3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.xutils.x;

/**
 * Created by aa on 2017/9/5.
 */

public class XUtils3MainActivity extends Activity{


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_xutils3_main);
        x.view().inject(this);

    }
}
