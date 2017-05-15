package com.upreckless.support.newsapp.util;

import com.upreckless.support.newsapp.domain.data.Item;
import com.upreckless.support.newsapp.domain.entity.ItemEntity;
import com.upreckless.support.newsapp.ui.news.model.ItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @mistreckless on 11.05.2017. !
 */

public class Helper {


    public static List<ItemEntity> convertListItemToItemEntity(List<Item> items) {
        List<ItemEntity> itemEntities = new ArrayList<>(items.size());
        for (Item item :
                items) {
            itemEntities.add(convertItemToITemEntity(item));
        }
        return itemEntities;
    }

    public static ItemEntity convertItemToITemEntity(Item item) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setTitle(item.getTitle());
        itemEntity.setDateTime(item.getDate());
        itemEntity.setSimpleDesc(item.getDescription());
        itemEntity.setLink(item.getLink());
        return itemEntity;
    }

    public static List<ItemView> convertItemEntityesToItemViews(List<ItemEntity> itemEntities) {
        List<ItemView> itemViews = new ArrayList<>(itemEntities.size());
        for (ItemEntity itemEntity :
                itemEntities) {
            itemViews.add(convertItemEntityToItemView(itemEntity));
        }
        return itemViews;
    }

    public static ItemView convertItemEntityToItemView(ItemEntity itemEntity) {
        ItemView itemView = new ItemView(itemEntity.getTitle(), itemEntity.getDateTime(), itemEntity.getSimpleDesc(), itemEntity.getLink(), itemEntity.isFavorites());
        itemView.setTitle(itemView.getTitle().replace("&nbsp;",""));
        itemView.setTitle(itemView.getTitle().replace("&#xa0;",""));
        itemView.setTitle(itemView.getTitle().replace("&#160;",""));
        itemView.setTitle(itemView.getTitle().replace("&#171;",""));
        itemView.setTitle(itemView.getTitle().replace("&#187;",""));
        itemView.setTitle(itemView.getTitle().replace("&amp;",""));
        itemView.setTitle(itemView.getTitle().replace("amp;",""));
        return itemView;
    }
}
