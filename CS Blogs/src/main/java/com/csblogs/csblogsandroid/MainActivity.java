package com.csblogs.csblogsandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.csblogs.csblogsandroid.api.CSBlogsApi;

import javax.inject.Inject;


public class MainActivity extends ActionBarActivity
{
    @Inject CSBlogsApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        CSBlogsApp csBlogsApp = (CSBlogsApp) getApplication();
        csBlogsApp.dependencies().inject(this);

        setContentView(R.layout.activity_main);
    }
}
