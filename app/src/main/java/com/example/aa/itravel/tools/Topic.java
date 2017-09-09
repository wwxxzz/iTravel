package com.example.aa.itravel.tools;

import java.util.Date;

/**
 * Created by admin on 2017/9/9.
 */

public class Topic {
	private Integer topicid;

	private String theme;

	private String topiccontent;

	private Date date;

	private Integer numberofcomment;

	private Boolean homepage;

	private Integer sharnumber;

	private Integer topicimgpos;

	private byte[] topicimg;

	public Integer getTopicid() {
		return topicid;
	}

	public void setTopicid(Integer topicid) {
		this.topicid = topicid;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme == null ? null : theme.trim();
	}

	public String getTopiccontent() {
		return topiccontent;
	}

	public void setTopiccontent(String topiccontent) {
		this.topiccontent = topiccontent == null ? null : topiccontent.trim();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getNumberofcomment() {
		return numberofcomment;
	}

	public void setNumberofcomment(Integer numberofcomment) {
		this.numberofcomment = numberofcomment;
	}

	public Boolean getHomepage() {
		return homepage;
	}

	public void setHomepage(Boolean homepage) {
		this.homepage = homepage;
	}

	public Integer getSharnumber() {
		return sharnumber;
	}

	public void setSharnumber(Integer sharnumber) {
		this.sharnumber = sharnumber;
	}

	public Integer getTopicimgpos() {
		return topicimgpos;
	}

	public void setTopicimgpos(Integer topicimgpos) {
		this.topicimgpos = topicimgpos;
	}

	public byte[] getTopicimg() {
		return topicimg;
	}

	public void setTopicimg(byte[] topicimg) {
		this.topicimg = topicimg;
	}
}
