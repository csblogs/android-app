package com.csblogs.csblogsandroid.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.csblogs.csblogsandroid.CSBlogsApp;
import com.csblogs.csblogsandroid.LatestBlogFeed;
import com.csblogs.csblogsandroid.R;
import com.csblogs.csblogsandroid.api.CSBlogsApi;
import com.csblogs.csblogsandroid.crates.BlogPostCrate;

import javax.inject.Inject;

public class LatestBlogsFragment extends Fragment
{
    public static final String TAG = "LATEST_BLOGS_FRAGMENT";

    @Inject CSBlogsApi api;
    @Inject BlogPostCrate blogPostCrate;

    @InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.card_recycler_view) RecyclerView blogPostRecyclerView;

    private LatestBlogFeed latestBlogFeed;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        CSBlogsApp.dependencies().inject(this);

        if(Build.VERSION.SDK_INT >= 21)
        {
            setExitTransition(new Slide(Gravity.BOTTOM));
            setEnterTransition(new Slide(Gravity.BOTTOM));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_latest_blogs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);

        latestBlogFeed = new LatestBlogFeed(swipeRefreshLayout,blogPostRecyclerView);
        if(savedInstanceState == null)
        {
            latestBlogFeed.fetchMoreBlogs();
        }
    }

    public void onSaveInstanceState(Bundle outState)
    {
        if(latestBlogFeed != null)
        {
            latestBlogFeed.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null)
        {
            if(latestBlogFeed != null)
            {
                latestBlogFeed.onRestoreInstanceState(savedInstanceState);
            }
        }
    }



}
