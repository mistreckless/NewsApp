package com.upreckless.support.newsapp.ui.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.upreckless.support.newsapp.R;
import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.ui.BaseFragment;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.Layout;
import com.upreckless.support.newsapp.ui.news.adapter.NewsAdapter;
import com.upreckless.support.newsapp.ui.news.dagger.NewsModule;
import com.upreckless.support.newsapp.ui.news.model.ItemView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by @mistreckless on 10.05.2017. !
 */
@Layout(id = R.layout.fragment_news)
public class News extends BaseFragment implements NewsView {
    @Inject
    NewsPresenter presenter;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private NewsAdapter newsAdapter;

    public static News newInstance() {
        return new News();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsAdapter = new NewsAdapter(presenter::itemClicked);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newsAdapter);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
                presenter.refresh();
            }, 2000);

        });
    }

    @Override
    protected Toolbar getToolbar() {
        toolbar.setTitle(R.string.news);
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
        Injector.getInstance().plusNewsComponent(new NewsModule()).inject(this);
    }

    @Override
    public void setItemList(List<ItemView> itemList) {
        newsAdapter.setItems(itemList);
        newsAdapter.notifyDataSetChanged();
    }
}
