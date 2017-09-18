package com.example.aa.itravel;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by aa on 2017/9/5.
 */


public class ITravelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//初始化
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
