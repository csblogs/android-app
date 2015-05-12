package com.csblogs.csblogsandroid.di;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.csblogs.csblogsandroid.CSBlogsApp;
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
    private CSBlogsApp csBlogsApp;

    public CSBlogsModule(CSBlogsApp csBlogsApp)
    {
        this.csBlogsApp = csBlogsApp;
    }

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
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(CSBlogsApi.API_ENDPOINT_URL).setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson)).build();
        return restAdapter;
    }

    @Provides
    @Singleton
    public CSBlogsApi provideCSBlogsApi(RestAdapter restAdapter)
    {
        return restAdapter.create(CSBlogsApi.class);
    }

    @Provides
    @Singleton
    public ImageLoader provideImageLoader(RequestQueue requestQueue,ImageLoader.ImageCache imageCache)
    {
        ImageLoader imageLoader = new ImageLoader(requestQueue,imageCache);
        return imageLoader;
    }

    @Provides
    @Singleton
    public RequestQueue provideRequestQueue()
    {
        return Volley.newRequestQueue(csBlogsApp);
    }

    @Provides
    @Singleton
    public ImageLoader.ImageCache provideImageCache()
    {
        return new ImageLoader.ImageCache()
        {
            private LruCache<String,Bitmap> lruCache;

            {
                lruCache = new LruCache<>(15);
            }
            @Override
            public Bitmap getBitmap(String s)
            {
                return lruCache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap)
            {
                lruCache.put(s,bitmap);
            }
        };
    }
}
