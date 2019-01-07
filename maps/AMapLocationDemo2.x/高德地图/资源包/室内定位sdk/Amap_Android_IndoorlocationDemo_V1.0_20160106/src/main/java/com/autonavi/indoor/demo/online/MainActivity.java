package com.autonavi.indoor.demo.online;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import locating.indoor.autonavi.com.onlinelocationdemo.R;

/**
 * 主界面用于用户选择建筑，室内定位必须先指定在哪栋建筑内
 */
public class MainActivity extends Activity implements OnMarkerClickListener, OnInfoWindowClickListener{

	/**
	 * 1. 主界面上用户需要配置支持室内定位的各个码点
	 */
	public static final String MAP_COORD_PATH = "map_coord.txt";
	
	private AMap aMap;
	private MapView mapView;

	private List<MapBean> m_list = new ArrayList<MapBean>();
	private Map<String, String> nameMap = new HashMap<String, String>();
	private Map<String, String> markerMap;
	private Map<String, String> markerMapId;
	private Map<String, String> markerMapName;
	public Marker mSelectedMarker = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap);

        mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);

		InitInfoList();
		init();
		IntentFilter filter=new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

		Thread.UncaughtExceptionHandler mUEHandler = new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				Log.d("Locating", "ERROR uncaughtException");
				MainActivity.this.finish();
			}
		};

		Thread.setDefaultUncaughtExceptionHandler(mUEHandler);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onDestroy()	{
        super.onDestroy();
    }

	private void init(){
		if (aMap == null){
			aMap = mapView.getMap();
			setUpMap();
		}
	}

	private void setUpMap(){
		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		aMap.setOnMarkerClickListener(this);
		CameraUpdate cu = CameraUpdateFactory.changeLatLng(new LatLng(39.98986611, 116.4804978));
		aMap.moveCamera(cu);
		cu = CameraUpdateFactory.zoomBy(2);
		aMap.moveCamera(cu);
		addMarkersToMap();// 往地图上添加marker

	}
	/**
	 * 初始化列表数据
	 */
	private void InitInfoList(){
		ArrayList<MapBean> showList = new ArrayList<MapBean>(); // 用于显示的列表
		String line = null;
		InputStream myInput = null;
		try	{
			File file = new File(Environment.getExternalStorageDirectory(), "IndoorMap/Locating/map_coord.txt");
			if (file.exists())	{
				myInput = new FileInputStream(file);
			}
			else{
				myInput = getAssets().open(MAP_COORD_PATH);
			}
			InputStreamReader isReader = new InputStreamReader(myInput, "UTF-8");
			BufferedReader buffer = new BufferedReader(isReader);
			while ((line = buffer.readLine()) != null)	{
				line = line.replaceAll("  +", "\t");
				line = line.replaceAll("\t\t+", "\t");
				String[] mapCoord = line.split("\t");
				if (mapCoord.length < 7){ // 判断该条数据是否完整，不完整则滤掉
					Log.d("Locating", line);
					continue;
				}

				if (!mapCoord[1].equals("-1")){ // POIID不为-1，直接放入列表
					nameMap.put(mapCoord[0], mapCoord[2]);
					MapBean mb = new MapBean();
					mb.setMapImageName(mapCoord[0]);
					mb.setMapModifyTime(mapCoord[0].length());
					double latitude = (Double.parseDouble(mapCoord[4]) + Double.parseDouble(mapCoord[6])) / 2;
					double longitude = (Double.parseDouble(mapCoord[3]) + Double.parseDouble(mapCoord[5])) / 2;
					String id = mapCoord[0];
					String poiId = mapCoord[1];
					String name = mapCoord[2];
					mb.setMapImageName(id);
					mb.setStrMapImageNameId(poiId);
					mb.setMapDataName(name);
					mb.setLongitude(longitude);
					mb.setLatitude(latitude);
					showList.add(mb);
				}
			}
			buffer.close();
		}
		catch (Throwable e)
		{
			Log.d("Locating", e.toString());
		}
		finally
		{
			try
			{
				myInput.close();
			}
			catch (Throwable e)
			{
				Log.d("Locating", e.toString());
			}
		}
		m_list.addAll(showList);
	}
	/**
	 * 在地图上添加marker
	 */
	void addMarkersToMap()
	{
		markerMap = new HashMap<String, String>();
		markerMapId = new HashMap<String, String>();
		markerMapName = new HashMap<String, String>();
		for (int i = 0; i < m_list.size(); i++)
		{
			LatLng mLatLng = new LatLng(m_list.get(i).getLatitude(), m_list.get(i).getLongitude());
			MarkerOptions markerOption = new MarkerOptions();
			markerOption.position(mLatLng);
			markerOption.title(m_list.get(i).getMapDataName()).snippet(m_list.get(i).getMapImageName() + "_" + m_list.get(i).getStrMapImageNameId());
//			mPositionMarker.title(m_list.get(i).getMapDataName()).snippet(m_list.get(i).getMapImageName());
			markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//			mPositionMarker.perspective(true);
			markerOption.draggable(true);
			Marker XIAN = aMap.addMarker(markerOption);
			markerMap.put(XIAN.getId(), m_list.get(i).getMapImageName());
			markerMapId.put(XIAN.getId(), m_list.get(i).getStrMapImageNameId());
			markerMapName.put(XIAN.getId(), m_list.get(i).getMapDataName());
		}
	}
	
	/**
	 * 监听点击infowindow窗口事件回调
	 */
	@Override
	public void onInfoWindowClick(Marker marker)
	{
		// 点击marker后跳转定位信息
		String taskInfo = marker.getId();
		String strBuildNameId = markerMapId.get(taskInfo);
		if (strBuildNameId.equals("-1"))
			return;
		switchBuilding(strBuildNameId);
	}
	void switchBuilding(String strBuildNameId){
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		intent.setClass(this, OnlineActivity.class);
		bundle.putString("build_name_Id", strBuildNameId);
		bundle.putString("build_nameId", strBuildNameId);
		bundle.putString("build_name", strBuildNameId);
		bundle.putString(MapBean.BUILD_DESCRIPTION_KEY, nameMap.get(strBuildNameId));
		bundle.putParcelableArrayList("result", null);
		intent.putExtras(bundle);
		startActivity(intent);
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    
	@Override
	public boolean onMarkerClick(Marker marker) {
		mSelectedMarker = marker;
		return false;
	}
}
