package com.fr.refactor.data.preferances;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PreferanceProvider {
    private static final String CUSTOMER_SETTINGS = "CUSTOMER_SETTINGS";
    private static final String TOKEN_STRING_VALUE = "TOKEN_STRING_VALUE";
    private static final String REFRESH_TOKEN_STRING_VALUE = "REFRESH_TOKEN_STRING_VALUE";
    private static final String FCM_TOKEN_STRING_VALUE = "FCM_TOKEN_STRING_VALUE";
    private static final String ACCOUNT_DETAIL_VALUE = "ACCOUNT_DETAIL_VALUE";
    private static final String PLANNING_TYPE_VALUE = "PLANNING_TYPE_VALUE";
    private static final String COUNTRY_LIST_VALUE = "COUNTRY_LIST_VALUE";
    private static final String CITY_LIST_VALUE = "CITY_LIST_VALUE";
    private static final String LOCALE_VALUE = "LOCALE_VALUE";
    private static final String BUSINESS_CATEGORY = "BUSINESS_CATEGORY";
    private static final String IMAGE_FILE_NAME = "IMAGE_FILE_NAME";
    private static final String COVER_IMAGE_FILE_NAME = "COVER_IMAGE_FILE_NAME";
    private static final String CUSTOMER_INFO = "CUSTOMER_ID";

    private PreferanceProvider() {

    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(CUSTOMER_SETTINGS, Context.MODE_PRIVATE);
    }

    public static String getImageFileNameStringValue(Context context) {
        return getSharedPreferences(context).getString(IMAGE_FILE_NAME, null);
    }

    public static void setImageFileNameStringValue(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(IMAGE_FILE_NAME, newValue);
        editor.commit();
    }

    public static void setCoverImageFileNameStringValue(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(COVER_IMAGE_FILE_NAME, newValue);
        editor.commit();
    }

    public static String getTokenStringValue(Context context) {
        return getSharedPreferences(context).getString(TOKEN_STRING_VALUE, null);
    }

    public static void setTokenStringValue(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TOKEN_STRING_VALUE, newValue);
        editor.commit();
    }

    public static String getRefreshTokenStringValue(Context context) {
        return getSharedPreferences(context).getString(REFRESH_TOKEN_STRING_VALUE, null);
    }

    public static void setRefreshTokenStringValue(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(REFRESH_TOKEN_STRING_VALUE, newValue);
        editor.commit();
    }

    public static String getFCMTokenStringValue(Context context) {
        return getSharedPreferences(context).getString(FCM_TOKEN_STRING_VALUE, null);
    }

    public static void setFCMTokenStringValue(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(FCM_TOKEN_STRING_VALUE, newValue);
        editor.commit();
    }

    public static Account getAccountDetail(Context context) {
        String value = getSharedPreferences(context).getString(ACCOUNT_DETAIL_VALUE, null);
        if (value == null) {
            return null;
        } else {
            return new Gson().fromJson(value, Account.class);
        }
    }

    public static void setAccountDetail(Context context, Account account) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ACCOUNT_DETAIL_VALUE, new Gson().toJson(account));
        editor.commit();
    }





//    public static List<PlanningType> getPlanningTypeList(Context context) {
//        String value = getSharedPreferences(context).getString(PLANNING_TYPE_VALUE, null);
//        if (value == null) {
//            return new ArrayList<>();
//        } else {
//            Type type = new TypeToken<List<PlanningType>>() {
//            }.getType();
//            return new Gson().fromJson(value, type);
//        }
//    }
//
//    public static void setPlanningTypeList(Context context, List<PlanningType> list) {
//        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
//        editor.putString(PLANNING_TYPE_VALUE, new Gson().toJson(list));
//        editor.commit();
//    }
//
//    public static void setBusineeCategoryList(Context context, List<Category> list) {
//        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
//        editor.putString(BUSINESS_CATEGORY, new Gson().toJson(list));
//        editor.commit();
//    }
//
//    public static List<Country> getCountryList(Context context) {
//        String value = getSharedPreferences(context).getString(COUNTRY_LIST_VALUE, null);
//        if (value == null) {
//            return new ArrayList<>();
//        } else {
//            Type type = new TypeToken<List<Country>>() {
//            }.getType();
//            return new Gson().fromJson(value, type);
//        }
//    }
//
//    public static List<Category> getBusinessCategoryList(Context context) {
//        String value = getSharedPreferences(context).getString(BUSINESS_CATEGORY, null);
//        if (value == null) {
//            return new ArrayList<>();
//        } else {
//            Type type = new TypeToken<List<Category>>() {
//            }.getType();
//            return new Gson().fromJson(value, type);
//        }
//    }
//
//    public static void setCountryList(Context context, List<Country> list) {
//        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
//        editor.putString(COUNTRY_LIST_VALUE, new Gson().toJson(list));
//        editor.commit();
//    }
//
//    public static List<City> getCityList(Context context) {
//        String value = getSharedPreferences(context).getString(CITY_LIST_VALUE, null);
//        if (value == null) {
//            return new ArrayList<>();
//        } else {
//            Type type = new TypeToken<List<City>>() {
//            }.getType();
//            return new Gson().fromJson(value, type);
//        }
//    }
//
//    public static void setCityList(Context context, List<City> list) {
//        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
//        editor.putString(CITY_LIST_VALUE, new Gson().toJson(list));
//        editor.commit();
//    }

    public static String getLocaleValue(Context context) {
        return getSharedPreferences(context).getString(LOCALE_VALUE, "");
    }

    public static void setLocalValue(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(LOCALE_VALUE, newValue);
        editor.commit();
    }
}
