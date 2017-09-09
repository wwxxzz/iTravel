package com.example.aa.itravel.tools;

import java.util.Date;

/**
 * Created by admin on 2017/9/7.
 */

public class User {
	private Integer userid;

	private String usertel;

	private String username;

	private String userpassword;

	private String usersex;

	private Boolean isproved;

	private Boolean isblocked;

	private Boolean isadmin;

	private Date usertime;

	private String userfootprint;

	private Integer preferencemessagetype;

	private String usercollection;

	private String usermessage;

	private String usercareer;

	private String userlocation;

	private String useremail;

	private String userbirth;

	private byte[] userphoto;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsertel() {
		return usertel;
	}

	public void setUsertel(String usertel) {
		this.usertel = usertel == null ? null : usertel.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword == null ? null : userpassword.trim();
	}

	public String getUsersex() {
		return usersex;
	}

	public void setUsersex(String usersex) {
		this.usersex = usersex == null ? null : usersex.trim();
	}

	public Boolean getIsproved() {
		return isproved;
	}

	public void setIsproved(Boolean isproved) {
		this.isproved = isproved;
	}

	public Boolean getIsblocked() {
		return isblocked;
	}

	public void setIsblocked(Boolean isblocked) {
		this.isblocked = isblocked;
	}

	public Boolean getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(Boolean isadmin) {
		this.isadmin = isadmin;
	}

	public Date getUsertime() {
		return usertime;
	}

	public void setUsertime(Date usertime) {
		this.usertime = usertime;
	}

	public String getUserfootprint() {
		return userfootprint;
	}

	public void setUserfootprint(String userfootprint) {
		this.userfootprint = userfootprint == null ? null : userfootprint.trim();
	}

	public Integer getPreferencemessagetype() {
		return preferencemessagetype;
	}

	public void setPreferencemessagetype(Integer preferencemessagetype) {
		this.preferencemessagetype = preferencemessagetype;
	}

	public String getUsercollection() {
		return usercollection;
	}

	public void setUsercollection(String usercollection) {
		this.usercollection = usercollection == null ? null : usercollection.trim();
	}

	public String getUsermessage() {
		return usermessage;
	}

	public void setUsermessage(String usermessage) {
		this.usermessage = usermessage == null ? null : usermessage.trim();
	}

	public String getUsercareer() {
		return usercareer;
	}

	public void setUsercareer(String usercareer) {
		this.usercareer = usercareer == null ? null : usercareer.trim();
	}

	public String getUserlocation() {
		return userlocation;
	}

	public void setUserlocation(String userlocation) {
		this.userlocation = userlocation == null ? null : userlocation.trim();
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail == null ? null : useremail.trim();
	}

	public String getUserbirth() {
		return userbirth;
	}

	public void setUserbirth(String userbirth) {
		this.userbirth = userbirth;
	}

	public byte[] getUserphoto() {
		return userphoto;
	}

	public void setUserphoto(byte[] userphoto) {
		this.userphoto = userphoto;
	}

	public void setPassword(String s) {

	}
}

