package com.upreckless.support.newsapp.domain;

import com.upreckless.support.newsapp.util.Constance;
import com.upreckless.support.newsapp.util.NewsConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by @mistreckless on 08.05.2017. !
 */

public class RestApiCreator {

    public static RestApi getRestApi() {
        return retrofit().create(RestApi.class);
    }

    public static Retrofit retrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constance.URL.HOST)
                .addConverterFactory(new NewsConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient);
        return builder.build();
    }
}
