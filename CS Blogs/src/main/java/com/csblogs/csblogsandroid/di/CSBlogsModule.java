package com.csblogs.csblogsandroid.di;

import com.csblogs.csblogsandroid.api.CSBlogsApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import javax.inject.Singleton;

@Module
public class CSBlogsModule
{
    @Provides
    @Singleton
    public Gson provideGson()
    {
        Gson gson = new GsonBuilder().setDateFormat(CSBlogsApi.API_DATE_FORMAT).create();
        return gson;
    }

    @Provides
    @Singleton
    public RestAdapter provideRestAdapter(Gson gson)
    {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(CSBlogsApi.API_ENDPOINT_URL)
                .setConverter(new GsonConverter(gson)).build();
        return restAdapter;
    }

    @Provides
    @Singleton
    public CSBlogsApi provideCSBlogsApi(RestAdapter restAdapter)
    {
        return restAdapter.create(CSBlogsApi.class);
    }
}
