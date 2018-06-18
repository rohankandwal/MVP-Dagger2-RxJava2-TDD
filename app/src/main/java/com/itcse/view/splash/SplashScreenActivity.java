package com.itcse.view.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itcse.R;
import com.itcse.data.network.model.TabDataResponse;
import com.itcse.utils.Constants;
import com.itcse.view.home.HomeScreenActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Entry class of the app, first class to be called.
 */
public class SplashScreenActivity extends AppCompatActivity implements HasActivityInjector, SplashScreenContract.View {

    @Inject
    DispatchingAndroidInjector<Activity> activityAndroidInjector;
    @Inject
    SplashScreenPresenter presenter;

    @BindView(R.id.tvProgress)
    TextView tvProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting SplashScreen
        AndroidInjection.inject(this);
        // Making activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);
        presenter.getTabData();
    }

    @Override
    protected void onStop() {
        presenter.destroy();
        super.onStop();
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityAndroidInjector;
    }

    @Override
    public void setTabData(@NonNull List<TabDataResponse> tabData) {
        Timber.d(tabData.toString());
        openHomeScreenActivity(new Gson().toJson(tabData));
    }

    @Override
    public void noTabDataFound() {
        openHomeScreenActivity(null);
    }

    @Override
    public void showProgress(boolean showProgress) {
        if (showProgress) {
            tvProgress.setText(getString(R.string.loading));
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
     * @param message String containing message to be displayed to the user
     */
    private void showToast(@NonNull final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Function to open {@link HomeScreenActivity} and pass data received from server.
     * @param data String received from server
     */
    private void openHomeScreenActivity(@Nullable final String data) {
        final Intent intent = new Intent(this, HomeScreenActivity.class);
        if (!TextUtils.isEmpty(data)) {
            intent.putExtra(Constants.DATA, data);
        }
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.tvProgress})
    void onClick(final View view) {
        switch (view.getId()) {
            case R.id.tvProgress:
                // Only retry in case of TextView showing "Retry" String
                if (tvProgress.getText().equals(getString(R.string.retry))) {
                    presenter.getTabData();
                }
                break;
            default:
                break;
        }
    }
}
