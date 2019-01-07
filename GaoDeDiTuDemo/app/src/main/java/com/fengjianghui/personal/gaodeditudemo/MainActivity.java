package com.fengjianghui.personal.gaodeditudemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyTrafficStyle;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.maps.model.Poi;
import com.amap.api.maps.overlay.BusRouteOverlay;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.fengjianghui.personal.gaodeditudemo.util.AMapUtil;
import com.fengjianghui.personal.gaodeditudemo.util.OffLineMapUtils;
import com.fengjianghui.personal.gaodeditudemo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, LocationSource, AMapLocationListener,
        AMap.OnPOIClickListener, AMap.OnMarkerClickListener,
        PoiSearch.OnPoiSearchListener, TextWatcher,
        AMap.OnMyLocationChangeListener,AMap.OnInfoWindowClickListener,
        OnRouteSearchListener{

    //声明变量
    private MapView mapView;
    private AMap aMap;
    private Spinner spinner;//点击下拉列表选择显示样式
    private CheckBox traffic;//点击是否选择路况
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private UiSettings mUiSettings;

    private AutoCompleteTextView act_keyword;
    private EditText et_city;
    private String keywords;
    private ProgressDialog proDialog = null;
    private PoiResult poiResult;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private AMapNavi aMapNavi;//导航引擎
    private AMapNaviView aMapNaviView;//暂时没有用
    private LatLng nowLatLng;
    private LatLng endLatLng;
    private LatLonPoint nowPoint;
    private LatLonPoint endPoint;
    private long startLatitude;
    private long startLongitude;
    private long endLatitude;
    private long endLongitude;
    private ArrayList<NaviLatLng> startPoints;
    private ArrayList<NaviLatLng> endPoints;

    private boolean isLocated;//是否成功定位
    private int routeType;//路径类型--步行路径/公交路径/驾车路径
    private RouteSearch routeSearch;//设置路径规划
    private DriveRouteResult driveRouteResult;// 驾车模式查询结果
    private BusRouteResult busRouteResult;
    private WalkRouteResult walkRouteResult;

    //起点和终点的实际名字
    private String strStart;
    private String strEnd;

    //唯一一个markerOption
    private MarkerOptions mMarkerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        mapView = (MapView) findViewById (R.id.map);//获得mapView实例
        mapView.onCreate (savedInstanceState);//mapview与activity绑定

        init ();
        searchAPosition ();

        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
        //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        MapsInitializer.sdcardDir = OffLineMapUtils.getSdCacheDir(this);

//        measureZoomLevel ();//测试最高和最低层级
    }

    private void searchAPosition() {
        Button searchButton = (Button) findViewById (R.id.bt_search);
        searchButton.setOnClickListener (this);
        act_keyword = (AutoCompleteTextView) findViewById (R.id.act_keyWord);
        act_keyword.addTextChangedListener (this);
        et_city = (EditText) findViewById (R.id.et_city);
        //aMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
        //aMap.setInfoWindowAdapter(this);·// 添加显示infowindow监听事件
    }

    private void measureZoomLevel() {
        //测试出当前地图最小和最大的缩放级别
        int minlevel = (int) aMap.getMinZoomLevel ();
        int maxlevel = (int) aMap.getMaxZoomLevel ();
        Toast.makeText (MainActivity.this, "" + minlevel, Toast.LENGTH_SHORT).show ();//3
        Toast.makeText (MainActivity.this, "" + maxlevel, Toast.LENGTH_SHORT).show ();//20
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap ();
            setThreeStyles ();
            setUpMap ();
            mUiSettings = aMap.getUiSettings ();
            mUiSettings.setCompassEnabled (true);//显示指南针
            mUiSettings.setScaleControlsEnabled (true);//显示比例尺
        }
    }

    private void setThreeStyles() {
        //设置三种显示样式--标准/夜晚/卫星
        spinner = (Spinner) findViewById (R.id.layers_spinner);
        traffic = (CheckBox) findViewById (R.id.traffic);
        traffic.setOnClickListener (this);
        //第二个参数是在string里定义的string-array，要用R.array....定义
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource (MainActivity.this, R.array.traffic_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter (adapter);
        spinner.setOnItemSelectedListener (this);
        MyTrafficStyle myTrafficStyle = new MyTrafficStyle ();
        myTrafficStyle.setSeriousCongestedColor (0xff92000a);
        myTrafficStyle.setCongestedColor (0xffea0312);
        myTrafficStyle.setSlowColor (0x00ff7508);
        myTrafficStyle.setSmoothColor (0xff00a209);
        aMap.setMyTrafficStyle (myTrafficStyle);
//        aMap.setPointToCenter (400,600);
//        //改变地图的旋转角度，即表示地图以屏幕像素点旋转地图。
//        CameraUpdate cameraUpdate = CameraUpdateFactory.changeBearing (45);
        //设置地图底图文字的z轴指数
        aMap.setMapTextZIndex (10);
        aMap.setMapType (AMap.MAP_TYPE_NORMAL);

    }

    private void setActivate1() {
        //定位
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient (getApplicationContext ());

            mLocationOption = new AMapLocationClientOption ();
            //设置定位监听
            mlocationClient.setLocationListener (this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode (AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置只定位一次
//            mLocationOption.setOnceLocation (true);

            /**
             * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
             * 注意：只有在高精度模式下的单次定位有效，其他方式无效
             */
            mLocationOption.setGpsFirst(true);

            mLocationOption.setInterval (100000);//100秒
            //设置定位参数
            mlocationClient.setLocationOption (mLocationOption);

            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation ();
        }
    }

    /*
    * 设置amap的一些属性
    * */
    private void setUpMap() {
        aMap.setLocationSource (this);//设置定位监听
        aMap.getUiSettings ().setMyLocationButtonEnabled (true);//设置默认定位按钮是否显示
        aMap.setOnMyLocationChangeListener (this);//暂时没有用
        //aMap.getUiSettings ().setZoomPosition (16);
        aMap.setMyLocationEnabled (true);//显示定位层并且可触发定位
        aMap.setOnPOIClickListener (this);
        aMap.setOnMarkerClickListener (this);
        aMap.setOnInfoWindowClickListener(this);
        aMap.moveCamera (CameraUpdateFactory.zoomTo (18));//将地图显示层级设置到18，显示较小区域
//        // 自定义系统定位小蓝点
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//                .fromResource(R.mipmap.bluecircle));// 设置小蓝点的图标
//
//        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
//        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
//
//// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
//        myLocationStyle.strokeWidth(0.1f);// 设置圆形的边框粗细
//        aMap.setMyLocationStyle(myLocationStyle);

        aMap.setMyLocationType (AMap.LOCATION_TYPE_MAP_ROTATE);//设置定位类型为定位，还有跟随和旋转
//        aMap.setMyLocationRotateAngle (90);//对系统默认定位图标设置旋转角度
    }

    @Override
    protected void onResume() {
        super.onResume ();
        mapView.onResume ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        mapView.onPause ();
        deactivate ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        mapView.onDestroy ();
        if (null != mlocationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mlocationClient.onDestroy();
            mlocationClient = null;
            mLocationOption = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.traffic:
                //traffic按钮按下即为可以显示交通状况
                aMap.setTrafficEnabled (((CheckBox) v).isChecked ());
                break;
            case R.id.bt_search://搜索地点的按钮
                searchButton ();
                break;
        }


    }

    private void searchButton() {
        keywords = AMapUtil.checkEditText (act_keyword);//得到输入的关键字字符串
        if ("".equals (keywords)) {
            ToastUtil.show (MainActivity.this, "请输入搜索关键字");
            return;
        } else {
            doSearchQuery ();
        }
    }

    private void doSearchQuery() {
        showProgressDialog ();// 显示进度框
//        currentPage = 0;
        query = new PoiSearch.Query (keywords, "", et_city.getText ().toString ());// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
//        query.setPageSize(10);// 设置每页最多返回多少条poiitem
//        query.setPageNum(currentPage);// 设置查第一页
        poiSearch = new PoiSearch (this, query);
        poiSearch.setOnPoiSearchListener (this);
        poiSearch.searchPOIAsyn ();
    }

    //显示进度条
    private void showProgressDialog() {
        if (proDialog == null) {
            proDialog = new ProgressDialog (this);
        }
        proDialog.setProgressStyle (ProgressDialog.STYLE_SPINNER);
        proDialog.setIndeterminate (false);
        proDialog.setCancelable (false);
        proDialog.setMessage ("正在搜索\n" + keywords);
        proDialog.show ();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (aMap != null) {
            setLayer ((String) parent.getItemAtPosition (position));
        }
    }

    private void setLayer(String layerName) {
        if (layerName.equals (getString (R.string.normal))) {
            aMap.setMapType (AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        } else if (layerName.equals (getString (R.string.satellite))) {
            aMap.setMapType (AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
        } else if (layerName.equals (getString (R.string.night))) {
            aMap.setMapType (AMap.MAP_TYPE_NIGHT);//夜景地图模式
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState (outState);
        mapView.onSaveInstanceState (outState);
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        setActivate1 ();
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation ();
            mlocationClient.onDestroy ();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {


        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode () == 0) {
                double locateLat = amapLocation.getLatitude ();//获得当前位置的经度
                double locateLng = amapLocation.getLongitude ();//获得当前位置的纬度
                System.out.println("经度为："+locateLng+"纬度为:"+locateLat);
                if (0.0 == locateLat || 0.0 == locateLng) {
                    Log.e ("[Location]", "Error Location:not update map");
                    isLocated = false;
                } else {
                    isLocated = true;
                    nowLatLng = new LatLng (locateLat, locateLng);//当前位置
                }
                mListener.onLocationChanged (amapLocation);// 显示系统小蓝点
                //当前位置名称
                strStart=amapLocation.getPoiName();
            } else {
                Log.e ("AmapErr", "定位失败");
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        return false;
    }

    private void setRoutForLocation(Marker marker) {
        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
        //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);

        strEnd=mMarkerOptions.getSnippet();
//        Toast.makeText(MainActivity.this, "strEnd:"+strEnd, Toast.LENGTH_SHORT).show();
        routeType=2;//驾车路径规划
        searchRouteResult(nowPoint,endPoint);

    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(LatLonPoint startPoint, LatLonPoint endPoint) {
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startPoint, endPoint);
        if (routeType == 1) {// 公交路径规划
            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, RouteSearch.BusDefault, "北京", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
            routeSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
        } else if (routeType == 2) {// 驾车路径规划
            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();

            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault,
                    null, null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            routeSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else if (routeType == 3) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
            routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }

    private void setNaviLatLng(Marker marker) {//设置导航参数
        // 导航
        // 起点终点列表
        startPoints = new ArrayList<NaviLatLng> ();
        endPoints = new ArrayList<NaviLatLng> ();

        endLatLng = marker.getPosition ();
        endPoint = AMapUtil.convertToLatLonPoint(endLatLng);

//        System.out.println ("纬度为：" + endLatLng.latitude + "，经度为：" + endLatLng.longitude);
        NaviLatLng endNaviLatLng = new NaviLatLng (endLatLng.latitude,
                endLatLng.longitude);
        endPoints.add (endNaviLatLng);


        NaviLatLng startNaviLatLng = new NaviLatLng (nowLatLng.latitude,
                nowLatLng.longitude);
        startPoints.add (startNaviLatLng);

    }

    private boolean getOutNaviBag(Marker marker) {//得到高德地图app的导航页面
        //构造导航函数
        NaviPara naviPara = new NaviPara ();
        //设置终点位置
        naviPara.setTargetPoint (marker.getPosition ());
        //设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle (AMapUtils.DRIVING_AVOID_CONGESTION);
        //调起高德地图导航
        try {
            AMapUtils.openAMapNavi (naviPara, getApplicationContext ());
        } catch (AMapException e) {
            //如果没有安装会进入异常，调起下载页面
            AMapUtils.getLatestAMapApp (getApplicationContext ());
        }
        aMap.clear ();
        return false;
    }

    @Override
    public void onPOIClick(Poi poi) {//点击时出现小的dialog
        aMap.clear ();//清除蓝色小图标
        mMarkerOptions = new MarkerOptions ();
        mMarkerOptions.position (poi.getCoordinate ());
        mMarkerOptions.title("去");
        mMarkerOptions.snippet(poi.getName());
//        mMarkerOptions.title("到 "+poi.getName()+" 去");
//        TextView textView = new TextView (getApplicationContext ());
//        textView.setText ("到" + poi.getName () + "去");
//        textView.setGravity (Gravity.CENTER);
//        textView.setTextColor (Color.argb (255, 0, 0, 250));
//        textView.setBackgroundResource (R.mipmap.custom_info_bubble);
//        mMarkerOptions.icon (BitmapDescriptorFactory.fromView (textView));
        aMap.addMarker (mMarkerOptions);
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dismissProgressDialog ();//取消显示进度条
        if (rCode == 0) {
            if (result != null && result.getQuery () != null) {// 搜索poi的结果
                if (result.getQuery ().equals (query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois ();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys ();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size () > 0) {
                        //aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay (aMap, poiItems);
                        poiOverlay.removeFromMap ();
                        poiOverlay.addToMap ();
                        poiOverlay.zoomToSpan ();
                    } else if (suggestionCities != null
                            && suggestionCities.size () > 0) {
                        showSuggestCity (suggestionCities);
                    } else {
                        ToastUtil.show (MainActivity.this,
                                R.string.no_result);
                    }
                }
            } else {
                ToastUtil.show (MainActivity.this,
                        R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show (MainActivity.this,
                    R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.show (MainActivity.this, R.string.error_key);
        } else {
            ToastUtil.show (MainActivity.this,
                    getString (R.string.error_other) + rCode);
        }
    }

    //返回一些推荐城市的信息
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size (); i++) {
            infomation += "城市名称:" + cities.get (i).getCityName () + "城市区号:"
                    + cities.get (i).getCityCode () + "城市编码:"
                    + cities.get (i).getAdCode () + "\n";
        }
        ToastUtil.show (MainActivity.this, infomation);

    }

    private void dismissProgressDialog() {
        if (proDialog != null) {
            proDialog.dismiss ();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString ().trim ();
        Inputtips inputTips = new Inputtips (MainActivity.this,
                new Inputtips.InputtipsListener () {

                    @Override
                    public void onGetInputtips(List<Tip> tipList, int rCode) {
                        if (rCode == 0) {// 正确返回
                            List<String> listString = new ArrayList<String> ();
                            for (int i = 0; i < tipList.size (); i++) {
                                listString.add (tipList.get (i).getName ());
                            }
                            ArrayAdapter<String> aAdapter = new ArrayAdapter<String> (
                                    getApplicationContext (),
                                    R.layout.route_inputs, listString);
                            act_keyword.setAdapter (aAdapter);
                            aAdapter.notifyDataSetChanged ();
                        }
                    }
                });
        try {
            inputTips.requestInputtips (newText, et_city.getText ().toString ());// 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号

        } catch (com.amap.api.services.core.AMapException e) {
            e.printStackTrace ();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }



    /**
     * 调起高德地图导航功能，如果没安装高德地图，会进入异常，可以在异常中处理，调起高德地图app的下载页面
     */
    public void startAMapNavi(Marker marker) {
        // 构造导航参数
        NaviPara naviPara = new NaviPara ();
        // 设置终点位置
        naviPara.setTargetPoint (marker.getPosition ());
        // 设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle (NaviPara.DRIVING_AVOID_CONGESTION);

        // 调起高德地图导航
        try {
            AMapUtils.openAMapNavi (naviPara, getApplicationContext ());
        } catch (com.amap.api.maps.AMapException e) {

            // 如果没安装会进入异常，调起下载页面
            AMapUtils.getLatestAMapApp (getApplicationContext ());

        }

    }

    @Override
    public void onMyLocationChange(Location location) {
        nowLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        double locateLat=location.getLatitude();
        double locateLng=location.getLongitude();
        if (0.0 == locateLat || 0.0 == locateLng) {
            Log.e ("[Location]", "Error Location:not update map");
            isLocated = false;
        } else {
            isLocated = true;
            nowLatLng = new LatLng (locateLat, locateLng);//当前位置
            nowPoint = AMapUtil.convertToLatLonPoint(nowLatLng);
        }
        mListener.onLocationChanged(location);//显示系统蓝点
        //地点名称
        strStart=((AMapLocation) location).getPoiName();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //设置导航的经纬度参数
        setNaviLatLng (marker);
        Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
        if (isLocated) {//定位成功时

            //规划路径
            setRoutForLocation(marker);
            // DrivingSaveMoney--省钱
            // DrivingShortDistance--最短距离
            // DrivingNoExpressways--不走高速
            // DrivingFastestTime--最短时间
            // DrivingAvoidCongestion--避免拥堵
//            aMapNavi = AMapNavi.getInstance (MainActivity.this);
//            aMapNavi.calculateDriveRoute (startPoints, endPoints, null,
//                    AMapNavi.DrivingDefault);

            //Intent intent = new Intent (MainActivity.this,AmapNaviViewActivity.class);
//            intent.addFlags (Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
////            Bundle bundle = new Bundle ();
////            bundle.putSerializable ("startList", startPoints);
////            bundle.putSerializable ("endList", endPoints);
////            intent.putExtras (bundle);
            //startActivity (intent);

        } else {
            Toast.makeText (getApplicationContext (), "未定位",
                    Toast.LENGTH_SHORT).show ();
        }

        // getOutNaviBag (marker);//得到网页导航页面
    }

    @Override
    public void onBusRouteSearched(BusRouteResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                busRouteResult = result;
                BusPath busPath = busRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                BusRouteOverlay routeOverlay = new BusRouteOverlay(this, aMap,
                        busPath, busRouteResult.getStartPos(),
                        busRouteResult.getTargetPos());
                routeOverlay.removeFromMap();
                routeOverlay.addToMap();
                routeOverlay.zoomToSpan();
            } else {
                ToastUtil.show(MainActivity.this, R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show(MainActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.show(MainActivity.this, R.string.error_key);
        } else {
            ToastUtil.show(MainActivity.this, getString(R.string.error_other)
                    + rCode);
        }
    }

    /*
    * 驾车结果回调
    * */
    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
//        dissmissProgressDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                driveRouteResult = result;
                DrivePath drivePath = driveRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                        this, aMap, drivePath, driveRouteResult.getStartPos(),
                        driveRouteResult.getTargetPos());
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();
            } else {
                ToastUtil.show(MainActivity.this, R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show(MainActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.show(MainActivity.this, R.string.error_key);
        } else {
            ToastUtil.show(MainActivity.this, getString(R.string.error_other)
                    + rCode);
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int rCode) {
//        dissmissProgressDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                walkRouteResult = result;
                WalkPath walkPath = walkRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this,
                        aMap, walkPath, walkRouteResult.getStartPos(),
                        walkRouteResult.getTargetPos());
                walkRouteOverlay.removeFromMap();
                walkRouteOverlay.addToMap();
                walkRouteOverlay.zoomToSpan();
            } else {
                ToastUtil.show(MainActivity.this, R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show(MainActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.show(MainActivity.this, R.string.error_key);
        } else {
            ToastUtil.show(MainActivity.this, getString(R.string.error_other)
                    + rCode);
        }
    }
}
