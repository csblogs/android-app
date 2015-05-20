package com.csblogs.csblogsandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.android.volley.toolbox.ImageLoader;
import com.csblogs.csblogsandroid.CSBlogsApp;
import com.csblogs.csblogsandroid.R;
import com.csblogs.csblogsandroid.api.CSBlogsApi;
import com.csblogs.csblogsandroid.api.payloads.Blogger;
import com.csblogs.csblogsandroid.crates.BloggerCrate;
import com.csblogs.csblogsandroid.views.CircularNetworkImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class BloggersFragment extends Fragment
{
    public static final String TAG = "BLOGGERS_FRAGMENT";

    @Inject CSBlogsApi csBlogsApi;
    @Inject ImageLoader imageLoader;
    @Inject BloggerCrate bloggerCrate;
    @InjectView(R.id.bloggers_recycler_view) RecyclerView bloggersRecyclerView;

    private List<Blogger> bloggersList = new ArrayList<>();


    public BloggersFragment()
    {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        CSBlogsApp.dependencies().inject(this);

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
                if(bloggersRecyclerView != null)
                {
                    bloggersRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error)
            {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_bloggers,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);


        bloggersRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

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
    }

    private static final String EXTRA_DISPLAYED_ITEM = "displayedItem";

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        if(bloggersList.size() > 0)
        {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) bloggersRecyclerView.getLayoutManager();
            int displayedIndex = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
            if (displayedIndex == -1)
            {
                // if no item is completely visible clip to first visible item
                displayedIndex = gridLayoutManager.findFirstVisibleItemPosition();
            }
            outState.putInt(EXTRA_DISPLAYED_ITEM, displayedIndex);

            bloggerCrate.removeAll();
            bloggerCrate.put(bloggersList);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null)
        {
            bloggersList.addAll(bloggerCrate.all());
            bloggersRecyclerView.getAdapter().notifyDataSetChanged();

            int displayedItem = savedInstanceState.getInt(EXTRA_DISPLAYED_ITEM, 0);
            bloggersRecyclerView.scrollToPosition(displayedItem);
        }
    }

    class BloggerGridItemHolder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.blogger_image)
        CircularNetworkImageView bloggerImageView;
        @InjectView(R.id.blogger_name)
        TextView bloggerNameTextView;
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


