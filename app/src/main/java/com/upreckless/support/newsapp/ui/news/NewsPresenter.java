package com.upreckless.support.newsapp.ui.news;

import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.domain.repository.NewsRepository;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.launch.MainActivityRouter;
import com.upreckless.support.newsapp.ui.news.dagger.NewsScope;
import com.upreckless.support.newsapp.ui.news.model.ItemView;
import com.upreckless.support.newsapp.util.Helper;

import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by @mistreckless on 10.05.2017. !
 */
@NewsScope
class NewsPresenter extends BasePresenter<NewsView, MainActivityRouter> {
    private NewsRepository newsRepository;
    private Disposable disposable;

    @Inject
    NewsPresenter(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public void onStart() {
        if (disposable == null || disposable.isDisposed())
            disposable = Observable.just(newsRepository.getItems())
                            .map(Helper::convertItemEntityesToItemViews)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(items -> {
                                if (getView() != null)
                                    getView().setItemList(items);
                            }, Throwable::printStackTrace);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        Injector.getInstance().clearNewsComponent();
        if (disposable!=null)
            disposable.dispose();
    }

    void itemClicked(ItemView item) {
        getRouter().navigateToWeb(item.getLink(),false);
    }

    void refresh() {
        newsRepository.getNews()
                .map(Helper::convertListItemToItemEntity)
                .map(itemEntities -> {
                    Collections.reverse(itemEntities);
                    return itemEntities;
                })
                .map(newsRepository::saveItemList)
                .map(b->newsRepository.getItems())
                .map(Helper::convertItemEntityesToItemViews)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemViews -> {
                    if (getView()!=null)
                        getView().setItemList(itemViews);
                    disposable.dispose();
                }, Throwable::printStackTrace);
    }
}
