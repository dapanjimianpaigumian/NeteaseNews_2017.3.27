package com.yulu.zhaoxinpeng.neteasenews.bean;

/**
 * Created by Administrator on 2017/3/16.
 */
public class NewsInfor {
    String summary ;
    String icon ;
    String stamp ;
    String title ;
    int nid ;
    String link ;
    int type ;

    public NewsInfor(String summary, String icon, String stamp, String title, int nid, String link, int type) {
        this.summary = summary;
        this.icon = icon;
        this.stamp = stamp;
        this.title = title;
        this.nid = nid;
        this.link = link;
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public String getStamp() {
        return stamp;
    }

    public String getTitle() {
        return title;
    }

    public int getNid() {
        return nid;
    }

    public String getLink() {
        return link;
    }

    public int getType() {
        return type;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NewsInfor{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", stamp='" + stamp + '\'' +
                ", title='" + title + '\'' +
                ", nid=" + nid +
                ", link='" + link + '\'' +
                ", type=" + type +
                '}';
    }
}
