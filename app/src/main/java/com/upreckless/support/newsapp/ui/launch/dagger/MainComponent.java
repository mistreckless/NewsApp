package com.upreckless.support.newsapp.ui.launch.dagger;

import com.upreckless.support.newsapp.ui.favorites.dagger.FavoritesComponent;
import com.upreckless.support.newsapp.ui.favorites.dagger.FavoritesModule;
import com.upreckless.support.newsapp.ui.launch.MainActivity;
import com.upreckless.support.newsapp.ui.news.dagger.NewsComponent;
import com.upreckless.support.newsapp.ui.news.dagger.NewsModule;
import com.upreckless.support.newsapp.ui.splash.dagger.SplashComponent;
import com.upreckless.support.newsapp.ui.splash.dagger.SplashModule;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 08.05.2017. !
 */

@MainScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
    NewsComponent plus(NewsModule newsModule);
    SplashComponent plus(SplashModule splashModule);

    FavoritesComponent plus(FavoritesModule favoritesModule);
}
