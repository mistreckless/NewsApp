package com.upreckless.support.newsapp.ui.splash;

import android.util.Log;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.domain.repository.NewsRepository;
import com.upreckless.support.newsapp.domain.repository.UserRepository;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.launch.MainActivityRouter;
import com.upreckless.support.newsapp.ui.splash.dagger.SplashScope;
import com.upreckless.support.newsapp.util.Constance;
import com.upreckless.support.newsapp.util.Helper;

import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by @mistreckless on 11.05.2017. !
 */
@SplashScope
class SplashPresenter extends BasePresenter<SplashView, MainActivityRouter> {
    private UserRepository userRepository;
    private NewsRepository newsRepository;
    private Disposable controlBtnDispose;

    @Inject
    SplashPresenter(UserRepository userRepository, NewsRepository newsRepository) {
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (controlBtnDispose != null)
            controlBtnDispose.dispose();
    }

    @Override
    public void onDestroy() {
        Injector.getInstance().clearSplashComponent();
    }

    void controlBtnGo(InitialValueObservable<CharSequence> phoneNumberObservable) {
        if (controlBtnDispose == null || controlBtnDispose.isDisposed())
            controlBtnDispose = Observable.combineLatest(loadNews(), listenPhoneNumberField(phoneNumberObservable),
                    (b1, b2) -> b1 && b2)
                    .subscribe(getView()::setGoBtnEnabled, Throwable::printStackTrace);
    }

    void btnGoClicked(String phoneNumber) {
        userRepository.putPhoneNumber(phoneNumber);
        if (controlBtnDispose != null)
            controlBtnDispose.dispose();
        getRouter().navigateToNews();
    }


    private Observable<Boolean> loadNews() {
        return Observable.just(newsRepository.getItems())
                .flatMap(itemEntities -> itemEntities.size() > 0 ? Observable.just(true) : newsRepository.getNews()
                        .observeOn(Schedulers.io())
                        .map(items -> {
                            Log.e("items", String.valueOf(items.size()) + " items size");
                            Collections.reverse(items);
                            return items;
                        })
                        .map(Helper::convertListItemToItemEntity)
                        .map(newsRepository::saveItemList))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Boolean> listenPhoneNumberField(Observable<CharSequence> phoneNumberObservable) {
        return phoneNumberObservable
                .map(String::valueOf)
                .filter(s -> !s.isEmpty())
                .doOnNext(number -> {
                    if (number.charAt(0) == '+' && number.length() > 1) {
                        if (number.contains(Constance.CountryCode.RU_CODE))
                            getView().showCountryThumb(Constance.CountryName.RUSSIA);
                        else if (number.contains(Constance.CountryCode.AM_CODE))
                            getView().showCountryThumb(Constance.CountryName.ARMENIA);
                        else if (number.contains(Constance.CountryCode.BY_CODE))
                            getView().showCountryThumb(Constance.CountryName.BELORUSSIA);
                        else if (number.contains(Constance.CountryCode.KG_CODE))
                            getView().showCountryThumb(Constance.CountryName.KYRGYZSTAN);
                        else if (number.contains(Constance.CountryCode.UA_CODE))
                            getView().showCountryThumb(Constance.CountryName.UKRAIN);
                        else getView().showCountryThumb(Constance.CountryName.UNKNOWN);

                    } else {
                        if (number.charAt(0) == '8')
                            getView().showCountryThumb(Constance.CountryName.RUSSIA);
                        else
                            getView().showCountryThumb(Constance.CountryName.UNKNOWN);
                    }
                })
                .map(s -> s.length() >= 11);
    }
}
