package com.example.aa.itravel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aa.itravel.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;

import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.aa.itravel.R;
import com.example.aa.itravel.tools.FootPrintEntity;
import com.example.aa.itravel.tools.Country;
import com.example.aa.itravel.tools.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@ContentView(R.layout.activity_foot_print_test)
public class FootPrintTestActivity extends AppCompatActivity implements View.OnClickListener {
	String path = "http://192.168.1.101:8080/iTravel_Server_SSM/AndroidService/";
	// String path1 = "http://223.3.82.239:8080/iTravel_Server_SSM/AndroidService/refresh";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	String TAG = "FootPrintTestActivity";
	private MapView mapView;
	private AMap aMap;
	private RadioGroup mGPSModeGroup;
	private LocationSource.OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	public AMapLocationClientOption mLocationOption = null;
	public AMapLocation amaplocation;
	private TextView mLocationErrText;
	private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
	private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
	private ImageView show;
	private ImageView light;
	private String s;
	private int type;
	private String countryy;
	private EditText ed;
	private String session_id;
	private Map<String,LatLng> countries;
	private double longtitude=0.0;
	private double latitude=0.0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foot_print_test);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		if (aMap == null) {
			aMap = mapView.getMap();

		}
		aMap.setMapLanguage(AMap.CHINESE);
		aMap.setMapType(AMap.MAP_TYPE_NORMAL);
		init();
		initialize();
		Bundle bundle = this.getIntent().getExtras();
		session_id= bundle.getString("sessionId");
		ed=(EditText)findViewById(R.id.editText2);
		show = (ImageView)findViewById(R.id.show    );
		show.setOnClickListener(this);
		light = (ImageView)findViewById(R.id.lightupp );
		light.setOnClickListener(this);


		;


	}
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){

			if(msg.what==1){
				if(type==1)
				{
					String qq = (String) msg.obj;
					Gson gson = new Gson();
					Result re = gson.fromJson(qq, Result.class);
					String back = re.getResult();
					if(back.equals("true"))
					{
						Toast.makeText(FootPrintTestActivity.this,"设置新足迹成功", Toast.LENGTH_SHORT).show();
					}

				}else {
					String qq = (String) msg.obj;
					Log.i(TAG, qq);
					Gson gson = new Gson();
					List<FootPrintEntity> list = gson.fromJson(qq,new TypeToken<List<FootPrintEntity>>(){}.getType());
					for(int i=0;i<list.size();i++)
					{
						newMarker(list.get(i).getLatitude(),list.get(i).getLongitude());
					}
					if(list.isEmpty())
					{
						Toast.makeText(FootPrintTestActivity.this,"您还没有任何足迹", Toast.LENGTH_SHORT).show();
					}


				}

			}

		}
	};
	private void initialize()
	{
		countries = new HashMap<String,LatLng>();
		countries.put("中国",Country.中国 );
		countries.put("美国",Country.美国 );
		countries.put("加拿大",Country.加拿大 );
		countries.put("英国",Country.英国 );
		countries.put("俄罗斯",Country.俄罗斯 );
		countries.put("法国",Country.法国 );
		countries.put("德国",Country.德国 );
		countries.put("意大利",Country.意大利 );
		countries.put("西班牙",Country.西班牙 );
		countries.put("葡萄牙",Country.葡萄牙 );
		countries.put("爱尔兰",Country.爱尔兰 );
		countries.put("白俄罗斯",Country.白俄罗斯 );
		countries.put("冰岛",Country.冰岛 );
		countries.put("波兰",Country.波兰 );
		countries.put("丹麦",Country.丹麦 );
		countries.put("芬兰",Country.芬兰 );
		countries.put("捷克",Country.捷克 );
		countries.put("立陶宛",Country.立陶宛 );
		countries.put("罗马尼亚",Country.罗马尼亚 );
		countries.put("挪威",Country.挪威 );
		countries.put("瑞典",Country.瑞典 );
		countries.put("乌克兰",Country.乌克兰 );
		countries.put("希腊",Country.希腊 );
		countries.put("澳大利亚",Country.澳大利亚 );
		countries.put("新西兰",Country.新西兰 );
		countries.put("埃及",Country.埃及 );
		countries.put("摩洛哥",Country.摩洛哥 );
		countries.put("尼日利亚",Country.尼日利亚 );
		countries.put("以色列",Country.以色列 );
		countries.put("约旦",Country.约旦 );
		countries.put("沙特阿拉伯",Country.沙特阿拉伯 );
		countries.put("南非",Country.南非 );
		countries.put("土耳其",Country.土耳其 );
		countries.put("哈萨克斯坦",Country.哈萨克斯坦 );
		countries.put("吉尔吉斯斯坦",Country.吉尔吉斯斯坦 );
		countries.put("巴基斯坦",Country.巴基斯坦 );
		countries.put("印度",Country.印度 );
		countries.put("尼泊尔",Country.尼泊尔 );
		countries.put("孟加拉国",Country.孟加拉国 );
		countries.put("缅甸",Country.缅甸 );
		countries.put("蒙古",Country.蒙古 );
		countries.put("老挝",Country.老挝 );
		countries.put("泰国",Country.泰国 );
		countries.put("柬埔寨",Country.柬埔寨 );
		countries.put("越南",Country.越南 );
		countries.put("马来西亚",Country.马来西亚 );
		countries.put("新加坡",Country.新加坡 );
		countries.put("印度尼西亚",Country.印度尼西亚 );
		countries.put("巴布亚新几内亚",Country.巴布亚新几内亚 );
		countries.put("菲律宾",Country.菲律宾 );
		countries.put("朝鲜",Country.朝鲜 );
		countries.put("韩国",Country.韩国 );
		countries.put("日本",Country.日本 );
		countries.put("墨西哥",Country.墨西哥 );
		countries.put("洪都拉斯",Country.洪都拉斯 );
		countries.put("尼加拉瓜",Country.尼加拉瓜 );
		countries.put("哥斯达黎加",Country.哥斯达黎加 );
		countries.put("巴拿马",Country.巴拿马 );
		countries.put("哥伦比亚",Country.哥伦比亚 );
		countries.put("委内瑞拉",Country.委内瑞拉 );
		countries.put("巴西",Country.巴西 );
		countries.put("阿根廷",Country.阿根廷 );
		countries.put("秘鲁",Country.秘鲁 );
		countries.put("巴拉圭",Country.巴拉圭 );


	}
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	private void changeCamera(CameraUpdate update) {

		aMap.moveCamera(update);

	}
	private void init()
	{
		LatLng locat = new LatLng(40,117);
		changeCamera(
				CameraUpdateFactory.newCameraPosition(new CameraPosition(
						locat, 3, 30, 30)));
	}


	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.show:
				type=2;
				postRequest(2);

				//    getCameraOption(amaplocation)
				break;
			case R.id.lightupp:
				countryy = ed.getText().toString();
				if(countries.containsKey(countryy)) {
					newMarker(countries.get(countryy));
					changeCamera(
							CameraUpdateFactory.newCameraPosition(new CameraPosition(
									countries.get(countryy),6, 30, 30)));
					type=1;
					postRequest(1);
				}else
				{
					ed.setText("未收录该国");
				}
				break;
		}
	}
	public void newMarker(LatLng country)
	{
		aMap.addMarker(new MarkerOptions().position(country)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
	}
	public void newMarker(double latitude,double longtitude )
	{
		aMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longtitude))
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
	}

	private void postRequest(int typp) {


		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Response response = null;
				try {
					//回调
					if (type == 1) {
						FootPrintEntity footprint = new FootPrintEntity();
						footprint.setCountry(countryy);
						footprint.setLatitude(countries.get(countryy).latitude);
						footprint.setLongitude(countries.get(countryy).longitude);


						Gson gson = new GsonBuilder().create();
						String content = gson.toJson(footprint);
						System.out.println(content);

						RequestBody body = RequestBody.create(JSON, content);

						Request request = new Request.Builder().addHeader("cookie",session_id)
								.url(path + "newfootprint")
								.post(body)
								.build();
						OkHttpClient okhttpc = new OkHttpClient();
						Call call = okhttpc.newCall(request);
						response = call.execute();
						//获取回复的header 剥离sessionId


						if (response.code() == 200) {
							//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
							mHandler.obtainMessage(1, response.body().string()).sendToTarget();

						} else {
							throw new IOException("Unexpected code:" + response);
						}
					} else {
						Request request = new Request.Builder().addHeader("cookie",session_id)
								.url(path + "getfootprints")
								.build();
						OkHttpClient okhttpc = new OkHttpClient();
						Call call = okhttpc.newCall(request);
						response = call.execute();
						//获取回复的header 剥离sessionId


						if (response.code() == 200) {
							//将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
							mHandler.obtainMessage(1, response.body().string()).sendToTarget();

						} else {
							throw new IOException("Unexpected code:" + response);
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

//            try{
//                Request request = new Request.Builder().addHeader("cookie",s).url(path1).build();
//
//                OkHttpClient okhttpc = new OkHttpClient();
//
//                Call call = okhttpc.newCall(request);
//                Response response1 = call.execute();
//
//                System.out.println(response1.body().string());
//            }catch(Exception e){
//                e.printStackTrace();
//            }
			}
		}).start();
	}
/* 定位内容
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        setupLocationStyle();
    }

    private void setupLocationStyle(){
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }


    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            mLocationOption.setNeedAddress(true);
            mLocationOption.setOnceLocation(false);
            mLocationOption.setInterval(2000);
             mLocationOption.setMockEnable(true);
            //设置为高精度定位模式

            //设置定位参数
            mLocationOption.setWifiScan(true);
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();

        }
    }



    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    mListener.onLocationChanged(amapLocation);
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    latitude  = amapLocation.getLatitude();//获取纬度
                 longtitude=    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
*/
}
