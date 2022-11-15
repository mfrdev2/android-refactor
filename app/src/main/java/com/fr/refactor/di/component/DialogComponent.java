package com.fr.refactor.di.component;



import com.fr.refactor.di.scope.DialogScope;

import dagger.Component;

@DialogScope
@Component(dependencies = AppComponent.class)
public interface DialogComponent {


}
