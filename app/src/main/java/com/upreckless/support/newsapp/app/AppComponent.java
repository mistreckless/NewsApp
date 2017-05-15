package com.upreckless.support.newsapp.app;

import com.upreckless.support.newsapp.domain.DomainModule;
import com.upreckless.support.newsapp.ui.launch.dagger.MainComponent;
import com.upreckless.support.newsapp.ui.launch.dagger.MainModule;
import com.upreckless.support.newsapp.ui.web.dagger.WebComponent;
import com.upreckless.support.newsapp.ui.web.dagger.WebModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by @mistreckless on 08.05.2017. !
 */
@Singleton
@Component(modules = {AppModule.class, DomainModule.class})
public interface AppComponent {
    void inject(App app);
    MainComponent plus(MainModule mainModule);

    WebComponent plus(WebModule webModule);
}
