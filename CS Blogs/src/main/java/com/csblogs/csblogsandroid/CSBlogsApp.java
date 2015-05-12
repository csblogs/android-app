package com.csblogs.csblogsandroid;

import android.app.Application;
import com.csblogs.csblogsandroid.api.CSBlogsApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class CSBlogsApp extends Application
{
    private static final String API_ENDPOINT_BASE = "http://api.csblogs.com/";
    private static final String API_VERSION_STRING ="v0.1";
    private static final String API_ENDPOINT_URL = API_ENDPOINT_BASE + API_VERSION_STRING;

    private static final String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Override
    public void onCreate()
    {
        super.onCreate();

        Gson gson = new GsonBuilder().setDateFormat(API_DATE_FORMAT).create();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_ENDPOINT_URL).setConverter(new GsonConverter(gson)).build();
        CSBlogsApi csBlogsApi = restAdapter.create(CSBlogsApi.class);
    }
}
