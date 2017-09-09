package com.example.aa.itravel.tools;

/**
 * Created by admin on 2017/9/9.
 */

public class Comment {
	private Integer commentid;

	private Integer messageid;

	private Integer topicid;

	private Integer authorid;

	private String commentatorname;

	private Integer likenumber;

	public Integer getCommentid() {
		return commentid;
	}

	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}

	public Integer getMessageid() {
		return messageid;
	}

	public void setMessageid(Integer messageid) {
		this.messageid = messageid;
	}

	public Integer getTopicid() {
		return topicid;
	}

	public void setTopicid(Integer topicid) {
		this.topicid = topicid;
	}

	public Integer getAuthorid() {
		return authorid;
	}

	public void setAuthorid(Integer authorid) {
		this.authorid = authorid;
	}

	public String getCommentatorname() {
		return commentatorname;
	}

	public void setCommentatorname(String commentatorname) {
		this.commentatorname = commentatorname == null ? null : commentatorname.trim();
	}

	public Integer getLikenumber() {
		return likenumber;
	}

	public void setLikenumber(Integer likenumber) {
		this.likenumber = likenumber;
	}
}
