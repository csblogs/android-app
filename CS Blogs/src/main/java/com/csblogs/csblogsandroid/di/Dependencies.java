package com.csblogs.csblogsandroid.di;

import com.csblogs.csblogsandroid.MainActivity;
import com.csblogs.csblogsandroid.fragments.BloggerFragment;
import com.csblogs.csblogsandroid.fragments.BloggersFragment;
import com.csblogs.csblogsandroid.fragments.LatestBlogsFragment;
import com.csblogs.csblogsandroid.views.BlogPostCard;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = CSBlogsModule.class)
public interface Dependencies
{
    void inject(MainActivity activity);
    void inject(BlogPostCard blogPostCard);
    void inject(BloggersFragment bloggersFragment);
    void inject(LatestBlogsFragment latestBlogsFragment);
    void inject(BloggerFragment bloggerFragment);
}
