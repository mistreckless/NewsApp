package com.upreckless.support.newsapp.domain.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.upreckless.support.newsapp.domain.data.Item;
import com.upreckless.support.newsapp.domain.repository.NewsRepository;
import com.upreckless.support.newsapp.util.Helper;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsService extends Service {
    private Disposable disposable;
    private NewsRepository newsRepository;

    public NewsService() {
    }

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (newsRepository != null && (disposable == null || disposable.isDisposed())) {
            disposable = Observable.interval(10 * 60 * 1000, TimeUnit.MILLISECONDS)
                    .timeInterval()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(val -> newsRepository.getNews()
                            .zipWith(Observable.just(newsRepository.getItems()), (items, itemEntities) -> items.size() > itemEntities.size() ? items : new ArrayList<Item>())
                            .map(Helper::convertListItemToItemEntity)
                            .map(newsRepository::saveItemList)
                            .subscribe(val1->{},Throwable::printStackTrace));

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}
