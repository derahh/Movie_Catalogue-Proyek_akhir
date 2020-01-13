package com.derahh.moviecatalogueretrofit.util;

import android.util.Log;

import java.util.Locale;

public class LanguageFormater {

    public static String checkCurrentLanguage() {
        Log.d("TAG", Locale.getDefault().getISO3Language());
        String currentLanguage = Locale.getDefault().getISO3Language();
        String language = "";
        if (currentLanguage.equalsIgnoreCase("ind")) {
            language = "id-ID";
        } else if (currentLanguage.equalsIgnoreCase("eng")) {
            language = "en-US";
        }

        return language;
    }
}
