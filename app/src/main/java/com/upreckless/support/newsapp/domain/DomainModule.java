package com.upreckless.support.newsapp.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.upreckless.support.newsapp.domain.repository.NewsRepository;
import com.upreckless.support.newsapp.domain.repository.NewsRepositoryImpl;
import com.upreckless.support.newsapp.domain.repository.UserRepository;
import com.upreckless.support.newsapp.domain.repository.UserRepositoryImpl;
import com.upreckless.support.newsapp.domain.service.NewsService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 08.05.2017. !
 */

@Singleton
@Module
public class DomainModule {

    @Singleton
    @Provides
    UserRepository provideUserRepository(SharedPreferences sharedPreferences) {
        return new UserRepositoryImpl(sharedPreferences);
    }

    @Singleton
    @Provides
    NewsRepository provideNewsRepository(Context context) {
        return new NewsRepositoryImpl(RestApiCreator.getRestApi(),context);
    }

    @Singleton
    @Provides
    NewsService provideNewsService(NewsRepository newsRepository){
        return new NewsService(newsRepository);
    }
}
