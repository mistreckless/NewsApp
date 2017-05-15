package com.upreckless.support.newsapp.domain;

import com.upreckless.support.newsapp.domain.data.Item;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by @mistreckless on 08.05.2017. !
 */

public interface RestApi {

    @GET("_/rss/_rss.html?subtype=1&category=2&city=21")
    Observable<List<Item>> getNews();

}
