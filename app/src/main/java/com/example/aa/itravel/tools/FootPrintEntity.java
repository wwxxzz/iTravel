package com.example.aa.itravel.tools;

/**
 * Created by admin on 2017/9/15.
 */

public class FootPrintEntity {
	private Integer footprintid;

	private Integer userid;

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

	private String country;

	public Integer getFootprintid() {
		return footprintid;
	}

	public void setFootprintid(Integer footprintid) {
		this.footprintid = footprintid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}



	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}
}
