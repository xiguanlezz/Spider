package com.cj.Spider.entity;

public class Page {
    //页面内容
    private String content;
    private String upnumber;
    private String downnumber;
    private String commentumber;
    private String name;
    private String url;

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getUpnumber() {
        return upnumber;
    }

    public void setUpnumber(String upnumber) {
        this.upnumber = upnumber;
    }

    public String getDownnumber() {
        return downnumber;
    }

    public void setDownnumber(String downnumber) {
        this.downnumber = downnumber;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommentumber() {
        return commentumber;
    }

    public void setCommentumber(String commentumber) {
        this.commentumber = commentumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
