package com.csblogs.csblogsandroid;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import com.csblogs.csblogsandroid.api.payloads.BlogsResponse;
import com.csblogs.csblogsandroid.crates.BlogPostCrate;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LatestBlogFeed extends BlogFeed
{
    private static final String FEED_TAG = "LATEST";

    public LatestBlogFeed(SwipeRefreshLayout swipeRefreshLayout, RecyclerView blogPostCardRecyclerview)
    {
        super(swipeRefreshLayout, blogPostCardRecyclerview, FEED_TAG);
    }

    @Override
    protected void fetchMoreBlogs(int pageNumber, int blogRequestLimit)
    {
        csBlogsApi.getBlogs(pageNumber, blogRequestLimit, new Callback<BlogsResponse>()
        {
            @Override
            public void success(BlogsResponse blogsResponse, Response response)
            {
                onBlogsFetched(blogsResponse.getBlogs());
            }

            @Override
            public void failure(RetrofitError error)
            {
                onFetchBlogsError(error);
            }
        });
    }
}
