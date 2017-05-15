package com.upreckless.support.newsapp.ui.splash.dagger;

import com.upreckless.support.newsapp.ui.splash.Splash;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 11.05.2017. !
 */
@SplashScope
@Subcomponent(modules = SplashModule.class)
public interface SplashComponent {
    void inject(Splash splash);
}
