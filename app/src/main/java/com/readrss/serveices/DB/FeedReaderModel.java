package com.readrss.serveices.DB;

public class FeedReaderModel {
    public String title;
    public String content;
    public String url;
    public  String img;
    public  long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;

    }

    public FeedReaderModel(String title, String content, String url, String img, String date) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.img = img;
        Date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String Date;
}
