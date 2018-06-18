package com.itcse.view.splash;

import android.text.TextUtils;

import com.itcse.R;
import com.itcse.data.network.ApiInterface;
import com.itcse.data.network.model.TabDataResponse;
import com.itcse.view.base.BaseView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Class used for making server calls on behalf of {@link SplashScreenPresenter}
 */
public class SplashScreenPresenter implements SplashScreenContract.Presenter {

    private ApiInterface apiInterface;
    private SplashScreenContract.View view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    SplashScreenPresenter(@NonNull ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    @Override
    public void getTabData() {
        view.showProgress(true);
        apiInterface.getTabData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<TabDataResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<TabDataResponse> tabDataResponses) {
                        if (tabDataResponses == null || tabDataResponses.size() == 0) {
                            view.noTabDataFound();
                        } else {
                            view.setTabData(tabDataResponses);
                        }
                        view.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        final String errorMessage = e.getMessage();
                        if (!TextUtils.isEmpty(errorMessage)) {
                            view.showError(errorMessage);
                        } else {
                            view.showError(R.string.error_unknown);
                        }
                        view.showProgress(false);
                    }
                });
    }


    @Override
    public void onAttach(BaseView view) {
        this.view = (SplashScreenContract.View) view;
    }

    @Override
    public void destroy() {
        compositeDisposable.dispose();
        view = null;
    }
}
