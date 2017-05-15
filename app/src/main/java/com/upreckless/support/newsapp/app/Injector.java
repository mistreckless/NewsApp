package com.upreckless.support.newsapp.app;

import com.upreckless.support.newsapp.ui.favorites.dagger.FavoritesComponent;
import com.upreckless.support.newsapp.ui.favorites.dagger.FavoritesModule;
import com.upreckless.support.newsapp.ui.launch.dagger.MainComponent;
import com.upreckless.support.newsapp.ui.launch.dagger.MainModule;
import com.upreckless.support.newsapp.ui.news.dagger.NewsComponent;
import com.upreckless.support.newsapp.ui.news.dagger.NewsModule;
import com.upreckless.support.newsapp.ui.splash.dagger.SplashComponent;
import com.upreckless.support.newsapp.ui.splash.dagger.SplashModule;
import com.upreckless.support.newsapp.ui.web.dagger.WebComponent;
import com.upreckless.support.newsapp.ui.web.dagger.WebModule;
import com.upreckless.support.newsapp.ui.webnews.dagger.WebNewsComponent;
import com.upreckless.support.newsapp.ui.webnews.dagger.WebNewsModule;

/**
 * Created by @mistreckless on 11.05.2017. !
 */

public class Injector {
    private static Injector instance;
    private MainComponent mainComponent;
    private SplashComponent splashComponent;
    private NewsComponent newsComponent;
    private WebComponent webComponent;
    private WebNewsComponent webNewsComponent;
    private FavoritesComponent favoritesComponent;

    private Injector() {
    }

    public static Injector getInstance() {
        if (instance == null) instance = new Injector();
        return instance;
    }

    public MainComponent plusMainComponent(MainModule mainModule) {
        if (mainComponent == null)
            mainComponent = App.getComponent().plus(mainModule);
        return mainComponent;
    }

    public void clearMainComponent() {
        mainComponent = null;
    }

    public SplashComponent plusSplashComponent(SplashModule splashModule) {
        if (splashComponent == null) splashComponent = mainComponent.plus(splashModule);
        return splashComponent;
    }

    public void clearSplashComponent() {
        splashComponent = null;
    }

    public NewsComponent plusNewsComponent(NewsModule newsModule){
        if (newsComponent==null) newsComponent=mainComponent.plus(newsModule);
        return newsComponent;
    }
    public void clearNewsComponent(){
        newsComponent=null;
    }

    public WebComponent plusWebComponent(WebModule webModule){
        if (webComponent==null) webComponent=App.getComponent().plus(webModule);
        return webComponent;
    }

    public void clearWebComponent() {
        webComponent=null;
    }

    public WebNewsComponent plusWebNewsComponent(WebNewsModule webNewsModule){
        if (webNewsComponent==null) webNewsComponent=webComponent.plus(webNewsModule);
        return webNewsComponent;
    }

    public void clearWebNewsComponent(){
        webNewsComponent=null;
    }

    public FavoritesComponent plusFavoritesComponent(FavoritesModule favoritesModule) {
        if (favoritesComponent==null) favoritesComponent=mainComponent.plus(favoritesModule);
        return favoritesComponent;
    }

    public void clearFavoritesComponent(){
        favoritesComponent=null;
    }
}
