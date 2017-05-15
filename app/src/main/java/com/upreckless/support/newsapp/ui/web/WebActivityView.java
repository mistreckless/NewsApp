package com.upreckless.support.newsapp.ui.web;

import com.upreckless.support.newsapp.ui.news.model.ItemView;

import java.util.List;

/**
 * Created by @mistreckless on 12.05.2017. !
 */
interface WebActivityView {
    void reInitViews();

    void init(List<ItemView> itemViews, int position, boolean isFavorites);

    void showToast(String s);

    void resetMenuItem(boolean b);

    void showShareDialog(ItemView itemView);
}
