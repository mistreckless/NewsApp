package com.upreckless.support.newsapp.ui.news.dagger;

import com.upreckless.support.newsapp.ui.news.News;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 10.05.2017. !
 */
@NewsScope
@Subcomponent(modules = NewsModule.class)
public interface NewsComponent {
    void inject(News news);
}
