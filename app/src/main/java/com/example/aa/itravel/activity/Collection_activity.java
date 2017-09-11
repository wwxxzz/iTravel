package com.example.aa.itravel.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.example.aa.itravel.R;
import com.example.aa.itravel.adapter.CollectionAdapter;
import com.example.aa.itravel.fragment.CollectionFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
@ContentView(R.layout.activity_collection)
public class Collection_activity extends AppCompatActivity {
    @ViewInject(R.id.title_bar_name)
    private TextView title;
    private ViewPager vp;
    private TabLayout tabLayout;
    private CollectionAdapter clAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        vp = (ViewPager) findViewById(R.id.cl_viewpage);
        tabLayout = (TabLayout) findViewById(R.id.cl_tab);
        title.setText("查看收藏");

        clAdapter = new CollectionAdapter(getSupportFragmentManager());
        fragments.add(CollectionFragment.newInstance("0"));
        fragments.add(CollectionFragment.newInstance("1"));
        clAdapter.setFragments(fragments);
        vp.setAdapter(clAdapter);
        //设置tabLayout
        tabLayout.setupWithViewPager(vp);
        //设置文字的颜色
        tabLayout.setTabTextColors(Color.GRAY, Color.BLUE);
        //设置下划线的颜色
        tabLayout.setSelectedTabIndicatorColor(Color.BLUE);
    }

}
