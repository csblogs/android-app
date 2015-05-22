package com.csblogs.csblogsandroid;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.csblogs.csblogsandroid.fragments.BloggerFragment;
import com.csblogs.csblogsandroid.fragments.BloggersFragment;
import com.csblogs.csblogsandroid.fragments.LatestBlogsFragment;


public class MainActivity extends ActionBarActivity implements BloggersFragment.BloggerSelectedCallback
{

    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    private final String[] enabledNavDrawerItems = {LatestBlogsFragment.TAG, BloggersFragment.TAG};
    @InjectView(R.id.drawer_items_container) LinearLayout drawerItemsContainer;

    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CSBlogsApp.dependencies().inject(this);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setupDrawer();

        if(savedInstanceState == null)
        {
            LatestBlogsFragment latestBlogsFragment = new LatestBlogsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,latestBlogsFragment,LatestBlogsFragment.TAG).commit();
        }
    }

    private void setupDrawer()
    {
        drawerLayout.setScrimColor(getResources().getColor(R.color.drawer_scrim_color));
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.csblogs_red));

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                if (pendingFragmentTransaction != null)
                {
                    pendingFragmentTransaction.commit();
                    pendingFragmentTransaction = null;
                }
            }
        };
        actionBarDrawerToggle.syncState();

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        final View.OnClickListener drawerItemClicked = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setPendingFragmentTransaction(v);
                drawerLayout.closeDrawers();
            }
        };

        LayoutInflater layoutInflater = getLayoutInflater();
        for(String drawerItemTag: enabledNavDrawerItems)
        {
            View newDrawerItem = layoutInflater.inflate(R.layout.drawer_list_item, drawerItemsContainer, false);
            ImageView iconView = (ImageView) newDrawerItem.findViewById(R.id.icon);
            TextView title = (TextView) newDrawerItem.findViewById(R.id.title);

            switch (drawerItemTag)
            {
                case LatestBlogsFragment.TAG:
                    iconView.setImageResource(R.drawable.ic_whatshot_black_48dp);
                    title.setText(R.string.latest_blogs);
                    break;
                case BloggersFragment.TAG:
                    iconView.setImageResource(R.drawable.ic_people_outline_black_48dp);
                    title.setText(R.string.bloggers);
                    break;
            }

            newDrawerItem.setTag(drawerItemTag);
            newDrawerItem.setOnClickListener(drawerItemClicked);

            drawerItemsContainer.addView(newDrawerItem);
        }
    }

    private FragmentTransaction pendingFragmentTransaction;

    private void setPendingFragmentTransaction(View drawerItem)
    {
        String selectedTag = (String) drawerItem.getTag();
        Fragment fragmentCurrentlyDisplayed = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if(fragmentCurrentlyDisplayed.getTag().equals(selectedTag))
        {
            return;
        }

        Fragment fragmentToShow = null;
        String fragmentToShowTag = null;

        pendingFragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (selectedTag)
        {
            case BloggersFragment.TAG:
                fragmentToShowTag = BloggersFragment.TAG;
                fragmentToShow = getSupportFragmentManager().findFragmentByTag(fragmentToShowTag);
                if(fragmentToShow == null)
                {
                    BloggersFragment bloggersFragment = new BloggersFragment();
                    bloggersFragment.setBloggerSelectedCallback(this);
                    fragmentToShow = bloggersFragment;
                }
                pendingFragmentTransaction.addToBackStack(null);
                break;
            case LatestBlogsFragment.TAG:
                getSupportFragmentManager().popBackStack();
                pendingFragmentTransaction = null;
                return;
        }
        pendingFragmentTransaction.replace(R.id.fragment_container,fragmentToShow,fragmentToShowTag);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBloggerClicked(String bloggerId)
    {
        Bundle args = new Bundle();
        args.putString(BloggerFragment.ARG_BLOGGER_ID, bloggerId);

        BloggerFragment bloggerFragment = new BloggerFragment();
        bloggerFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        BloggersFragment bloggersFragment = (BloggersFragment) getSupportFragmentManager().findFragmentByTag(BloggersFragment.TAG);
        if(bloggersFragment != null)
        {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            {
                TransitionInflater transitionInflater = TransitionInflater.from(this);

                bloggerFragment.setSharedElementEnterTransition(transitionInflater.inflateTransition(R.transition.shared_elements_transition));
                bloggerFragment.setSharedElementReturnTransition(transitionInflater.inflateTransition(R.transition.shared_elements_transition));

                bloggersFragment.setSharedElementEnterTransition(transitionInflater.inflateTransition(R.transition.shared_elements_transition));
                bloggersFragment.setSharedElementReturnTransition(transitionInflater.inflateTransition(R.transition.shared_elements_transition));
            }

            bloggersFragment.addSharedElements(fragmentTransaction);
        }

        fragmentTransaction.replace(R.id.fragment_container, bloggerFragment, BloggerFragment.TAG)
                .addToBackStack(null).commit();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        //if activity has been recreated register for bloggerSelected callbacks again
        BloggersFragment bloggersFragment = (BloggersFragment) getSupportFragmentManager().findFragmentByTag(BloggersFragment.TAG);
        if(bloggersFragment != null)
        {
            bloggersFragment.setBloggerSelectedCallback(this);
        }
    }
}
