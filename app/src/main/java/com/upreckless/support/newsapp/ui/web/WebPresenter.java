package com.upreckless.support.newsapp.ui.web;

import android.os.Bundle;

import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.domain.repository.NewsRepository;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.news.model.ItemView;
import com.upreckless.support.newsapp.ui.web.dagger.WebScope;
import com.upreckless.support.newsapp.util.Helper;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by @mistreckless on 12.05.2017. !
 */
@WebScope
class WebPresenter extends BasePresenter<WebActivityView, WebActivityRouter> {
    private NewsRepository newsRepository;

    @Inject
    WebPresenter(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        Injector.getInstance().clearWebComponent();
    }

    void create(Bundle savedInstanceState, String link, boolean isFavorites) {
        if (link == null) return;
        if (savedInstanceState != null)
            getView().reInitViews();
        else
            Observable.just(isFavorites ? newsRepository.getFavoritesItems() : newsRepository.getItems())
                    .map(Helper::convertItemEntityesToItemViews)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(itemViews -> {
                        int index = 0;
                        for (int i = 0; i < itemViews.size(); i++) {
                            if (itemViews.get(i).getLink().equals(link))
                                index = i;
                        }
                        if (getView() != null)
                            getView().init(itemViews, index,isFavorites);

                    }, Throwable::printStackTrace);


    }

    void addToFavoritesClicked(ItemView itemView) {
        if (!itemView.isFavorites())
            newsRepository.getHtmlPage(itemView.getLink())
                    .map(html -> newsRepository.saveHtmlPage(html, itemView.getLink()))
                    .flatMap(b -> b ? Observable.just(newsRepository.getItem(itemView.getLink()))
                            .map(itemEntity -> {
                                itemEntity.setFavorites(true);
                                return itemEntity;
                            })
                            .map(newsRepository::updateItem) : Observable.just(false))
                    .subscribe(b -> {
                        if (getView() != null) {
                            getView().showToast(b ? "success" : "failed");
                            if (b) getView().resetMenuItem(true);
                        }
                    }, Throwable::printStackTrace);
        else Observable.just(newsRepository.getItem(itemView.getLink()))
                .map(itemEntity -> {
                    itemEntity.setFavorites(false);
                    return itemEntity;
                })
                .doOnNext(itemEntity -> newsRepository.removeFile(itemEntity.getLink()))
                .map(newsRepository::updateItem)
                .subscribe(b -> {
                    if (getView() != null) {
                        getView().showToast(b ? "success" : "failed");
                        if (b) getView().resetMenuItem(false);
                    }
                }, Throwable::printStackTrace);
    }

    void shareClicked(ItemView itemView) {
        getView().showShareDialog(itemView);
    }
}
