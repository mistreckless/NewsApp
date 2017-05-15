package com.upreckless.support.newsapp.ui.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.upreckless.support.newsapp.R;
import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.ui.BaseFragment;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.Layout;
import com.upreckless.support.newsapp.ui.favorites.dagger.FavoritesModule;
import com.upreckless.support.newsapp.ui.news.adapter.NewsAdapter;
import com.upreckless.support.newsapp.ui.news.model.ItemView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by @mistreckless on 14.05.2017. !
 */
@Layout(id= R.layout.fragment_favorites)
public class Favorites extends BaseFragment implements FavoritesView {
    @Inject
    FavoritesPresenter presenter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private NewsAdapter newsAdapter;

    public static Favorites newInstance(){
        return new Favorites();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsAdapter=new NewsAdapter(presenter::newsClicked);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void setItemList(List<ItemView> itemList) {
        newsAdapter.setItems(itemList);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    protected Toolbar getToolbar() {
        toolbar.setTitle(R.string.favorites);
        return toolbar;
    }

    @Override
    protected Object getRouter() {
        return getActivity();
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plusFavoritesComponent(new FavoritesModule()).inject(this);
    }
}
