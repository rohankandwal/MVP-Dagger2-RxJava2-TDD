package com.itcse.view.home.child_fragment;

import android.support.annotation.NonNull;

import com.itcse.data.network.model.TabChildResponse;
import com.itcse.view.base.BasePresenter;
import com.itcse.view.base.BaseView;

import java.util.List;

public interface ChildContract {

    interface View extends BaseView {
        /**
         * Function to set the list of items we got from server
         * @param tabChildResponseList Contains list of {@link TabChildResponse} we got from server
         */
        void setItems(@NonNull final List<TabChildResponse> tabChildResponseList);

        /**
         * Function called when no items found on server.
         */
        void noItemFound();

    }

    interface Presenter extends BasePresenter {
        /**
         * Function to get items for the list
         * @param url String containing url to load items
         */
        void getItems(@NonNull final String url);
    }
}
