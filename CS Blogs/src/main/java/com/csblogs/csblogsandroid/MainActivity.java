package com.csblogs.csblogsandroid;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity
{
    private static final String DRAWER_ITEM_BLOGS = "BLOGS";
    private static final String DRAWER_ITEM_BLOGGERS = "BLOGGERS";

    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    private final String[] enabledNavDrawerItems = {DRAWER_ITEM_BLOGS, DRAWER_ITEM_BLOGGERS};
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
    }

    private void setupDrawer()
    {
        drawerLayout.setScrimColor(getResources().getColor(R.color.drawer_scrim_color));
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.csblogs_red));

        View.OnClickListener drawerItemClicked = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onDrawerItemClicked(v);
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
                case DRAWER_ITEM_BLOGS:
                    iconView.setImageResource(R.drawable.ic_whatshot_black_48dp);
                    title.setText(R.string.latest_blogs);
                    break;
                case DRAWER_ITEM_BLOGGERS:
                    iconView.setImageResource(R.drawable.ic_people_outline_black_48dp);
                    title.setText(R.string.bloggers);
                    break;
            }

            newDrawerItem.setTag(drawerItemTag);
            newDrawerItem.setOnClickListener(drawerItemClicked);

            drawerItemsContainer.addView(newDrawerItem);
        }
    }

    private void onDrawerItemClicked(View drawerItem)
    {
        drawerLayout.closeDrawers();
        Toast.makeText(this, (CharSequence) drawerItem.getTag(),Toast.LENGTH_SHORT).show();
    }
}
