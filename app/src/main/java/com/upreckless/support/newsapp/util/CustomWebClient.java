package com.upreckless.support.newsapp.util;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by @mistreckless on 13.05.2017. !
 */

public class CustomWebClient extends WebChromeClient {
    private WebClientListener listener;

    public CustomWebClient(WebClientListener listener){
        this.listener=listener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        listener.progressUpdate(newProgress);
        super.onProgressChanged(view, newProgress);
    }
}
