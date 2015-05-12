package com.csblogs.csblogsandroid;

import android.app.Application;
import com.csblogs.csblogsandroid.di.DaggerDependencies;
import com.csblogs.csblogsandroid.di.Dependencies;

public class CSBlogsApp extends Application
{
    private Dependencies dependencies;

    @Override
    public void onCreate()
    {
        super.onCreate();
        dependencies = DaggerDependencies.create();
    }

    public Dependencies dependencies()
    {
        return dependencies;
    }
}
