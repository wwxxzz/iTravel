package com.example.aa.itravel.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Ynez on 2017/9/7.
 */
@ContentView(R.layout.activity_preference)
public class Preference_activity extends Activity {
    private Context mContext;
    @ViewInject(R.id.title_bar_name)
    private TextView title;

    @ViewInject(R.id.cb_food)
    private CheckBox food;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_preference);
        mContext =this;
        x.view().inject(this);
        title.setText("偏好设置");
        getUserPreferenc();
    }

    @Event(value = R.id.bt_savePreference)
    private void event(View view){
        //PreferenceAdapter
        //存储偏好设置
        Toast.makeText(Preference_activity.this,"偏好设置成功",Toast.LENGTH_SHORT).show();
    }

    public void getUserPreferenc() {
            //Adapter设置默认选中状态
    }
}
