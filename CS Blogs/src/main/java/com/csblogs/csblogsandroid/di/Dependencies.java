package com.csblogs.csblogsandroid.di;

import com.csblogs.csblogsandroid.MainActivity;
import com.csblogs.csblogsandroid.api.CSBlogsApi;
import com.csblogs.csblogsandroid.views.BlogPostCard;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = CSBlogsModule.class)
public interface Dependencies
{
    CSBlogsApi csBlogsApi();
    void inject(MainActivity activity);
    void inject(BlogPostCard blogPostCard);
}
