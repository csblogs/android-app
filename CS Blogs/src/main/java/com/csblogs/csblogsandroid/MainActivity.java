package com.csblogs.csblogsandroid;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;

import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.csblogs.csblogsandroid.api.CSBlogsApi;
import com.csblogs.csblogsandroid.api.payloads.BlogPost;
import com.csblogs.csblogsandroid.api.payloads.BlogsResponse;
import com.csblogs.csblogsandroid.views.BlogPostCard;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
{
    private static final int BLOG_REQUEST_LIMIT = 10;

    @Inject CSBlogsApi api;

    @InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.card_recycler_view) RecyclerView blogPostRecyclerView;

    private int blogPage = 0;
    private List<BlogPost> blogPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CSBlogsApp csBlogsApp = (CSBlogsApp) getApplication();
        csBlogsApp.dependencies().inject(this);

        ButterKnife.inject(this);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        blogPostRecyclerView.setLayoutManager(gridLayoutManager);

        blogPostRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleCard = gridLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleCard == (blogPage * BLOG_REQUEST_LIMIT) - 2)
                {
                    fetchMoreBlogs();
                }
            }
        });

        blogPostRecyclerView.setAdapter(new RecyclerView.Adapter<BlogPostCardHolder>()
        {
            @Override
            public BlogPostCardHolder onCreateViewHolder(ViewGroup viewGroup, int itemType)
            {
                return new BlogPostCardHolder(new BlogPostCard(viewGroup.getContext()));
            }

            @Override
            public void onBindViewHolder(BlogPostCardHolder blogPostCardHolder, int pos)
            {
                blogPostCardHolder.bind(blogPosts.get(pos));
            }

            @Override
            public int getItemCount()
            {
                return blogPosts.size();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                blogPosts = new ArrayList<BlogPost>();
                blogPage = 0;
                blogPostRecyclerView.getAdapter().notifyDataSetChanged();
                fetchMoreBlogs();
            }
        });

        fetchMoreBlogs();
    }

    private BlogsResponse lastBlogsResponse;

    private void fetchMoreBlogs()
    {
        if(lastBlogsResponse != null && !lastBlogsResponse.getHasMore())
        {
            return;
        }

        blogPage++;
        api.getBlogs(blogPage, BLOG_REQUEST_LIMIT, new Callback<BlogsResponse>()
        {
            @Override
            public void success(BlogsResponse blogsResponse, Response response)
            {
                lastBlogsResponse = blogsResponse;
                blogPosts.addAll(blogsResponse.getBlogs());
                blogPostRecyclerView.getAdapter().notifyDataSetChanged();
                if(swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void failure(RetrofitError error)
            {
                if(swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    static class BlogPostCardHolder extends RecyclerView.ViewHolder
    {
        public BlogPostCardHolder(BlogPostCard blogPostCard)
        {
            super(blogPostCard);
        }

        protected void bind(BlogPost blogPost)
        {
            BlogPostCard blogPostCard = (BlogPostCard) this.itemView;
            blogPostCard.setBlogPost(blogPost);
        }
    }
}
