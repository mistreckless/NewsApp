package com.upreckless.support.newsapp.domain.repository;


import com.upreckless.support.newsapp.domain.data.Item;
import com.upreckless.support.newsapp.domain.entity.ItemEntity;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by @mistreckless on 08.05.2017. !
 */
public interface NewsRepository {

    Observable<List<Item>> getNews();

    Observable<String> getHtmlPage(String url);

    boolean saveItemList(List<ItemEntity> items);

    List<ItemEntity> getItems();

    boolean updateItem(ItemEntity itemEntity);

    List<ItemEntity> getFavoritesItems();

    boolean saveHtmlPage(String html, String url);

    ItemEntity getItem(String url);

    boolean removeFile(String url);

    File getSavedFile(String url);
}
