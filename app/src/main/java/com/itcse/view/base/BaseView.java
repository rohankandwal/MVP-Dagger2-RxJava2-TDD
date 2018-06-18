package com.itcse.view.base;

import android.support.annotation.StringRes;

import io.reactivex.annotations.NonNull;

/**
 * Interface to be extended by all View interfaces. Contains functions to be inherited by all views.
 */
public interface BaseView {

    /**
     * Function to show or hide progress
     *
     * @param showProgress true to show progress or hide progress
     */
    void showProgress(final boolean showProgress);

    /**
     * Function to pass error message to the views
     * @param message String containing error message
     */
    void showError(@NonNull final String message);

    /**
     * Function to pass error message to the views
     * @param errorMessageId Integer denoting String resource Id from strings.xml
     */
    void showError(@StringRes final int errorMessageId);
}
