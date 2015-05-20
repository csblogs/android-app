package com.csblogs.csblogsandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.csblogs.csblogsandroid.fragments.BloggersFragment;
import com.csblogs.csblogsandroid.fragments.LatestBlogsFragment;


public class MainActivity extends ActionBarActivity
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

        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener()
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
        });

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
                    fragmentToShow =  new BloggersFragment();
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
}
