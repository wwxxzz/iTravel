package com.example.aa.itravel.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;

/**
 * Created by aa on 2017/9/7.
 */

@ContentView(R.layout.layout_menu)
public class DrawerFragment extends Fragment implements OnClickListener{

    private View myInfo;
    private View myCollect;
    private View myFootprint;
    private View myPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menu, null);
        findViews(view);

        return view;
    }


    public void findViews(View view) {
        myInfo = view.findViewById(R.id.tv_myinfo);
        myCollect = view.findViewById(R.id.tv_mycollect);
        myFootprint = view.findViewById(R.id.tv_myfootprint);
        myPreference = view.findViewById(R.id.tv_mypreference);

        myInfo.setOnClickListener(this);
        myCollect.setOnClickListener(this);
        myFootprint.setOnClickListener(this);
        myPreference.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Fragment newContent = null;
        String title = null;
        switch (v.getId()) {
            case R.id.tv_myinfo: // 个人资料
                //newContent = new TodayFragment();
                //title = getString(R.string.today);
                break;
            case R.id.tv_mycollect:// 我的收藏
                //newContent = new LastListFragment();
                //title = getString(R.string.lastList);
                break;
            case R.id.tv_myfootprint: // 我的足迹
                //newContent = new DiscussFragment();
                //title = getString(R.string.discussMeetting);
                break;
            case R.id.tv_mypreference: // 偏好设置
                //newContent = new MyFavoritesFragment();
                //title = getString(R.string.myFavorities);
                break;
            default:
                break;
        }
        if (newContent != null) {
            //switchFragment(newContent, title);
        }
    }


    /**
     * 切换fragment
     * @param fragment
     */
    /*private void switchFragment(Fragment fragment, String title) {
        if (getActivity() == null) {
            return;
        }
        if (getActivity() instanceof MainActivity) {
            MainActivity fca = (MainActivity) getActivity();
            fca.switchConent(fragment, title);
        }
    }*/
}
