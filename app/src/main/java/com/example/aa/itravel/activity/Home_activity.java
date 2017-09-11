package com.example.aa.itravel.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

//import org.xutils.view.annotation.ContentView;
;import static com.example.aa.itravel.R.id.bottombar;
import static com.example.aa.itravel.R.id.button_home;

/**
 * Created by aa on 2017/9/5.
 */
@ContentView(R.layout.home_main)
public class Home_activity extends AppCompatActivity {

    private Context mContext;

    private ViewPager view_pager;
    private LinearLayout ll_dotGroup;
    //private TextView newsTitle;
    //存储图片
    private int imgResIds[] = new int[]{R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4};
    //存储目录
    //private String textview[] = new String[]{"门前大桥下","游过一群鸭","快来快来数一数","二四六七八","233333"};

    //记录当前滚动的位置
    private int curIndex =0;
    PicsAdapter picsAdapter;

    @ViewInject(R.id.title_bar_name)
    private TextView textView;

    String TAG = "HOME_Activity";
    //s用来保存sessionid     发送refresh请求
    String session;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //setContentView(R.layout.home_main);
            mContext =this;
            x.view().inject(this);

            setViewPager();

            textView.setText("首页推荐");


            Bundle bundle = this.getIntent().getExtras();
            /*获取Bundle中的数据，注意类型和key*/
            session = bundle.getString("sessionId");

            //设置当前页面 首页 字体为红色
            Fragment exFragment = (Fragment)getSupportFragmentManager().findFragmentById(bottombar);
            Button home =(Button) exFragment.getView().findViewById(button_home);
            home.setTextColor(Color.parseColor("#f75b47"));


            /*FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tt =fm.beginTransaction();
            tt.replace(R.id.bottombar,new BottomBar());
            tt.commit();*/


            Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
            setSupportActionBar(toolbar);


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            //监听drawer拉出、隐藏
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();



        }
   // @Event(value = {R.id.bt_friend,R.id.bt_message,R.id.prefence,R.id.footprint})


   @Event(value = {R.id.button_friend,R.id.button_message,R.id.bt_entertopic,R.id.bt_info,R.id.bt_footprint,
                        R.id.bt_collection,R.id.bt_preference})
    private void event(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.button_friend:
                intent = new Intent(mContext,Friend_activity.class);
                startActivity(intent);
                break;
            case R.id.button_message:
                intent = new Intent(mContext,Message_activity.class);
                startActivity(intent);
                break;
            case R.id.bt_entertopic:
                switch (curIndex){
                    case 0:
                        intent = new Intent(mContext,Topic_activity.class);
                        intent.putExtra("sessionId", session);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(mContext,Topic_activity2.class);
                        intent.putExtra("sessionId", session);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(mContext,Topic_activity3.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(mContext,Topic_activity4.class);
                        intent.putExtra("sessionId", session);
                        startActivity(intent);
                        break;
                }
            case R.id.bt_info:
                intent = new Intent(mContext,ShowUserInfo.class);
	            intent.putExtra("sessionId", session);
                startActivity(intent);
                break;
            case R.id.bt_footprint:
                intent = new Intent(mContext,Footprint_activity.class);
                startActivity(intent);
                break;
            case R.id.bt_collection:
                intent = new Intent(mContext,Collection_activity.class);
                startActivity(intent);
                break;
            case R.id.bt_preference:
                intent = new Intent(mContext,Preference_activity.class);
	            intent.putExtra("sessionId", session);
                startActivity(intent);
                break;
        }
    }



    private void setViewPager() {

        //newsTitle=(TextView)findViewById(R.id.NewsTitle);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        ll_dotGroup = (LinearLayout) findViewById(R.id.dotgroup);

        PicsAdapter picsAdapter = new PicsAdapter(); // 创建适配器
        picsAdapter.setData(imgResIds);
        view_pager.setAdapter(picsAdapter); // 设置适配器

        view_pager.setOnPageChangeListener(new MyPageChangeListener()); //设置页面切换监听器

        initPoints(imgResIds.length); //初始化图片小圆点
        startAutoScroll(); // 开启自动播放
    }

    // 初始化图片轮播的小圆点和目录
    private void initPoints(int count) {
        for (int i = 0; i < count; i++) {

            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    20, 20);
            params.setMargins(0, 0, 20, 0);
            iv.setLayoutParams(params);

            iv.setImageResource(R.drawable.point_normal);

            ll_dotGroup.addView(iv);

        }
        ((ImageView) ll_dotGroup.getChildAt(curIndex))
                .setImageResource(R.drawable.point_focus);

        //newsTitle.setText(textview[curIndex]);
    }

    // 自动播放
    private void startAutoScroll() {
        ScheduledExecutorService scheduledExecutorService = Executors
                .newSingleThreadScheduledExecutor();
        // 每隔4秒钟切换一张图片
        //scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5,
        //4, TimeUnit.SECONDS);
    }

    // 切换图片任务
    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int count = picsAdapter.getCount();
                    view_pager.setCurrentItem((curIndex + 1) % count);
                }
            });
        }
    }

    // 定义ViewPager控件页面切换监听器
    class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            ImageView imageView1 = (ImageView) ll_dotGroup.getChildAt(position);
            ImageView imageView2 = (ImageView) ll_dotGroup.getChildAt(curIndex);
            if (imageView1 != null) {
                imageView1.setImageResource(R.drawable.point_focus);
            }
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.point_normal);
            }
            curIndex = position;
            //newsTitle.setText(textview[curIndex]);

        }


        boolean b = false;

        @Override
        public void onPageScrollStateChanged(int state) {
            //这段代码可不加，主要功能是实现切换到末尾后返回到第一张
            switch (state) {
                case 1:// 手势滑动
                    b = false;
                    break;
                case 2:// 界面切换中
                    b = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (view_pager.getCurrentItem() == view_pager.getAdapter()
                            .getCount() - 1 && !b) {
                        view_pager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (view_pager.getCurrentItem() == 0 && !b) {
                        view_pager.setCurrentItem(view_pager.getAdapter()
                                .getCount() - 1);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    // 定义ViewPager控件适配器
    class PicsAdapter extends PagerAdapter {

        private List<ImageView> views = new ArrayList<ImageView>();

        @Override
        public int getCount() {
            if (views == null) {
                return 0;
            }
            return views.size();
        }

        public void setData(int[] imgResIds) {
            for (int i = 0; i < imgResIds.length; i++) {
                ImageView iv = new ImageView(Home_activity.this);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                iv.setLayoutParams(params);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                //设置ImageView的属性
                iv.setImageResource(imgResIds[i]);
                views.add(iv);
            }
        }

        public Object getItem(int position) {
            if (position < getCount())
                return views.get(position);
            return null;
        }


        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }


        @Override
        public void destroyItem(View container, int position, Object object) {

            if (position < views.size())
                ((ViewPager) container).removeView(views.get(position));
        }


        @Override
        public int getItemPosition(Object object) {
            return views.indexOf(object);
        }


        @Override
        public Object instantiateItem(View container, int position) {
            if (position < views.size()) {
                final ImageView imageView = views.get(position);
                ((ViewPager) container).addView(imageView);
                return views.get(position);
            }
            return null;
        }

    }


}
