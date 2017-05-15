package com.upreckless.support.newsapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by @mistreckless on 11.05.2017. !
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        getPresenter().setView(this);
        getPresenter().setRouter(this);
    }

    protected abstract BasePresenter getPresenter();


    protected abstract void inject();

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().setRouter(null);
        getPresenter().setView(null);
    }
}
