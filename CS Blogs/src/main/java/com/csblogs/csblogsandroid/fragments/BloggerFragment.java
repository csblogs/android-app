package com.csblogs.csblogsandroid.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.android.volley.toolbox.ImageLoader;
import com.csblogs.csblogsandroid.CSBlogsApp;
import com.csblogs.csblogsandroid.R;
import com.csblogs.csblogsandroid.api.payloads.Blogger;
import com.csblogs.csblogsandroid.crates.BloggerCrate;
import com.csblogs.csblogsandroid.views.CircularNetworkImageView;

import javax.inject.Inject;

public class BloggerFragment extends Fragment
{
    public static final String TAG = "BLOGGER_FRAGMENT";
    public static final String ARG_BLOGGER_ID = "BLOGGER";

    @Inject BloggerCrate bloggerCrate;
    @Inject ImageLoader imageLoader;
    @InjectView(R.id.blogger_image) CircularNetworkImageView bloggerImageView;
    @InjectView(R.id.blogger_name) TextView bloggerNameTextView;
    @InjectView(R.id.blogger_url) TextView bloggerUrlTextView;

    private Blogger blogger;

    public BloggerFragment()
    {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        CSBlogsApp.dependencies().inject(this);

        Bundle args = getArguments();
        if(args != null)
        {
            String bloggerId = getArguments().getString(ARG_BLOGGER_ID);
            if (bloggerId != null)
            {
                blogger = bloggerCrate.withId(bloggerId);
            }
        }

        if(Build.VERSION.SDK_INT >= 21)
        {
            setEnterTransition(new Slide(Gravity.BOTTOM));
            setExitTransition(new Slide(Gravity.BOTTOM));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_blogger,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        if(blogger != null)
        {
            bloggerImageView.setImageUrl(blogger.getProfile_picture_uri(), imageLoader);
            bloggerNameTextView.setText(blogger.getFirst_name() + " " + blogger.getLast_name());
            bloggerUrlTextView.setText(blogger.getBlog_uri().replace("http://", ""));
        }
    }
}
