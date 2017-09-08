package com.example.aa.itravel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Ynez on 2017/9/8.
 */

public class CollectionAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;
        private String[] titles = {"动态", "话题"};


        public CollectionAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setFragments(List<Fragment> fragments) {
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments != null ? fragments.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

}
