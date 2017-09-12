package com.example.aa.itravel.activity;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_send_message)
public class SendMessageActivity extends AppCompatActivity {
    private Context mContext;

    @ViewInject(R.id.title_bar_name)
    private TextView titlebar;
    @ViewInject(R.id.msg_addLocation)
    private TextView addlocation;
    @ViewInject(R.id.iv_right)
    private ImageView right_icon;
    @ViewInject(R.id.msg_editText)
    private EditText new_msg;
    @ViewInject(R.id.msg_addImage)
    private ImageView new_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext =this;
        x.view().inject(this);
        //setContentView(R.layout.activity_send_message);
        titlebar.setText("发表动态");
        right_icon.setImageResource(R.drawable.tick);
        addlocation.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
    }


    @Event(value = {R.id.msg_addLocation,R.id.msg_addImage})
    private void event(View view){
        switch (view.getId()){
            case R.id.msg_addLocation:
                addlocation.setText("中国江苏南京");
                break;
            case R.id.msg_addImage:
                new_image.setImageDrawable(getResources().getDrawable(R.drawable.img1));
                break;
        }

    }
}
