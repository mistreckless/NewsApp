package com.upreckless.support.newsapp.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.upreckless.support.newsapp.util.Constance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 08.05.2017. !
 */

@Singleton
@Module
class AppModule {
    private Context context;

    AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Context context){
        return context.getSharedPreferences(Constance.SharedPreferencesHolder.PREFERENCES_KEY,Context.MODE_PRIVATE);
    }
}
