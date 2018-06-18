package com.itcse;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.itcse.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

/**
 * Application class instance.
 */
public class MyApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        // Dagger AppComponent for injection
        DaggerAppComponent.builder().create(this).inject(this);
        // Initialising Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }
}
