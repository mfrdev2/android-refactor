package com.fr.refactor.di.mudule.appmodule;

import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class RetrofitModule {
 //   private static final String GAS_MONKEY_BASE_URL = "http://192.168.30.123:8801/";   //DEV-SERVER
    private static final String GAS_MONKEY_BASE_URL = "https://testapi-k8s.oss.net.bd/gas-monkey/"; //BA-UAT
 //    private static final String GAS_MONKEY_BASE_URL = "https://api-uat.gasmonkeybd.com/"; //GM-UAT
  //  private static final String GAS_MONKEY_BASE_URL = "https://api.gasmonkeybd.com/";   //LIVE
    private static final String ROUTE_SERVICE_BASE_URL = "https://api.openrouteservice.org";
    private static final String ADDRESS_SERVICE_BASE_URL = "https://nominatim.openstreetmap.org";



    @Singleton
    @Provides
    @Named("ret_for_route")
    public Retrofit provideRetrofitInstance(){
        return new Retrofit.Builder()
                .client(getRequestHeader())
                .baseUrl(ROUTE_SERVICE_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    @Named("ret_for_address")
    public Retrofit provideAddressRetrofitInstance(){
        return new Retrofit.Builder()
                .client(getRequestHeader())
                .baseUrl(ADDRESS_SERVICE_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    @Named("ret_for_auth_common")
    public Retrofit provideCommonRetrofitInstance(){
        return new Retrofit.Builder()
                .client(getRequestHeader())
                .baseUrl(GAS_MONKEY_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    @Named("ret_for_profile")
    public Retrofit provideProfileRetrofitInstance(){  //for token request
        return new Retrofit.Builder()
                .client(getRequestHeaderForProfile())
                .baseUrl(GAS_MONKEY_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient getRequestHeader() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message
                -> Timber.i("OkHttp:\n %s",message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(33, TimeUnit.SECONDS)
                .writeTimeout(33, TimeUnit.SECONDS)
                .readTimeout(33, TimeUnit.SECONDS)
                .build();
    }


    private static OkHttpClient getRequestHeaderForProfile() {
        Interceptor headerInterceptor = chain -> {
          //  TokenAuthenticator tokenAuthenticator = new TokenAuthenticator();
          //  tokenAuthenticator.callRefreshTokenAPI();
            Request newRequest = chain.request().newBuilder()
                 //   .addHeader("Authorization", "Bearer " + PreferanceProvider.getTokenStringValue(MyApplication.getInstance()))
                    .build();
            return chain.proceed(newRequest);
        };

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message
                -> Timber.i("OkHttp:\n %s", message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);



        return new OkHttpClient.Builder()
               // .authenticator(new TokenAuthenticator())
                .addInterceptor(headerInterceptor)
                .addInterceptor(interceptor)
                .connectTimeout(33, TimeUnit.SECONDS)
                .writeTimeout(33, TimeUnit.SECONDS)
                .readTimeout(33, TimeUnit.SECONDS)
                .build();
    }
}
