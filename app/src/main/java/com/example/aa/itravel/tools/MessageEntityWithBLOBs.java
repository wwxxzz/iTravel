package com.example.aa.itravel.tools;

/**
 * Created by admin on 2017/9/12.
 */

public class MessageEntityWithBLOBs extends Message {
	private String messagecontent;

	private byte[] messageimage;

	public String getMessagecontent() {
		return messagecontent;
	}

	public void setMessagecontent(String messagecontent) {
		this.messagecontent = messagecontent == null ? null : messagecontent.trim();
	}

	public byte[] getMessageimage() {
		return messageimage;
	}

	public void setMessageimage(byte[] messageimage) {
		this.messageimage = messageimage;
	}
}
