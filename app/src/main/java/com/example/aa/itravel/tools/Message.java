package com.example.aa.itravel.tools;

import java.util.Date;

/**
 * Created by Ynez on 2017/9/8.
 */

public class Message {
    private Integer messageid;

    private Integer userid;

    private String username;

    private Integer originalmessageid;

    private String originalusername;

    private String messagetime;

    private Integer likenumber;

    private Integer commitnumber;

    private Boolean isproved;

    private Boolean isrecommended;

    private Integer messagetype;

    private String messagelocation;

    private Integer forwardnumber;

    private String userimage;


    public Integer getMessageid() {
        return messageid;
    }

    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getOriginalmessageid() {
        return originalmessageid;
    }

    public void setOriginalmessageid(Integer originalmessageid) {
        this.originalmessageid = originalmessageid;
    }

    public String getOriginalusername() {
        return originalusername;
    }

    public void setOriginalusername(String originalusername) {
        this.originalusername = originalusername == null ? null : originalusername.trim();
    }

    public String getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(String messagetime) {
        this.messagetime = messagetime;
    }

    public Integer getLikenumber() {
        return likenumber;
    }

    public void setLikenumber(Integer likenumber) {
        this.likenumber = likenumber;
    }

    public Integer getCommitnumber() {
        return commitnumber;
    }

    public void setCommitnumber(Integer commitnumber) {
        this.commitnumber = commitnumber;
    }

    public Boolean getIsproved() {
        return isproved;
    }

    public void setIsproved(Boolean isproved) {
        this.isproved = isproved;
    }

    public Boolean getIsrecommended() {
        return isrecommended;
    }

    public void setIsrecommended(Boolean isrecommended) {
        this.isrecommended = isrecommended;
    }

    public Integer getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(Integer messagetype) {
        this.messagetype = messagetype;
    }

    public String getMessagelocation() {
        return messagelocation;
    }

    public void setMessagelocation(String messagelocation) {
        this.messagelocation = messagelocation == null ? null : messagelocation.trim();
    }

    public Integer getForwardnumber() {
        return forwardnumber;
    }

    public void setForwardnumber(Integer forwardnumber) {
        this.forwardnumber = forwardnumber;
    }


    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage == null ? null : userimage.trim();
    }

}
