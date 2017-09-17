package com.example.aa.itravel.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.os.Message;

import java.io.IOException;
import java.net.URL;

/**
 * Created by anogkongda on 2017/9/16.
 */

public class GetImage implements Runnable {
    private String filename;
    private Handler handler;
    public GetImage(String f,Handler h) {
        filename = f;
        handler = h;
    }

    public void run() {
        try {
            URL url = new URL(Network.IMGURL + filename);
            Bitmap pp = BitmapFactory.decodeStream(url.openStream());
            Message msg = new Message();
            msg.what = 1;
            msg.obj = pp;
            //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
            handler.handleMessage(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
