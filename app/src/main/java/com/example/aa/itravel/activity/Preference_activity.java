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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ynez on 2017/9/7.
 */
@ContentView(R.layout.activity_preference)
public class Preference_activity extends Activity {
    private Context mContext;
    @ViewInject(R.id.title_bar_name)
    private TextView title;

    private static List<CheckBox> checkBoxs=new ArrayList<CheckBox>();

    @ViewInject(R.id.cb_food)
    private CheckBox cb1;
    @ViewInject(R.id.cb_living)
    private CheckBox cb2;
    @ViewInject(R.id.cb_shopping)
    private CheckBox cb3;
    @ViewInject(R.id.cb_selfdriving)
    private CheckBox cb4;
    @ViewInject(R.id.cb_town)
    private CheckBox cb5;
    @ViewInject(R.id.cb_mountain)
    private CheckBox cb6;
    @ViewInject(R.id.cb_boating)
    private CheckBox cb7;
    @ViewInject(R.id.cb_savemoney)
    private CheckBox cb8;
    @ViewInject(R.id.cb_photographing)
    private CheckBox cb9;
    @ViewInject(R.id.cb_abord)
    private CheckBox cb10;
    @ViewInject(R.id.cb_domestic)
    private CheckBox cb11;
    @ViewInject(R.id.cb_couple)
    private CheckBox cb12;
    @ViewInject(R.id.cb_others)
    private CheckBox cb13;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_preference);
        mContext = this;
        x.view().inject(this);
        title.setText("偏好设置");

        checkBoxs.add(cb1);
        checkBoxs.add(cb2);
        checkBoxs.add(cb3);
        checkBoxs.add(cb4);
        checkBoxs.add(cb5);
        checkBoxs.add(cb6);
        checkBoxs.add(cb7);
        checkBoxs.add(cb8);
        checkBoxs.add(cb9);
        checkBoxs.add(cb10);
        checkBoxs.add(cb11);
        checkBoxs.add(cb12);
        checkBoxs.add(cb12);

        getUserPreferenc(checkBoxs);
    }

    @Event(value = R.id.bt_savePreference)
    private void event(View view){
        //PreferenceAdapter
        //存储偏好设置
        Toast.makeText(Preference_activity.this,"偏好设置成功",Toast.LENGTH_SHORT).show();
    }

    public void getUserPreferenc(List<CheckBox> cbx) {
            //Adapter设置默认选中状态
    }
}
