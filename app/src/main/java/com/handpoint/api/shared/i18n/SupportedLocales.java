package com.handpoint.api.shared.i18n;

import java.util.Locale;

public enum SupportedLocales {
    en_CA("en_CA"),
    en_UK("en_GB"),
    en_US("en_US"),
    es_ES("es_ES"),
    fr_FR("fr_FR"),
    hr_HR("hr_HR"),
    is_IS("is_IS");

    private String localeStr;

    SupportedLocales(String localeStr){
        Locale locale = i18n.stringToLocale(localeStr);
        this.localeStr = getLocaleString(locale);
    }

    public Locale getLocale() {
        Locale locale = i18n.stringToLocale(this.localeStr);
        return locale;
    }

    public static SupportedLocales fromString(String loctr) {
        Locale locale = i18n.stringToLocale(loctr);
        String localeStr = getLocaleString(locale);
        for (SupportedLocales loc : SupportedLocales.values()) {
            if (loc.localeStr.equalsIgnoreCase(localeStr)) {
                return loc;
            }
        }
        return en_US;
    }

    public String toString() {
        return localeStr;
    }

    private static String getLocaleString(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();
    }

}
