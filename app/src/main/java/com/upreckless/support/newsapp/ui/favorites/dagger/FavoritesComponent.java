package com.upreckless.support.newsapp.ui.favorites.dagger;

import com.upreckless.support.newsapp.ui.favorites.Favorites;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 14.05.2017. !
 */
@FavoritesScope
@Subcomponent(modules = FavoritesModule.class)
public interface FavoritesComponent {
    void inject(Favorites favorites);
}
