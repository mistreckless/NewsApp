package com.upreckless.support.newsapp.ui.web.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.upreckless.support.newsapp.ui.news.model.ItemView;
import com.upreckless.support.newsapp.ui.webnews.WebNews;

import java.util.List;

/**
 * Created by @mistreckless on 12.05.2017. !
 */

public class WebPageAdapter extends FragmentStatePagerAdapter {
    private List<ItemView> itemViews;
    private boolean isFavorites;

    public WebPageAdapter(FragmentManager fm, List<ItemView> itemViews, boolean isFavorites) {
        super(fm);
        this.itemViews=itemViews;
    }

    public void setItemViews(List<ItemView> itemViews) {
        this.itemViews = itemViews;
    }

    public List<ItemView> getItemViews() {
        return itemViews;
    }

    @Override
    public Fragment getItem(int position) {
        return WebNews.newInstance(itemViews.get(position).getLink(), isFavorites);
    }

    @Override
    public int getCount() {
        return itemViews.size();
    }

    public ItemView getItemView(int position){
        return itemViews.get(position);
    }


}
