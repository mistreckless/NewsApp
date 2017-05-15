package com.upreckless.support.newsapp.ui.favorites;


import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.domain.repository.NewsRepository;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.favorites.dagger.FavoritesScope;
import com.upreckless.support.newsapp.ui.launch.MainActivityRouter;
import com.upreckless.support.newsapp.ui.news.model.ItemView;
import com.upreckless.support.newsapp.util.Helper;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by @mistreckless on 14.05.2017. !
 */
@FavoritesScope
class FavoritesPresenter extends BasePresenter<FavoritesView, MainActivityRouter> {

    private NewsRepository newsRepository;
    private Disposable disposable;

    @Inject
    FavoritesPresenter(NewsRepository newsRepository) {
        this.newsRepository=newsRepository;
    }


    void newsClicked(ItemView itemView) {
        getRouter().navigateToWeb(itemView.getLink(),true);
    }

    @Override
    public void onStart() {
        if (disposable == null || disposable.isDisposed())
            disposable = Observable.just(newsRepository.getFavoritesItems())
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
        if (disposable!=null)
            disposable.dispose();
        Injector.getInstance().clearFavoritesComponent();
    }
}
