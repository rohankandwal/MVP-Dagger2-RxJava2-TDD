package com.itcse.view.splash;

import com.itcse.RxSchedulersOverrideRule;
import com.itcse.data.network.ApiInterface;
import com.itcse.data.network.model.TabDataResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;

public class SplashScreenPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Mock
    private SplashScreenContract.View view;

    @Mock
    private ApiInterface apiInterface;

    private SplashScreenPresenter presenter;

    @Before
    public void setup() {
        presenter = new SplashScreenPresenter(apiInterface);
        presenter.onAttach(view);
    }

    @Test
    public void gotTabData() {
        final List<TabDataResponse> tabDataResponseList = Arrays.asList(new TabDataResponse(),
                new TabDataResponse());
        // Given
        Mockito.when(apiInterface.getTabData())
                .thenReturn(Single.just(tabDataResponseList));

        // When
        presenter.getTabData();

        // Then
        // Progress function should be called exactly twice, once to show and second to hide progress
        Mockito.verify(view, Mockito.times(2)).showProgress(Mockito.anyBoolean());

        // Checking if setTabData() function was called
        Mockito.verify(view).setTabData(tabDataResponseList);
    }

    @Test
    public void noTabData() {
        final List<TabDataResponse> tabDataResponseList = new ArrayList<>();
        // Given
        Mockito.when(apiInterface.getTabData())
                .thenReturn(Single.just(tabDataResponseList));

        // When
        presenter.getTabData();

        // Then
        // Progress function should be called exactly twice, once to show and second to hide progress
        Mockito.verify(view, Mockito.times(2)).showProgress(Mockito.anyBoolean());

        // Making sure that noTabDataFound() function was called
        Mockito.verify(view).noTabDataFound();
    }

}