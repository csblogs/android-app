package com.csblogs.csblogsandroid.views;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.csblogs.csblogsandroid.CSBlogsApp;
import com.csblogs.csblogsandroid.R;
import com.csblogs.csblogsandroid.api.payloads.BlogPost;

import javax.inject.Inject;

public class BlogPostCard extends RelativeLayout
{
    private BlogPost blogPost;

    @InjectView(R.id.title_text) TextView titleTextView;
    @InjectView(R.id.blog_image) NetworkImageView blogImageView;
    @InjectView(R.id.summary_text) TextView summaryTextView;

    @Inject ImageLoader imageLoader;

    public BlogPostCard(Context context)
    {
        super(context);
        init();
    }

    public BlogPostCard(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BlogPostCard(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        inflate(getContext(), R.layout.blog_post_card, this);
        ButterKnife.inject(this);
        CSBlogsApp app = (CSBlogsApp) getContext().getApplicationContext();
        app.dependencies().inject(this);
    }

    public void setBlogPost(BlogPost blogPost)
    {
        this.blogPost = blogPost;

        titleTextView.setText(blogPost.getTitle());
        if(blogPost.getImageUrl() != null)
        {
            blogImageView.setVisibility(View.VISIBLE);
            blogImageView.setImageUrl(blogPost.getImageUrl(), imageLoader);
        }
        else
        {
            blogImageView.setVisibility(View.GONE);
        }
        summaryTextView.setText(Html.fromHtml(blogPost.getSummary()));
    }

}
