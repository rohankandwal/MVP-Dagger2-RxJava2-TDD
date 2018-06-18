package com.itcse.view.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hold1.pagertabsindicator.PagerTabsIndicator;
import com.itcse.R;
import com.itcse.data.network.model.TabDataResponse;
import com.itcse.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class HomeScreenActivity extends AppCompatActivity implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjection;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjection;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    TextView fab;
    @BindView(R.id.vpHome)
    ViewPager vpHome;
    @BindView(R.id.tvEmptyData)
    TextView tvEmptyData;
    @BindView(R.id.tabs)
    PagerTabsIndicator tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.DATA)) {
            final List<TabDataResponse> tabDataResponseList = new Gson().fromJson(intent.getStringExtra(Constants.DATA),
                    new TypeToken<List<TabDataResponse>>() {
                    }.getType());
            if (tabDataResponseList != null) {
                vpHome.setAdapter(new HomeScreenAdapter(getSupportFragmentManager(), tabDataResponseList));
                vpHome.setOffscreenPageLimit(tabDataResponseList.size());
                tabStrip.setViewPager(vpHome);
            }
        }
        // Showing empty data text in case of no data passed from SplashScreenActivity
        if (vpHome.getAdapter() == null) {
            tvEmptyData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.fab})
    void onClick(final View view) {
        switch (view.getId()) {
            case R.id.fab:
                Snackbar.make(view, getString(R.string.some_action), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.action), null).show();
                break;
            default:
                break;
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjection;
    }

    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return fragmentInjection;
    }
}
