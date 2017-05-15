package com.upreckless.support.newsapp.ui.launch;

import android.os.Bundle;

import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.domain.repository.UserRepository;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.launch.dagger.MainScope;

import javax.inject.Inject;

/**
 * Created by @mistreckless on 08.05.2017. !
 */
@MainScope
public class MainActivityPresenter extends BasePresenter<MainActivityView, MainActivityRouter> {
    private UserRepository userRepository;

    @Inject
    MainActivityPresenter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            if (userRepository.getPhoneNumber() == null)
                getView().initSplashScreen();
            else getView().initNormalScreen();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        Injector.getInstance().clearMainComponent();
    }


}
