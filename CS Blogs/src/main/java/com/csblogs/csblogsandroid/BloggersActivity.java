package com.csblogs.csblogsandroid;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.android.volley.toolbox.ImageLoader;
import com.csblogs.csblogsandroid.api.CSBlogsApi;
import com.csblogs.csblogsandroid.api.payloads.Blogger;
import com.csblogs.csblogsandroid.views.CircularNetworkImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class BloggersActivity extends ActionBarActivity
{
    @Inject ImageLoader imageLoader;
    @Inject CSBlogsApi csBlogsApi;
    @InjectView(R.id.bloggers_recycler_view) RecyclerView bloggersRecyclerView;

    List<Blogger> bloggersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloggers);

        CSBlogsApp csBlogsApp = (CSBlogsApp) getApplication();
        csBlogsApp.dependencies().inject(this);

        ButterKnife.inject(this);

        bloggersRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        bloggersRecyclerView.setAdapter(new RecyclerView.Adapter<BloggerGridItemHolder>()
        {
            @Override
            public BloggerGridItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                View bloggerGridItem = View.inflate(parent.getContext(), R.layout.blogger_grid_item, null);
                return new BloggerGridItemHolder(bloggerGridItem);
            }

            @Override
            public void onBindViewHolder(BloggerGridItemHolder holder, int position)
            {
                holder.bind(bloggersList.get(position));
            }

            @Override
            public int getItemCount()
            {
                return bloggersList.size();
            }
        });

        if(savedInstanceState == null)
        {
            fetchBloggers();
        }
    }

    private void fetchBloggers()
    {
        csBlogsApi.getBloggers(new Callback<List<Blogger>>()
        {
            @Override
            public void success(List<Blogger> bloggers, Response response)
            {
                bloggersList.clear();
                bloggersList.addAll(bloggers);
                bloggersRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error)
            {

            }
        });
    }

    class BloggerGridItemHolder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.blogger_image) CircularNetworkImageView bloggerImageView;
        @InjectView(R.id.blogger_name) TextView bloggerNameTextView;
        @InjectView(R.id.bloger_url) TextView bloggerUrlTextView;

        public BloggerGridItemHolder(View itemView)
        {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }

        public void bind(Blogger blogger)
        {
            bloggerImageView.setImageUrl(blogger.getAvatarUrl(), imageLoader);
            bloggerNameTextView.setText(blogger.getFirstName() + " " + blogger.getLastName());
            String blogUrl = blogger.getBlogWebsiteUrl().replace("http://", "");
            bloggerUrlTextView.setText(blogUrl);
        }
    }
}


