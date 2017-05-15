package com.upreckless.support.newsapp.ui.web;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.upreckless.support.newsapp.R;
import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.ui.BaseActivity;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.news.model.ItemView;
import com.upreckless.support.newsapp.ui.web.adapter.WebPageAdapter;
import com.upreckless.support.newsapp.ui.web.dagger.WebModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity implements WebActivityView, WebActivityRouter{

    @Inject
    WebPresenter presenter;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private WebPageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        presenter.create(savedInstanceState,getIntent().getStringExtra("link"), getIntent().getBooleanExtra("mode",false));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setNavigationIcon(ContextCompat.getDrawable(this, android.support.design.R.drawable.abc_ic_clear_material));
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_web,menu);
        ItemView itemView=pageAdapter.getItemView(viewPager.getCurrentItem());
        menu.findItem(R.id.add_favorites).setTitle(!itemView.isFavorites()?R.string.add_favorites:R.string.remove_favorites);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_share:
                presenter.shareClicked(pageAdapter.getItemView(viewPager.getCurrentItem()));
                return true;
            case R.id.add_favorites:
                presenter.addToFavoritesClicked(pageAdapter.getItemView(viewPager.getCurrentItem()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plusWebComponent(new WebModule()).inject(this);
    }

    @Override
    public void reInitViews() {
        viewPager.setAdapter(pageAdapter);
    }

    @Override
    public void init(List<ItemView> itemViews, int position, boolean isFavorites) {
        pageAdapter=new WebPageAdapter(getSupportFragmentManager(),itemViews, isFavorites);
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resetMenuItem(boolean b) {
        pageAdapter.getItemViews().get(viewPager.getCurrentItem()).setFavorites(b);
        invalidateOptionsMenu();

    }

    @Override
    public void showShareDialog(ItemView itemView) {
        ShareDialog shareDialog=new ShareDialog(this);
        if (ShareDialog.canShow(ShareLinkContent.class)){
            ShareLinkContent shareLinkContent=new ShareLinkContent.Builder()
                    .setContentTitle(itemView.getTitle())
                    .setContentUrl(Uri.parse(itemView.getLink()))
                    .setContentDescription(itemView.getDate())
                    .build();
            shareDialog.show(shareLinkContent);
        }
    }
}
