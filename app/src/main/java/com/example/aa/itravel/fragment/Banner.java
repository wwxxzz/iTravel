package com.example.aa.itravel.fragment;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by aa on 2017/9/7.
 */


@ContentView(R.layout.banner_fragment)
public class Banner extends Fragment {


    //@ViewInject(R.id.view_pager)
    private ViewPager view_pager;

    //@ViewInject(R.id.dotgroup)
    private LinearLayout ll_dotGroup;

    //private TextView newsTitle;
    //存储图片
    private int imgResIds[] = new int[]{R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4};
    //存储目录
   // private String textview[] = new String[]{"门前大桥下","游过一群鸭","快来快来数一数","二四六七八"};
    //记录当前滚动的位置
    private int curIndex =0;
    PicsAdapter picsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Log.w("TEST","onCreate");

        setViewPager();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        //return super.onCreateView(inflater,container,savedINstanceState);
        //return x.view().inject(this,inflater,container);

        View view = inflater.inflate(R.layout.banner_fragment, null);// View android.view.LayoutInflater.inflate(int resource, ViewGroup root)
        return view;

    }

    /*public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager);
        ll_dotGroup = (LinearLayout) view.findViewById(R.id.dotgroup);
    }*/

    public void onPause(){
        super.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }


    private void setViewPager() {

        //newsTitle=(TextView)findViewById(R.id.NewsTitle);

        view_pager = (ViewPager) getActivity().findViewById(R.id.view_pager);
        ll_dotGroup = (LinearLayout) getActivity().findViewById(R.id.dotgroup);


        PicsAdapter picsAdapter = new PicsAdapter(); // 创建适配器
        picsAdapter.setData(imgResIds);
        view_pager.setAdapter(picsAdapter); // 设置适配器

        view_pager.setOnPageChangeListener(new MyPageChangeListener()); //设置页面切换监听器

        initPoints(imgResIds.length); // 初始化图片小圆点
        startAutoScroll(); // 开启自动播放
    }


    // 初始化图片轮播的小圆点和目录
    private void initPoints(int count) {
        for (int i = 0; i < count; i++) {

            //ImageView iv = new ImageView(this);

            ImageView iv = new ImageView(getActivity());


            //
            //View view = inflater.inflater(R.layout.XXXX,container,false);
            //layout = view.findViewById(R.id.layout);

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
        //scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5, 4, TimeUnit.SECONDS);
    }

    // 切换图片任务
    /*private class ViewPagerTask implements Runnable {
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
    }*/


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
                //ImageView iv = new ImageView(Banner.this);
                ImageView iv = new ImageView(getActivity());

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



