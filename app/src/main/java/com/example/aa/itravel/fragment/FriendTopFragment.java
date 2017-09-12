package com.example.aa.itravel.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.aa.itravel.R;
import com.example.aa.itravel.activity.AddNewFriendActivity;
import com.example.aa.itravel.activity.Message_activity;

@SuppressLint("ValidFragment")
public class FriendTopFragment extends Fragment {
	private String name;
	private FriendData friendData=new FriendData();
	private NewFriend newFriend=new NewFriend();

	public class FriendData implements OnClickListener{

		@Override
		public void onClick(View view) {
			startActivity(new Intent(getActivity(), Message_activity.class));
		}
	}
	public class NewFriend implements OnClickListener{

		@Override
		public void onClick(View view) {
			startActivity(new Intent(getActivity(), AddNewFriendActivity.class));
		}
	}

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
		View view = inflater.inflate(R.layout.notice_fragment, null);
		switch (name) {
			case "1":
				view = inflater.inflate(R.layout.friendlist_fragment, null);
				view.findViewById(R.id.fr_next_01).setOnClickListener(friendData);
				view.findViewById(R.id.new_friend).setOnClickListener(newFriend);
				break;
			case "2":
				view = inflater.inflate(R.layout.chat_fragment, null);
				break;
			default:
				view = inflater.inflate(R.layout.notice_fragment, null);
				break;
		}
		return view;
	}

	public static com.example.aa.itravel.fragment.FriendTopFragment newInstance(String name) {
		Bundle args = new Bundle();
		args.putString("name", name);
		com.example.aa.itravel.fragment.FriendTopFragment fragment = new com.example.aa.itravel.fragment.FriendTopFragment();
		fragment.setArguments(args);
		return fragment;
	}

}