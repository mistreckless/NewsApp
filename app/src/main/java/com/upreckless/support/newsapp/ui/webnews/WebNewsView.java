package com.upreckless.support.newsapp.ui.webnews;

import java.io.File;

/**
 * Created by @mistreckless on 10.05.2017. !
 */
interface WebNewsView {

    void init(String url);

    void init(File file);

    void setProgress(int progress);

    void setProgressVisibility(boolean b);

}
