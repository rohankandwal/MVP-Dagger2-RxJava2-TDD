package com.itcse.view.home.child_fragment;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.itcse.R;
import com.itcse.data.network.ApiInterface;
import com.itcse.data.network.model.TabChildResponse;
import com.itcse.view.base.BaseView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Class for making calls to server on behalf of {@link ChildFragment}
 */
public class ChildPresenter implements ChildContract.Presenter {

    private ApiInterface apiInterface;
    private ChildContract.View view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ChildPresenter(@NonNull ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    @Override
    public void getItems(@NonNull String url) {
        view.showProgress(true);
        apiInterface.getTabChildData(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<TabChildResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<TabChildResponse> tabChildResponseList) {
                        if (tabChildResponseList == null || tabChildResponseList.size() == 0) {
                            view.noItemFound();
                        } else {
                            view.setItems(tabChildResponseList);
                        }
                        view.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!TextUtils.isEmpty(e.getMessage())) {
                            view.showError(e.getMessage());
                        } else {
                            view.showError(R.string.error_unknown);
                        }
                        view.showProgress(false);
                    }
                });
    }

    @Override
    public void onAttach(BaseView view) {
        this.view = (ChildContract.View) view;
    }

    @Override
    public void destroy() {
        compositeDisposable.dispose();
        view = null;
    }
}
