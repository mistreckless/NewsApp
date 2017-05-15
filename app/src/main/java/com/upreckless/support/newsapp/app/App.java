package com.upreckless.support.newsapp.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.upreckless.support.newsapp.domain.DomainModule;

/**
 * Created by @mistreckless on 08.05.2017. !
 */

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=buildComponent();
        appComponent.inject(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .domainModule(new DomainModule())
                .build();
    }

    public static AppComponent getComponent(){
        return appComponent;
    }
}
