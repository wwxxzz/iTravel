package com.example.aa.itravel.tools;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by anogkongda on 2017/9/16.
 */

public class UploadUtil implements Runnable {
    private File f;

    private String type;
    private Activity activity;
    private Handler handler;
    public UploadUtil(File filename,String t,Activity a,Handler h)
    {
        f = filename;
        type = t;
        activity = a;
        handler = h;
    }

    @Override
    public void run() {
        try {
            int REQUEST_EXTERNAL_STORAGE = 1;
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            MediaType mt = MediaType.parse("image/"+type);
                builder.addFormDataPart("img", f.getName(), RequestBody.create(mt, f));



            MultipartBody requestBody = builder.build();

            Request request = new Request.Builder().url(Network.URL + "springUpload").post(requestBody).build();
            OkHttpClient okhttpc = new OkHttpClient();
            Call call = okhttpc.newCall(request);
            Response response = call.execute();
            Log.i(TAG, "响应成功");
            if (response.isSuccessful()) {
                Log.i(TAG, "响应成功");
                //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                handler.obtainMessage(1, response.body().string()).sendToTarget();
            } else {
                throw new IOException("Unexpected code:" + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

