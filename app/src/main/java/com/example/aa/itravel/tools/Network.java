package com.example.aa.itravel.tools;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by admin on 2017/9/12.
 */

public class Network {
	final  static public String URL = "http://223.3.81.152:8080/iTravel_Server_SSM/AndroidService/";
	final static public String IMGURL = "http://223.3.81.152:8080/iTravel_Server_SSM/images/";
	static public Bitmap edit(Bitmap bm)
	{
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 设置想要的大小
		int newWidth = 30;
		int newHeight = 30;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		return  Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	}
}
