package com.upreckless.support.newsapp.ui.news.model;

/**
 * Created by @mistreckless on 10.05.2017. !
 */

public class ItemView {
    private String title;
    private String date;
    private String simpleDesc;
    private String link;
    private boolean isFavorites;

    public ItemView(){}

    public ItemView(String title, String date, String simpleDesc, String link, boolean isFavorites) {
        this.title = title;
        this.date = date;
        this.simpleDesc = simpleDesc;
        this.link=link;
        this.isFavorites=isFavorites;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSimpleDesc() {
        return simpleDesc;
    }

    public void setSimpleDesc(String simpleDesc) {
        this.simpleDesc = simpleDesc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isFavorites() {
        return isFavorites;
    }

    public void setFavorites(boolean favorites) {
        isFavorites = favorites;
    }
}
