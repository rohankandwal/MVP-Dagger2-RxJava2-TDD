package com.itcse.view.home.child_fragment;

import com.itcse.RxSchedulersOverrideRule;
import com.itcse.data.network.ApiInterface;
import com.itcse.data.network.model.TabChildResponse;

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


public class ChildPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Mock
    private ChildContract.View view;

    @Mock
    private ApiInterface apiInterface;

    private ChildPresenter presenter;

    @Before
    public void setup() {
        presenter = new ChildPresenter(apiInterface);
        presenter.onAttach(view);
    }

    @Test
    public void gotTabChildData() {
        final List<TabChildResponse> tabDataResponseList = Arrays.asList(new TabChildResponse(),
                new TabChildResponse());
        // Given
        Mockito.when(apiInterface.getTabChildData(Mockito.anyString()))
                .thenReturn(Single.just(tabDataResponseList));

        // When
        presenter.getItems(Mockito.anyString());

        // Then
        // Progress function should be called exactly twice, once to show and second to hide progress
        Mockito.verify(view, Mockito.times(2)).showProgress(Mockito.anyBoolean());

        // Checking if setTabData() function was called
        Mockito.verify(view).setItems(tabDataResponseList);
    }

    @Test
    public void noTabChildData() {
        final List<TabChildResponse> tabDataResponseList = new ArrayList<>();
        // Given
        Mockito.when(apiInterface.getTabChildData(Mockito.anyString()))
                .thenReturn(Single.just(tabDataResponseList));

        // When
        presenter.getItems(Mockito.anyString());

        // Then
        // Progress function should be called exactly twice, once to show and second to hide progress
        Mockito.verify(view, Mockito.times(2)).showProgress(Mockito.anyBoolean());

        // Making sure that noTabDataFound() function was called
        Mockito.verify(view).noItemFound();
    }

}