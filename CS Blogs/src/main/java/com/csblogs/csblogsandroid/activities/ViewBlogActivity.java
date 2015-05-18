package com.csblogs.csblogsandroid.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.csblogs.csblogsandroid.CSBlogsApp;
import com.csblogs.csblogsandroid.R;

public class ViewBlogActivity extends ActionBarActivity
{
    public static final String EXTRA_BLOG_URL ="BLOG_URL";

    @InjectView(R.id.webView) WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blog);

        CSBlogsApp csBlogsApp = (CSBlogsApp) getApplication();
        csBlogsApp.dependencies().inject(this);

        ButterKnife.inject(this);

        webView.getSettings().setJavaScriptEnabled(true);

        if(savedInstanceState == null)
        {
            Intent intent = getIntent();
            if(intent != null)
            {
                String blogUrl = intent.getStringExtra(EXTRA_BLOG_URL);
                webView.loadUrl(blogUrl);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_view_blog, menu);
        return true;
    }
}
