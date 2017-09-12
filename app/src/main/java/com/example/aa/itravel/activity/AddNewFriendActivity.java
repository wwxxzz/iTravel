package com.example.aa.itravel.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
@ContentView(R.layout.activity_add_new_friend)
public class AddNewFriendActivity extends AppCompatActivity {
    private Context mContext;
    @ViewInject(R.id.title_bar_name)
    private TextView title;

    @ViewInject(R.id.search_friend)
    private EditText newfriend;
    @ViewInject(R.id.search_result)
    private RelativeLayout result;

    @ViewInject(R.id.newfriend_request)
    private LinearLayout.LayoutParams requests;
    @ViewInject(R.id.frrequest_01)
    private RelativeLayout request1;
    @ViewInject(R.id.frrequest_02)
    private RelativeLayout request2;
    @ViewInject(R.id.frrequest_03)
    private RelativeLayout request3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_new_friend);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.friend);
        mContext =this;
        x.view().inject(this);
        title.setText("添加好友");
    }

    @Event(value = {R.id.search_sure,R.id.frrequest_accept_01,R.id.frrequest_refuse_01,R.id.newfriend_add })
    private void event(View view){
        switch (view.getId()){
            case R.id.search_sure:
                ;//通过editText内容检索
                result.setVisibility(View.VISIBLE);
                //requests.setMargins(0,0,0,0);
                break;
            case R.id.newfriend_add:
                //添加到好友列表
                Toast.makeText(AddNewFriendActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.frrequest_accept_01:
                ;//添加到好友列表
                request1.setVisibility(View.GONE);
                break;
            case R.id.frrequest_refuse_01:
                ;//从好友列表中删除
                request1.setVisibility(View.GONE);
                break;
        }
    }
}
