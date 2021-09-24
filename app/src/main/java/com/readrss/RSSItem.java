package com.readrss;


public class RSSItem {

    public String title;
    public String link;
    public String description;
    public String pubdate;
    public String guid;
    public String img;

    public RSSItem(String title, String link, String description, String pubdate, String guid) {
        this.title = title;
        this.link = link;
        this.description = description;
        /// Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
        this.img =  description.split("src=")[1].split("''")[0];
        this.pubdate = pubdate;
        this.guid = guid;
    }
}
