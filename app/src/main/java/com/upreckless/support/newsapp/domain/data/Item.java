package com.upreckless.support.newsapp.domain.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by @mistreckless on 09.05.2017. !
 */
@Root(name = "item")
public class Item {
    @Element(name = "title")
    private String title;
    @Element(name = "link")
    private String link;
    @Element(name = "description")
    private String description;
    @Element(name = "pubDate")
    private String date;
    @Element(name = "guid")
    private String guid;

    public Item(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
