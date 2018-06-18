package com.itcse.di;

import com.itcse.view.home.child_fragment.ChildContract;
import com.itcse.view.home.child_fragment.ChildPresenter;
import com.itcse.view.splash.SplashScreenContract;
import com.itcse.view.splash.SplashScreenPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Module for injecting presenters into the activity.
 */
@Module
abstract class PresenterModule {

    @Binds
    @PerActivity
    abstract SplashScreenContract.Presenter providesSplashScreenPresenter(SplashScreenPresenter presenter);

    @Binds
    @PerActivity
    abstract ChildContract.Presenter providesChildPresenter(ChildPresenter presenter);
}
