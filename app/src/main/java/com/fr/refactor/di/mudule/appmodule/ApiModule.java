package com.fr.refactor.di.mudule.appmodule;


import android.app.Application;
import android.content.Context;

import com.fr.refactor.data.remote.network.AddressApiService;
import com.fr.refactor.data.remote.network.AuthApiService;
import com.fr.refactor.data.remote.network.CommonApiService;
import com.fr.refactor.data.remote.network.ProfileService;
import com.fr.refactor.data.preferances.prefs.AppPreferencesHelper;
import com.fr.refactor.data.preferances.prefs.PreferencesHelper;
import com.fr.refactor.di.qualifier.PreferenceInfo;
import com.fr.refactor.utils.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Singleton
    @Provides
    public AuthApiService provideAuthApi(@Named("ret_for_auth_common") Retrofit retrofit) {
        return retrofit
                .create(AuthApiService.class);
    }

    @Singleton
    @Provides
    public CommonApiService provideCommonApi(@Named("ret_for_auth_common") Retrofit retrofit) {
        return retrofit
                .create(CommonApiService.class);
    }

    @Singleton
    @Provides
    public ProfileService provideProfileApi(@Named("ret_for_profile") Retrofit retrofit) {
        return retrofit
                .create(ProfileService.class);
    }


    @Singleton
    @Provides
    public AddressApiService provideAddressApi(@Named("ret_for_address") Retrofit retrofit) {
        return retrofit
                .create(AddressApiService.class);
    }


    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }


    /**
     * provide Gson
     *
     * @return Gson's instance
     */
    @Singleton
    @Provides
    public Gson provideGson() {
      return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @Singleton
    Context provideContext(Application myApp) {
        return myApp;
    }
}
