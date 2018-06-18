package com.itcse.view.splash;

import com.itcse.data.network.model.TabDataResponse;
import com.itcse.view.base.BasePresenter;
import com.itcse.view.base.BaseView;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Interface containing contracts for View and Presenter interfaces extended by
 * {@link SplashScreenActivity} & {@link SplashScreenPresenter} respectively.
 */
public interface SplashScreenContract {

    interface View extends BaseView {
        /**
         * Function to set tab data list JSON String.
         *
         * @param tabDataList List of {@link TabDataResponse} containing list of tabs
         */
        void setTabData(@NonNull final List<TabDataResponse> tabDataList);

        /**
         * Function called when no tab data is found.
         */
        void noTabDataFound();
    }

    interface Presenter extends BasePresenter {
        /**
         * Function to get list of latest tabs.
         */
        void getTabData();
    }
}
