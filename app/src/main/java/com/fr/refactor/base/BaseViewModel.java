package com.fr.refactor.base;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.fr.refactor.utils.network.NetworkUtils;

import java.lang.ref.WeakReference;

public abstract class BaseViewModel<N> extends AndroidViewModel {

    private WeakReference<N> mNavigator;

//    private WeakReference<B> bindClass;
//
//    public B getBindClass() {
//        return bindClass.get();
//    }
//
//    public void setBindClass(B bindClass) {
//        this.bindClass = new WeakReference<>(bindClass);
//    }

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    protected boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplication());
    }


    public String getVersionName() {
        PackageManager packageManager = getApplication().getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(getApplication().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
