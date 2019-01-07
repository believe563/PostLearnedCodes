package com.autonavi.indoor.demo.online;

/**
 * 地图上支持室内定位的建筑码点
 */
public class MapBean
{
	public static String BUILD_DESCRIPTION_KEY = "build_description_name";

	private String strMapImageName;
	private String strMapImageNameId;
	private String strMapDataName;
	private long lModifyTime;
	private double longitude;
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	private double latitude;
	


	public MapBean()
	{
		strMapDataName = "";
		strMapImageName = "";
		strMapImageNameId = "";
		lModifyTime = 0;
		longitude = 0;
		latitude = 0;
		
	}

	public String getStrMapImageNameId() {
		return strMapImageNameId;
	}

	public void setStrMapImageNameId(String strMapImageNameId) {
		this.strMapImageNameId = strMapImageNameId;
	}

	public void setMapImageName(String mapImageName)
	{
		strMapImageName = mapImageName;
	}
	
	public void setMapDataName(String mapDataName)
	{
		strMapDataName = mapDataName;
	}

	public void setMapModifyTime(long modifyTime)
	{
		lModifyTime = modifyTime;
	}

	public String getMapImageName()
	{
		return strMapImageName;
	}
	
	public String getMapDataName()
	{
		return strMapDataName;
	}
}
