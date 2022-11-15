package com.fr.refactor.di.component;

import com.fr.refactor.di.mudule.notificationmodule.NotificationModule;
import com.fr.refactor.di.scope.ServiceScope;

import dagger.Component;

@ServiceScope
@Component(modules = NotificationModule.class, dependencies = AppComponent.class)
public interface ServiceComponent {

}
