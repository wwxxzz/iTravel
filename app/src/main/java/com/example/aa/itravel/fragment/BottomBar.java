package com.example.aa.itravel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aa.itravel.R;

import org.xutils.view.annotation.ContentView;



@ContentView(R.layout.fragment_bottom_bar)
public class BottomBar extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.w("TEST","onCreate");

        //setViewPager();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        //return super.onCreateView(inflater,container,savedINstanceState);
        //return x.view().inject(this,inflater,container);

        View view = inflater.inflate(R.layout.fragment_bottom_bar, null);  // View android.view.LayoutInflater.inflate(int resource, ViewGroup root)
        return view;

    }

    public void onPause(){
        super.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    /*@Event(value = {R.id.button_friend,R.id.button_message})
    private void event(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.button_friend:
                intent = new Intent(getActivity(),Friend_activity.class);
                startActivity(intent);
                break;
            case R.id.button_message:
                intent = new Intent(getActivity(),Message_activity.class);
                startActivity(intent);
                break;
        }
    }*/
}
