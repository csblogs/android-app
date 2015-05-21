package com.csblogs.csblogsandroid.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.csblogs.csblogsandroid.CSBlogsApp;
import com.csblogs.csblogsandroid.R;
import com.csblogs.csblogsandroid.api.CSBlogsApi;
import com.csblogs.csblogsandroid.api.payloads.BlogPost;
import com.csblogs.csblogsandroid.api.payloads.BlogsResponse;
import com.csblogs.csblogsandroid.crates.BlogPostCrate;
import com.csblogs.csblogsandroid.views.BlogPostCard;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class LatestBlogsFragment extends Fragment
{
    public static final String TAG = "LATEST_BLOGS_FRAGMENT";
    private static final int BLOG_REQUEST_LIMIT = 10;

    @Inject CSBlogsApi api;
    @Inject BlogPostCrate blogPostCrate;

    @InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.card_recycler_view) RecyclerView blogPostRecyclerView;

    private int blogPage = 0;
    private List<BlogPost> blogPosts = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        CSBlogsApp.dependencies().inject(this);

        if(savedInstanceState == null)
        {
            fetchMoreBlogs();
        }

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

        ButterKnife.inject(this,view);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
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
                blogPostCrate.removeWithTag(BlogPostCrate.TAG_LATEST);
                fetchMoreBlogs();
            }
        });
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
                blogPostCrate.put(blogPosts, BlogPostCrate.TAG_LATEST);
                if(blogPostRecyclerView != null)
                {
                    blogPostRecyclerView.getAdapter().notifyDataSetChanged();
                    if (swipeRefreshLayout.isRefreshing())
                    {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error)
            {
                if(swipeRefreshLayout != null)
                {
                    if (swipeRefreshLayout.isRefreshing())
                    {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
    }

    private static final String EXTRA_DISPLAYED_ITEM = "displayedItem";
    private static final String EXTRA_BLOG_PAGE = "blogPage";

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) blogPostRecyclerView.getLayoutManager();
        int displayedIndex = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
        if(displayedIndex == -1)
        {
            // if no item is completely visible clip to first visible item
            displayedIndex = gridLayoutManager.findFirstVisibleItemPosition();
        }
        outState.putInt(EXTRA_DISPLAYED_ITEM, displayedIndex);
        outState.putInt(EXTRA_BLOG_PAGE, blogPage);

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null)
        {
            this.blogPage = savedInstanceState.getInt(EXTRA_BLOG_PAGE, 0);

            List<BlogPost> latestBlogs = blogPostCrate.withTag(BlogPostCrate.TAG_LATEST);
            blogPosts.addAll(latestBlogs);
            blogPostRecyclerView.getAdapter().notifyDataSetChanged();

            int firstDisplayedItem = savedInstanceState.getInt(EXTRA_DISPLAYED_ITEM, 0);
            blogPostRecyclerView.scrollToPosition(firstDisplayedItem);
        }
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
