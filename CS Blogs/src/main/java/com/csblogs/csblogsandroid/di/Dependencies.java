package com.csblogs.csblogsandroid.di;

import com.csblogs.csblogsandroid.MainActivity;
import com.csblogs.csblogsandroid.api.CSBlogsApi;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = CSBlogsModule.class)
public interface Dependencies
{
    CSBlogsApi csBlogsApi();
    void inject(MainActivity activity);
}
