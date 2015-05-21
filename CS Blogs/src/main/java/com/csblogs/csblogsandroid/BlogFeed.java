package com.csblogs.csblogsandroid;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.csblogs.csblogsandroid.api.CSBlogsApi;
import com.csblogs.csblogsandroid.api.payloads.BlogPost;
import com.csblogs.csblogsandroid.crates.BlogPostCrate;
import com.csblogs.csblogsandroid.views.BlogPostCard;
import retrofit.RetrofitError;

import javax.inject.Inject;
import javax.security.auth.callback.Callback;
import java.util.ArrayList;
import java.util.List;

public abstract class BlogFeed implements Callback
{
    private static final String EXTRA_DISPLAYED_ITEM = "displayedItem";
    private static final String EXTRA_BLOG_PAGE = "blogPage";

    private static final int BLOG_REQUEST_LIMIT = 10;

    @Inject CSBlogsApi csBlogsApi;
    @Inject BlogPostCrate blogPostCrate;

    private final String FEED_TAG;
    private final RecyclerView blogPostRecyclerView;
    private final SwipeRefreshLayout swipeRefreshLayout;

    private int blogPage = 0;
    private List<BlogPost> blogPosts = new ArrayList<>();

    public BlogFeed(SwipeRefreshLayout swipeRefreshLayout,RecyclerView blogPostCardRecyclerview,String feedTag)
    {
        CSBlogsApp.dependencies().inject(this);

        this.swipeRefreshLayout = swipeRefreshLayout;
        this.blogPostRecyclerView = blogPostCardRecyclerview;
        this.FEED_TAG = "BLOG_FEED_" + feedTag;

        setupRecyclerView();
    }

    private void setupRecyclerView()
    {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(blogPostRecyclerView.getContext(), 1);
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
                    fetchMoreBlogs(blogPage,BLOG_REQUEST_LIMIT);
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
                blogPostCrate.removeWithTag(FEED_TAG);
                fetchMoreBlogs(blogPage, BLOG_REQUEST_LIMIT);
            }
        });
    }

    private void addBlogs(List<BlogPost> blogPosts)
    {
        blogPostCrate.put(blogPosts, FEED_TAG);
        this.blogPosts.addAll(blogPosts);
        blogPostRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public final void fetchMoreBlogs()
    {
        fetchMoreBlogs(this.blogPage,BLOG_REQUEST_LIMIT);
    }

    protected abstract void fetchMoreBlogs(int pageNumber,int blogRequestLimit);

    protected final void onBlogsFetched(List<BlogPost> blogs)
    {
        addBlogs(blogs);
        blogPage++;
    }

    protected final void onFetchBlogsError(RetrofitError retrofitError)
    {
        if (swipeRefreshLayout.isRefreshing())
        {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public final void onSaveInstanceState(Bundle outState)
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
    }

    public final void onRestoreInstanceState(Bundle savedInstanceState)
    {
        this.blogPage = savedInstanceState.getInt(EXTRA_BLOG_PAGE, 0);

        List<BlogPost> latestBlogs = blogPostCrate.withTag(FEED_TAG);
        blogPosts.addAll(latestBlogs);
        blogPostRecyclerView.getAdapter().notifyDataSetChanged();

        int firstDisplayedItem = savedInstanceState.getInt(EXTRA_DISPLAYED_ITEM, 0);
        blogPostRecyclerView.scrollToPosition(firstDisplayedItem);
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
