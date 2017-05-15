package com.upreckless.support.newsapp.domain.repository;

import android.content.SharedPreferences;

import com.upreckless.support.newsapp.util.Constance;

import javax.inject.Inject;

/**
 * Created by @mistreckless on 08.05.2017. !
 */

public class UserRepositoryImpl implements UserRepository {
    private SharedPreferences spf;

    @Inject
    public UserRepositoryImpl(SharedPreferences spf){
        this.spf=spf;
    }

    @Override
    public String getPhoneNumber() {
        return spf.getString(Constance.SharedPreferencesHolder.PHONE_NUMBER_KEY,null);
    }

    @Override
    public void putPhoneNumber(String phoneNumber) {
        spf.edit()
                .putString(Constance.SharedPreferencesHolder.PHONE_NUMBER_KEY,phoneNumber)
                .apply();
    }
}
