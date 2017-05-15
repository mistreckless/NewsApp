package com.upreckless.support.newsapp.domain.repository;

import android.content.Context;
import android.util.Log;

import com.esotericsoftware.kryo.Kryo;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.KeyIterator;
import com.snappydb.SnappydbException;
import com.upreckless.support.newsapp.domain.RestApi;
import com.upreckless.support.newsapp.domain.data.Item;
import com.upreckless.support.newsapp.domain.entity.ItemEntity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by @mistreckless on 08.05.2017. !
 */

public class NewsRepositoryImpl implements NewsRepository {
    private RestApi restApi;
    private Context context;

    @Inject
    public NewsRepositoryImpl(RestApi restApi, Context context) {
        this.restApi = restApi;
        this.context = context;
    }

    @Override
    public Observable<List<Item>> getNews() {
        return restApi.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> getHtmlPage(String url) {
        OkHttpClient client = new OkHttpClient();
        return Observable.just(new Request.Builder().url(url).build())
                .map(request -> client.newCall(request).execute())
                .map(response -> response.body().string())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public boolean saveItemList(List<ItemEntity> items) {

        DB snappyDb = null;
        try {
            snappyDb = DBFactory.open(context, "newsdb", new Kryo());
            for (ItemEntity itemEntity :
                    items) {
                if (!snappyDb.exists(itemEntity.getLink()))
                    snappyDb.put(itemEntity.getLink(), itemEntity);
            }
            return true;
        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            if (snappyDb != null)
                try {
                    snappyDb.close();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    @Override
    public List<ItemEntity> getItems() {
        List<ItemEntity> itemEntities = new ArrayList<>();
        DB snappyDb = null;
        try {
            snappyDb = DBFactory.open(context, "newsdb", new Kryo());
            KeyIterator it = snappyDb.allKeysIterator();
            while (it.hasNext()) {
                for (String key : it.next(50)) {
                    itemEntities.add(snappyDb.get(key, ItemEntity.class));
                }
            }
        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            if (snappyDb != null)
                try {
                    snappyDb.close();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
        }
        return itemEntities;
    }

    @Override
    public boolean updateItem(ItemEntity itemEntity) {
        DB snappyDb = null;
        try {
            snappyDb = DBFactory.open(context, "newsdb", new Kryo());
            if (snappyDb.exists(itemEntity.getLink()))
                snappyDb.put(itemEntity.getLink(), itemEntity);
            return true;
        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            if (snappyDb != null)
                try {
                    snappyDb.close();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    @Override
    public List<ItemEntity> getFavoritesItems() {
        List<ItemEntity> itemEntities = new ArrayList<>();
        DB snappyDb = null;
        try {
            snappyDb = DBFactory.open(context, "newsdb", new Kryo());
            KeyIterator it = snappyDb.allKeysIterator();
            while (it.hasNext()) {
                for (String key : it.next(50)) {
                    if (snappyDb.get(key, ItemEntity.class).isFavorites())
                        itemEntities.add(snappyDb.get(key, ItemEntity.class));
                }
            }
        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            if (snappyDb != null)
                try {
                    snappyDb.close();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
        }
        return itemEntities;
    }

    @Override
    public boolean saveHtmlPage(String html, String url) {
        String[] subs = url.split("/");
        String fileName = subs[subs.length - 1].substring(0, subs[subs.length-1].indexOf('?'));
        Log.e("file",fileName);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(html);
            outputStreamWriter.close();
            return true;
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
    }

    @Override
    public ItemEntity getItem(String url) {
        ItemEntity itemEntity = null;
        DB snappyDb = null;
        try {
            snappyDb = DBFactory.open(context, "newsdb", new Kryo());

            itemEntity = snappyDb.get(url, ItemEntity.class);
        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            if (snappyDb != null)
                try {
                    snappyDb.close();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
        }
        return itemEntity;
    }

    @Override
    public boolean removeFile(String url) {
        String[] subs = url.split("/");
        String fileName = subs[subs.length - 1].substring(0, subs[subs.length-1].indexOf('?'));
        File file = context.getFileStreamPath(fileName);
        return file.delete();
    }

    @Override
    public File getSavedFile(String url) {
        String[] subs = url.split("/");
        String fileName = subs[subs.length - 1].substring(0, subs[subs.length-1].indexOf('?'));
        return context.getFileStreamPath(fileName);
    }
}
