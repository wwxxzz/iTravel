package com.example.aa.itravel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aa.itravel.R;

/**
 * Created by Ynez on 2017/9/8.
 */

public class CollectionFragment extends Fragment {
    private String name;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.get("name").toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.collection_fragment, null);
        switch (name){
            case "1":
                view= inflater.inflate(R.layout.topic_collection_fragment, null);
                break;
            default:
                view= inflater.inflate(R.layout.collection_fragment, null);
                break;
        }
        return view;
    }

    public static CollectionFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
