package com.example.aa.itravel.tools;

/**
 * Created by admin on 2017/9/9.
 */

public class CommentEntityWithBLOBs extends Comment {
	private byte[] commentatorimg;

	private String commentcontent;

	public byte[] getCommentatorimg() {
		return commentatorimg;
	}

	public void setCommentatorimg(byte[] commentatorimg) {
		this.commentatorimg = commentatorimg;
	}

	public String getCommentcontent() {
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent == null ? null : commentcontent.trim();
	}
}
