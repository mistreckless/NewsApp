package com.upreckless.support.newsapp.ui.webnews.dagger;

import com.upreckless.support.newsapp.ui.webnews.WebNews;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 13.05.2017. !
 */
@WebNewsScope
@Subcomponent(modules = WebNewsModule.class)
public interface WebNewsComponent {
    void inject(WebNews webNews);
}
