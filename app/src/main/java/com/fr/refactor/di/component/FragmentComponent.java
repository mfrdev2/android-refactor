package com.fr.refactor.di.component;



import com.fr.refactor.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {

}
