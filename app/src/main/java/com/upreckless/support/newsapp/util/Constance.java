package com.upreckless.support.newsapp.util;

/**
 * Created by @mistreckless on 08.05.2017. !
 */

public class Constance {

    public static class CountryCode {
        public static final String RU_CODE = "+7";
        public static final String KG_CODE = "+996";
        public static final String AM_CODE = "+374";
        public static final String BY_CODE = "+375";
        public static final String UA_CODE = "+380";

    }

    public static class URL{
        public static final String HOST="https://alfabank.ru/";
    }

    public static class SharedPreferencesHolder {
        public static final String PHONE_NUMBER_KEY = "key";
        public static final String PREFERENCES_KEY = "prefs";
    }

    public enum CountryName {
        RUSSIA, UKRAIN, KYRGYZSTAN, ARMENIA, BELORUSSIA, UNKNOWN
    }
}
