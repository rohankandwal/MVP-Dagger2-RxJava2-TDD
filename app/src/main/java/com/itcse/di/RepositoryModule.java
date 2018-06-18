package com.itcse.di;

import com.itcse.data.network.ApiInterface;
import com.itcse.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module for injecting Retrofit and related Networking components
 */
@Module
class RepositoryModule {

    @Singleton
    @Provides
    OkHttpClient providesHTTPClient() {
        // Used for logging Retrofit calls, helpful in debugging
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // Adding logging interceptor
        builder.addInterceptor(loggingInterceptor);

        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(@NonNull final OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

    }

    @Singleton
    @Provides
    ApiInterface providesApiInterface(@NonNull Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

}
