package com.itcse.view.home.child_fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itcse.R;
import com.itcse.data.network.model.TabChildResponse;
import com.itcse.utils.Constants;
import com.itcse.utils.GridSpacingItemDecoration;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.AndroidSupportInjection;

public class ChildFragment extends Fragment implements HasActivityInjector, ChildContract.View {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject
    ChildPresenter presenter;

    private Context context;

    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.rvData)
    RecyclerView rvData;

    private String url;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_child_home, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            // Getting the String from arguments bundle to load data
            url = getArguments().getString(Constants.DATA);
            if (!TextUtils.isEmpty(url)) {
                presenter.onAttach(this);
                presenter.getItems(url);
            }
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Perform injection here for M (API 23) due to deprecation of onAttach(*Activity*).
            AndroidSupportInjection.inject(this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Perform injection here for versions before M as onAttach(*Context*) did not yet exist
            AndroidSupportInjection.inject(this);
        }
    }

    @Override
    public void onDetach() {
        this.context = null;
        super.onDetach();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void setItems(@NonNull List<TabChildResponse> tabChildResponseList) {
        if (context != null) {
            if (rvData.getAdapter() == null) {
                rvData.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
                rvData.setAdapter(new ChildAdapter(tabChildResponseList));
                rvData.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));
            } else {
                ChildAdapter adapter = (ChildAdapter) rvData.getAdapter();
//                adapter.up
            }
        }
        tvProgress.setVisibility(View.GONE);
    }

    @Override
    public void noItemFound() {
        tvProgress.setText(getString(R.string.no_data));
    }

    @Override
    public void showProgress(boolean showProgress) {
        if (showProgress) {
            tvProgress.setText(getString(R.string.loading));
            tvProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError(@NonNull String message) {
        showToast(message);
        tvProgress.setText(getString(R.string.retry));
    }

    @Override
    public void showError(int errorMessageId) {
        showToast(getString(errorMessageId));
        tvProgress.setText(getString(R.string.retry));
    }

    /**
     * Function to show toast message to the user
     *
     * @param message String containing message to be displayed to the user
     */
    private void showToast(@NonNull final String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.tvProgress})
    void onClick(final View view) {
        switch (view.getId()) {
            case R.id.tvProgress:
                // Only retry in case of TextView showing "Retry" String
                if (tvProgress.getText().equals(getString(R.string.retry))) {
                    if (!TextUtils.isEmpty(url)) {
                        presenter.getItems(url);
                    }
                }
                break;
            default:
                break;
        }
    }
}
