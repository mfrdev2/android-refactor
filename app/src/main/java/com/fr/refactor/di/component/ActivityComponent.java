package com.fr.refactor.di.component;

import com.fr.refactor.di.mudule.activitymodule.ActivityModule;
import com.fr.refactor.di.scope.ActivityScope;
import com.fr.refactor.ui.mainactivity.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(MainActivity activity);
}
