package com.upreckless.support.newsapp.ui.launch;

/**
 * Created by @mistreckless on 11.05.2017. !
 */

public interface MainActivityRouter {
    void navigateToNews();

    void navigateToWeb(String link, boolean isOnline);

    void navigateToAbout();

    void navigateToFavorites();
}
