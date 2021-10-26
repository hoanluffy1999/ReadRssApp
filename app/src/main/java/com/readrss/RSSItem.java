package com.readrss;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
         Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
        Matcher matcher = p.matcher(description);

        if(matcher.find()){
            this.img = matcher.group(1);
        }

        this.pubdate = pubdate;
        this.guid = guid;
    }
}
