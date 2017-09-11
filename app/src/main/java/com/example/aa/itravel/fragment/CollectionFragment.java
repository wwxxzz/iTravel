package com.example.aa.itravel.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aa.itravel.R;
import com.example.aa.itravel.activity.Message_activity;

/**
 * Created by Ynez on 2017/9/8.
 */
@SuppressLint("ValidFragment")
public class CollectionFragment extends Fragment implements View.OnClickListener {
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
                view.findViewById(R.id.cl_top_01).setOnClickListener(this);
                break;
            default:
                view= inflater.inflate(R.layout.collection_fragment, null);
                view.findViewById(R.id.cl_msg_01).setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), Message_activity.class));
    }
}
