package com.handpoint.api.shared.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class i18n {

    private static String DEFAULT_LANGUAGE = "en";
    private static String DEFAULT_COUNTRY =  "US";
    private static i18n INSTANCE;
    private Locale currentLocale;
    private Map<Locale, ResourceBundle> messages;
    private List<Locale> supportedLocales;

    public static Locale DEFAULT_LOCALE = new Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY);

    private i18n() {
        currentLocale = DEFAULT_LOCALE;

        // Preferred languages at the end of the list
        supportedLocales = new ArrayList<Locale>();
        for (SupportedLocales loc : SupportedLocales.values()) {
            supportedLocales.add(loc.getLocale());
        }
    }

    public static i18n getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new i18n();
        }
        return INSTANCE;
    }

    /**
     * Set the current language
     * @param langCode ISO 639 alpha-2 or alpha-3 language code
     */
    public static void setLanguage(String langCode) {
        Locale closestLocale = getInstance().getClosestMatch(new Locale(langCode));
        getInstance().setLocale(closestLocale);
    }

    /**
     * Set the current locale
     * @param locale
     */
    public static void setLocale(Locale locale) {
        Locale closestLocale = getInstance().getClosestMatch(locale);
        getInstance().currentLocale = closestLocale;
    }

    /**
     * Get the current locale
     * @return
     */
    public static Locale getLocale() {
        return getInstance().currentLocale;
    }

    /**
     * Get the current language
     * @return
     */
    public static String getLanguage() {
        return getInstance().currentLocale.getLanguage();
    }

    /**
     * Translate a key using the current locale
     * @param key Text key
     * @return
     */
    public static String translate(String key) {
        return getInstance().translateString(key);
    }

    /**
     * Translate a key using the specified language
     * @param key Text key
     * @param language preferred language
     * @return
     */
    public static String translate(String key, String language) {
        Locale closestLocale = getInstance().getClosestMatch(new Locale(language));
        return getInstance().translateString(key, closestLocale);
    }

    /**
     * Translate a key using the specified languages
     * @param key Text key
     * @param languages preferred languages list
     * @return
     */
    public static String translate(String key, List<String> languages) {
        Locale closestLocale = getInstance().getClosestMatch(languages);
        return getInstance().translateString(key, closestLocale);
    }

    /**
     * Translate a key using the specified locale
     * @param key Text key
     * @param locale preferred locale
     * @return
     */
    public static String translate(String key, Locale locale) {
        Locale closestLocale = getInstance().getClosestMatch(locale);
        return getInstance().translateString(key, closestLocale);
    }

    /**
     *
     * @param key Text key
     * @param arguments Compound message arguments
     * @return
     */
    public static String translateC(String key, Object[] arguments) {
        return getInstance().translateString(key, getInstance().currentLocale, arguments);
    }

    /**
     * Translate a key with a compound message using the specified list of preferred languages
     * @param key Text key
     * @param languages ordered list of preferred languages
     * @param arguments Compound message arguments
     * @return
     */
    public static String translateC(String key, List<String> languages, Object[] arguments) {
        Locale closestLocale = getInstance().getClosestMatch(languages);
        return getInstance().translateString(key, closestLocale, arguments);
    }

    /**
     * Translate a key with a compound message using the specified language
     * @param key Text key
     * @param language preferred language
     * @param arguments Compound message arguments
     * @return
     */
    public static String translateC(String key, String language, Object[] arguments) {
        Locale closestLocale = getInstance().getClosestMatch(new Locale(language));
        return getInstance().translateString(key, closestLocale, arguments);
    }

    /**
     * Translate a free text. To get the key just replace spaces by underscores and transform to upper case
     * @param text the free text
     * @return
     */
    public static String translateText(String text) {
        String result = getInstance().translate(text.replaceAll(" ", "_").replaceAll("-", "_").toUpperCase());
        if (result.equals("")) {
            return text;
        } else {
            return result;
        }
    }

    /**
     * Format a date using current locale
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return getInstance().formatDate(date, DateFormat.SHORT);
    }

    /**
     * Format a date using current locale and Specified format
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, int format) {
        DateFormat df = DateFormat.getDateInstance(format, getInstance().currentLocale);
        return df.format(date);
    }

    /**
     * Format a time using current locale
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        return getInstance().formatTime(date, DateFormat.SHORT);
    }

    /**
     * Format a time using current locale and Specified format
     * @param date
     * @param format
     * @return
     */
    public static String formatTime(Date date, int format) {
        DateFormat df = DateFormat.getTimeInstance(format, getInstance().currentLocale);
        return df.format(date);
    }

    /**
     * Format a number using current locale
     * @param number
     * @return
     */
    public static String formatNumber(double number) {
        NumberFormat nf = NumberFormat.getInstance(getInstance().currentLocale);
        return nf.format(number);
    }

    /**
     * Format a currency using current locale
     * @param amount the amount in the minor unit of currency
     * @return
     */
    public static String formatCurrency(String amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(getInstance().currentLocale);
        return getInstance().formatCurrency(amount, currencyFormatter.getCurrency().getDefaultFractionDigits());
    }

    public static String formatCurrency(BigInteger amount, com.handpoint.api.shared.Currency currency) {
        String amnt = (amount == null) ? "0" : amount.toString();
        if (currency == null) {
            return formatCurrency(amnt);
        } else {
            return formatCurrency(amnt, currency.getFractionDigits());
        }
    }

    public static String formatCurrency(String amount, int fractionDigits) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(getInstance().currentLocale);
        // Transform to double
        Double dAmt = (new Double(amount));
        if (fractionDigits > 0) {
            int multiplier = (int) Math.pow(10, fractionDigits);
            dAmt = dAmt / multiplier;
        }
        return currencyFormatter.format(dAmt);
    }

    /**
     * Format a currency using current locale. The currency code will be used instead of the symbol
     * @param amount the amount in the minor unit of currency
     * @return
     */
    public static String formatCurrencyCode(String amount) {
        Currency currency = Currency.getInstance(getInstance().currentLocale);
        return getInstance().formatCurrencyCode(amount, currency.getCurrencyCode());
    }

    /**
     * Format a currency using current locale and specified currency code.
     * @param amount the amount in the minor unit of currency
     * @param currencyCode ISO 4217 currency code
     * @return
     */
    public static String formatCurrencyCode(String amount, String currencyCode) {
        Currency currency = Currency.getInstance(getInstance().currentLocale);
        Currency customerCurrencyCode = Currency.getInstance(currencyCode);
        String localeSymbol = currency.getSymbol(getInstance().currentLocale);
        String localeCurrencyCode = currency.getCurrencyCode();
        int fractionDigits = customerCurrencyCode.getDefaultFractionDigits();
        String strAmount = getInstance().formatCurrency(amount, fractionDigits)
                .replace(localeSymbol, localeCurrencyCode)
                .replace(localeCurrencyCode, currencyCode);
        return getInstance().formatLeadingCurrency(strAmount);
    }

    /**
     * Format a currency using current locale and specified currency code.
     * @param amount the amount (double)
     * @param currencyCode ISO 4217 currency code
     * @return
     */
    public static String formatDoubleCurrency(double amount, String currencyCode) {
        Currency customerCurrencyCode = Currency.getInstance(currencyCode);
        int fractionDigits = customerCurrencyCode.getDefaultFractionDigits();
        int digitsToAdd = fractionDigits - getInstance().getDecimalDigits(amount);
        String sAmount;
        if (fractionDigits == 0) {
            sAmount = getInstance().getWholeNumberPart(amount);
        } else {
            sAmount = String.valueOf(amount).replace(".", "").replace(",", "");
        }

        int iAmount = Integer.valueOf(sAmount);
        if (digitsToAdd > 0) {
            iAmount *= Math.pow(10, digitsToAdd);
        }

        return getInstance().formatCurrencyCode(String.valueOf(iAmount), currencyCode);
    }

    public static Locale stringToLocale(String localeStr) {
        String l = null;
        String c = null;

        if (localeStr != null) {
            StringTokenizer tempStringTokenizer = new StringTokenizer(localeStr,"_- ");
            if (tempStringTokenizer.hasMoreTokens()) {
                l = tempStringTokenizer.nextToken();
            }
            if (tempStringTokenizer.hasMoreTokens()) {
                c = tempStringTokenizer.nextToken();
            }
        }

        if (l == null && c == null) {
            return new Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY);
        } else if (c == null) {
            return getInstance().getClosestMatch(new Locale(l));
        } else {
            return new Locale(l.toLowerCase(), c.toUpperCase());
        }
    }

    private String translateString(String key) {
        return translateString(key, currentLocale);
    }

    private String translateString(String key, Locale locale) {
        // Get resource bundle
        ResourceBundle localeMessages = messages.get(locale);

        if (localeMessages.containsKey(key)) {
            return localeMessages.getString(key);
        } else if (messages.get(DEFAULT_LOCALE).containsKey(key)) {
            return messages.get(DEFAULT_LOCALE).getString(key);
        } else {
            return "";
        }

    }

    private String translateString(String key, Locale locale, Object[] messageArguments) {
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(currentLocale);
        String template = translateString(key, locale);
        formatter.applyPattern(template);
        return formatter.format(messageArguments);
    }

    /**
     * Get the closest allowed locales for a given locale
     * NOTE: there is a better way to do this with Locale.lookup, but API Level 26 is required and we are currently
     * using 22
     * @param locale
     * @return
     */
    private Locale getClosestMatch(Locale locale) {
        Locale closest = null;

        for (Locale lc : supportedLocales) {
            if (lc.equals(locale)) {
                return lc;
            } else if (lc.getLanguage().equals(locale.getLanguage())) {
                closest = lc;
            }
        }
        return (closest != null) ? closest : DEFAULT_LOCALE;
    }

    /**
     * Return the closest supported locale for a list of preferred languages
     * @param languages
     * @return
     */
    private Locale getClosestMatch(List<String> languages) {
        Locale closest = null;

        for (String lang : languages) {
            for (Locale lc : supportedLocales) {
                if (lang.equals(lc.getLanguage())) {
                    return lc;
                }
            }
        }

        return DEFAULT_LOCALE;
    }

    /**
     * Normalize leading currency code format adding a blank space after the code
     * @param currencyAmount
     * @return
     */
    private String formatLeadingCurrency(String currencyAmount) {
        Pattern pattern = Pattern.compile("^(\\D{2,3}\\s*)(.*)");
        Matcher matcher = pattern.matcher(currencyAmount);
        if (matcher.matches()) {
            String currencyCode = matcher.group(1);
            return currencyAmount.replace(currencyCode, currencyCode.trim() + " ");
        } else {
            return currencyAmount;
        }
    }

    /**
     * Get the number of decimal digits of an amount [double]
     * @param amount [double]
     * @return number of decimal digits
     */
    public static int getDecimalDigits(double amount) {
        String[] parts = String.valueOf(amount).split("\\.");
        if (parts.length > 1) {
            if (parts[1].equals("0")) {
                return 1;
            } else {
                return parts[1].length();
            }
        } else {
            return 0;
        }
    }

    /**
     * Get the whole number part of an amount
     * @param amount [double]
     * @return whole number part
     */
    public static String getWholeNumberPart(double amount) {
        String[] parts = String.valueOf(amount).split("\\.");
        if (parts.length > 0) {
            return parts[0];
        } else {
            return "0";
        }
    }

}
