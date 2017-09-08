package com.example.aa.itravel.tools;

/**
 * Created by Ynez on 2017/9/8.
 */

public class Message {
    private Integer messageid;//动态id

    private Integer userid;//发布者id

    private Integer originaluserid;//原发布者

    private String messagetime;//发布时间

    private Integer likenumber;//点赞数

    private Integer commitnumber;//评论数

    private Boolean isproved;//是否被举报

    private Boolean isrecommended;//是否推送到首页

    private Integer messagetype;//标签

    private String messagelocation;//定位

    private Integer forwardnumber;//转发数

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

    public Integer getOriginaluserid() {
        return originaluserid;
    }

    public void setOriginaluserid(Integer originaluserid) {
        this.originaluserid = originaluserid;
    }

    public String getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(String messagetime) {
        this.messagetime = messagetime == null ? null : messagetime.trim();
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
}
