package com.upreckless.support.newsapp.domain.entity;

import java.io.Serializable;

/**
 * Created by @mistreckless on 10.05.2017. !
 */

public class ItemEntity implements Serializable{
    private String title;
    private String link;
    private String simpleDesc;
    private String dateTime;
    private boolean isFavorites;

    public ItemEntity(){}

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

    public String getSimpleDesc() {
        return simpleDesc;
    }

    public void setSimpleDesc(String simpleDesc) {
        this.simpleDesc = simpleDesc;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isFavorites() {
        return isFavorites;
    }

    public void setFavorites(boolean favorites) {
        isFavorites = favorites;
    }
}
