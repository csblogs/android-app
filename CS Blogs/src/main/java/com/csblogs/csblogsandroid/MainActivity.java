package com.csblogs.csblogsandroid;

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
import java.util.List;


public class MainActivity extends ActionBarActivity
{
    @Inject CSBlogsApi api;

    @InjectView(R.id.card_recycler_view) RecyclerView blogPostRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CSBlogsApp csBlogsApp = (CSBlogsApp) getApplication();
        csBlogsApp.dependencies().inject(this);

        ButterKnife.inject(this);

        blogPostRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));


        api.getBlogs(0, 10, new Callback<BlogsResponse>()
        {
            @Override
            public void success(BlogsResponse blogsResponse, Response response)
            {
                displayBlogPosts(blogsResponse.getBlogs());
            }

            @Override
            public void failure(RetrofitError error)
            {
            }
        });

    }

    private void displayBlogPosts(final List<BlogPost> blogPosts)
    {
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
