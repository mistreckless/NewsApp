package com.upreckless.support.newsapp.ui.web.dagger;

import com.upreckless.support.newsapp.ui.web.WebActivity;
import com.upreckless.support.newsapp.ui.webnews.dagger.WebNewsComponent;
import com.upreckless.support.newsapp.ui.webnews.dagger.WebNewsModule;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 12.05.2017. !
 */
@WebScope
@Subcomponent(modules = WebModule.class)
public interface WebComponent {
    void inject(WebActivity webActivity);

    WebNewsComponent plus(WebNewsModule webNewsModule);
}
