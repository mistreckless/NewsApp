package com.upreckless.support.newsapp.ui.splash;

import com.upreckless.support.newsapp.util.Constance;

/**
 * Created by @mistreckless on 11.05.2017. !
 */
public interface SplashView {

    void setGoBtnEnabled(boolean enabled);

    void showCountryThumb(Constance.CountryName countryName);
}
