package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_single_message)
public class SingleMessageActivity extends AppCompatActivity {
    private Context mContext;
    @ViewInject(R.id.title_bar_name)
    private TextView textView;
    @ViewInject(R.id.tr_like)
    private Button like;
    @ViewInject(R.id.tr_collection)
    private Button collection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        x.view().inject(this);
        textView.setText("详情");
    }
    @Event(value = {R.id.tr_like,R.id.tr_collection,R.id.tr_comment_ic,R.id.tr_transfer})
    private void event(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.tr_like:
                like.setSelected(true);
                break;
            case R.id.tr_collection:
                collection.setSelected(true);
                break;
            case R.id.tr_comment_ic:
                intent = new Intent(mContext, PushCommit.class);
                startActivity(intent);
                break;
            case R.id.tr_transfer:
                intent = new Intent(mContext, PushCommit.class);
                startActivity(intent);
                break;
        }
    }
}
