package com.upreckless.support.newsapp.ui.webnews;

import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.domain.repository.NewsRepository;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.web.WebActivityRouter;

import javax.inject.Inject;

/**
 * Created by @mistreckless on 13.05.2017. !
 */

class WebNewsPresenter extends BasePresenter<WebNewsView, WebActivityRouter> {

    private NewsRepository newsRepository;

    @Inject
    WebNewsPresenter(NewsRepository newsRepository) {
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
        Injector.getInstance().clearWebNewsComponent();
    }

    void create(String url, boolean isFavorites) {
        if (!isFavorites)
            getView().init(url);
        else getView().init(newsRepository.getSavedFile(url));
    }

    void progressChanged(int progress) {
        if (getView() != null) {
            getView().setProgress(progress);
            getView().setProgressVisibility(progress != 100);
        }
    }
}
