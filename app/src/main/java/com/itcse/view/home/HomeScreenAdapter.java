package com.itcse.view.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itcse.data.network.model.TabDataResponse;
import com.itcse.utils.Constants;
import com.itcse.view.home.child_fragment.ChildFragment;

import java.util.List;

/**
 * Class used to load different tabs for Base response.
 */
public class HomeScreenAdapter extends FragmentPagerAdapter {

    private final List<TabDataResponse> tabDataResponseList;

    HomeScreenAdapter(@NonNull final FragmentManager fm,
                             @NonNull final List<TabDataResponse> tabDataResponseList) {
        super(fm);
        this.tabDataResponseList = tabDataResponseList;
    }

    @Override
    public Fragment getItem(int position) {
        final Bundle bundle = new Bundle();
        bundle.putString(Constants.DATA, tabDataResponseList.get(position).data);

        final ChildFragment fragment = new ChildFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return tabDataResponseList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabDataResponseList.get(position).name;
    }
}
