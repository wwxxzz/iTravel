package com.example.aa.itravel.tools;

/**
 * Created by aa on 2017/9/12.
 */

public class MessageBuffer {
    private Integer messagebid;

    private Integer fromuserid;

    private String fromusername;

    private Integer touserid;

    private String sendtime;

    private Integer messagebtype;

    private String tousername;

    private String messagebcontent;

    public Integer getMessagebid() {
        return messagebid;
    }

    public void setMessagebid(Integer messagebid) {
        this.messagebid = messagebid;
    }

    public Integer getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(Integer fromuserid) {
        this.fromuserid = fromuserid;
    }

    public String getFromusername() {
        return fromusername;
    }

    public void setFromusername(String fromusername) {
        this.fromusername = fromusername == null ? null : fromusername.trim();
    }

    public Integer getTouserid() {
        return touserid;
    }

    public void setTouserid(Integer touserid) {
        this.touserid = touserid;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getMessagebtype() {
        return messagebtype;
    }

    public void setMessagebtype(Integer messagebtype) {
        this.messagebtype = messagebtype;
    }

    public String getTousername() {
        return tousername;
    }

    public void setTousername(String tousername) {
        this.tousername = tousername == null ? null : tousername.trim();
    }

    public String getMessagebcontent() {
        return messagebcontent;
    }

    public void setMessagebcontent(String messagebcontent) {
        this.messagebcontent = messagebcontent == null ? null : messagebcontent.trim();
    }
}
