package com.upreckless.support.newsapp.ui.webnews;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.upreckless.support.newsapp.R;
import com.upreckless.support.newsapp.app.Injector;
import com.upreckless.support.newsapp.ui.BaseFragment;
import com.upreckless.support.newsapp.ui.BasePresenter;
import com.upreckless.support.newsapp.ui.Layout;
import com.upreckless.support.newsapp.ui.webnews.dagger.WebNewsModule;
import com.upreckless.support.newsapp.util.CustomWebClient;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by @mistreckless on 10.05.2017. !
 */
@Layout(id = R.layout.fragment_webnews)
public class WebNews extends BaseFragment implements WebNewsView {

    @Inject
    WebNewsPresenter presenter;

    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    public static WebNews newInstance(String url, boolean isFavorites) {
        Log.e("web", url);
        WebNews webNews = new WebNews();
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putBoolean("mode",isFavorites);
        webNews.setArguments(args);
        return webNews;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.create(getArguments().getString("url"), getArguments().getBoolean("mode"));
    }


    @Override
    protected Toolbar getToolbar() {
        return null;
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
        Injector.getInstance().plusWebNewsComponent(new WebNewsModule()).inject(this);
    }

    @Override
    public void init(String url) {
        setWebSettings();
        webView.loadUrl(url);
    }

    @Override
    public void init(File file) {
        setWebSettings();
        Log.e("filePath",file.getAbsolutePath());
        webView.loadUrl(file.getAbsolutePath());
    }

    private void setWebSettings(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebChromeClient(new CustomWebClient(presenter::progressChanged));
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setProgressVisibility(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setProgressVisibility(false);
            }

        });
    }

    @Override
    public void setProgress(int progress) {
        if (progressBar != null)
            progressBar.setProgress(progress);
    }

    @Override
    public void setProgressVisibility(boolean b) {
        if (progressBar != null)
            progressBar.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }


}
