package com.itcse.data.network;

import com.itcse.data.network.model.TabChildResponse;
import com.itcse.data.network.model.TabDataResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Interface containing all API calls made to server
 */
public interface ApiInterface {

    @GET("m-et/Android/json/master.json")
    Single<List<TabDataResponse>> getTabData();

    @GET("")
    Single<List<TabChildResponse>> getTabChildData(@Url String url);
}
