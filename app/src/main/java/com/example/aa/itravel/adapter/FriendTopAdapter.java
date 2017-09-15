package com.example.aa.itravel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.aa.itravel.fragment.FriendTopFragment;

import java.util.List;

/**
 * Created by Ynez on 2017/9/9.
 */

public class FriendTopAdapter extends FragmentPagerAdapter {
    private Fragment currentFragment;
    private List<Fragment> fragments;
    private String[] titles = {"通知", "好友"};

    public FriendTopAdapter(FragmentManager fm) {
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
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentFragment = (FriendTopFragment) object;
        super.setPrimaryItem(container, position, object);
    }

}