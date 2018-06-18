package com.itcse.di;

import android.app.Application;

import com.itcse.MyApplication;
import com.itcse.view.home.HomeScreenActivity;
import com.itcse.view.home.child_fragment.ChildFragment;
import com.itcse.view.splash.SplashScreenActivity;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

/*
 * App module used to inject fragments, services and activities.
 */
@Module(includes = {AndroidInjectionModule.class, RepositoryModule.class, PresenterModule.class})
abstract class AppModule {

    @Binds
    @Singleton
    /*
     * Singleton annotation isn't necessary since Application instance is unique but is here for
     * convention. In general, providing Activity, Fragment, BroadcastReceiver, etc does not require
     * them to be scoped since they are the components being injected and their instance is unique.
     *
     * However, having a scope annotation makes the module easier to read. We wouldn't have to look
     * at what is being provided in order to understand its scope.*/
    abstract Application getApplication(MyApplication application);

    @PerActivity
    @ContributesAndroidInjector
    abstract SplashScreenActivity splashScreenActivityInjector();

    @PerActivity
    @ContributesAndroidInjector
    abstract HomeScreenActivity homeScreenActivityInjector();

    @ContributesAndroidInjector
    abstract ChildFragment homeScreenChildFragmentInjector();
}
