package com.example.aa.itravel.tools;

/**
 * Created by admin on 2017/9/9.
 */

public class CommentEntityWithBLOBs extends Comment {
	private String commentatorimg;

	private String commentcontent;

	public String getCommentatorimg() {
		return commentatorimg;
	}

	public void setCommentatorimg(String commentatorimg) {
		this.commentatorimg = commentatorimg == null ? null : commentatorimg.trim();
	}


	public String getCommentcontent() {
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent == null ? null : commentcontent.trim();
	}
}
