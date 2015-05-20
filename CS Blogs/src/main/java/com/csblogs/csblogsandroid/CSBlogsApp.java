package com.csblogs.csblogsandroid;

import android.app.Application;
import com.csblogs.csblogsandroid.di.CSBlogsModule;
import com.csblogs.csblogsandroid.di.DaggerDependencies;
import com.csblogs.csblogsandroid.di.Dependencies;

public class CSBlogsApp extends Application
{
    private static Dependencies dependencies;

    @Override
    public void onCreate()
    {
        super.onCreate();
        dependencies = DaggerDependencies.builder().cSBlogsModule(new CSBlogsModule(this)).build();
    }

    public static Dependencies dependencies()
    {
        return dependencies;
    }
}
