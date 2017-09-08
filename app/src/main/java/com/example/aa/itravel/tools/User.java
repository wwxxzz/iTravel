package com.example.aa.itravel.tools;

import android.util.Log;
import android.widget.Toast;

import com.example.aa.itravel.activity.Login_activity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 2017/9/7.
 */

public class User {
	public String username;
	public String usertel;
	public String userpassword;
	public void User(){};
	public void setUsername(String name){
		this.username = name;
	}
	public void setPassword(String psd){
		this.userpassword = psd;
	}
	public void setUsetel(String tel){
		this.usertel = tel;
	}
	public String getUsername(){
		return this.username;
	}
	public String getPassword(){
		return  this.userpassword;
	}
	public String getUsertel(){
		return  this.usertel;
	}

}
