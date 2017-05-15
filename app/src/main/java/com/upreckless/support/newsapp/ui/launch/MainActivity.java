package com.upreckless.support.newsapp.ui.launch;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.upreckless.support.newsapp.R;
import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.domain.service.NewsService;
import com.upreckless.support.newsapp.ui.BaseActivity;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.about.About;
import com.upreckless.support.newsapp.ui.favorites.Favorites;
import com.upreckless.support.newsapp.ui.launch.dagger.MainModule;
import com.upreckless.support.newsapp.ui.news.News;
import com.upreckless.support.newsapp.ui.splash.Splash;
import com.upreckless.support.newsapp.ui.web.WebActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainActivityView, MainActivityRouter, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    MainActivityPresenter presenter;
    @Inject
    NewsService newsService;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.create(savedInstanceState);
    }

    @Override
    public void initSplashScreen() {
        Splash splash = Splash.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, splash, splash.getTag())
                .commitAllowingStateLoss();
    }

    @Override
    public void initNormalScreen() {
        newsService.onDestroy();
        newsService.onStartCommand(new Intent(), Service.START_FLAG_REDELIVERY, 0);
        ButterKnife.bind(this);
        navigationView.setNavigationItemSelectedListener(this);
        News news = News.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, news, news.getTag())
                .commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void setToolbarToDrawer(Toolbar toolbar, boolean isAddedToBackStack) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.mipmap.ic_menu_black_24dp));
            toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plusMainComponent(new MainModule()).inject(this);
    }

    @Override
    public void navigateToNews() {
        News news = News.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, news, news.getTag())
                .commit();
    }

    @Override
    public void navigateToWeb(String link, boolean isFavorites) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("link", link);
        intent.putExtra("mode", isFavorites);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_news:
                navigateToNews();
                return true;
            case R.id.nav_favorites:
                navigateToFavorites();
                return true;
            case R.id.nav_about:
                navigateToAbout();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void navigateToAbout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new About())
                .commit();
    }

    @Override
    public void navigateToFavorites() {
        Favorites favorites = Favorites.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, favorites)
                .commit();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
