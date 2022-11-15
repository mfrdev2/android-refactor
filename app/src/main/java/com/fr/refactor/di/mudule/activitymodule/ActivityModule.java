package com.fr.refactor.di.mudule.activitymodule;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.fr.refactor.base.BaseActivity;
import com.fr.refactor.ui.mainactivity.ActivityMainViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }

    @Provides
    public ActivityMainViewModel provideMainActivityViewModel() {
        return new ViewModelProvider((ViewModelStoreOwner) activity).get(ActivityMainViewModel.class);
    }


}
