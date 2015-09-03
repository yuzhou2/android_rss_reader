package com.yuzhou.rss.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.google.common.collect.ImmutableMap;
import com.yuzhou.rss.R;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private ImmutableMap<String, String> newsList;

    public MainActivity()
    {
        newsList = new ImmutableMap.Builder<String, String>()
                .put("Inside 硬塞的網路趨勢觀察", "http://www.inside.com.tw/feed")
                .put("蘋果日報", "http://www.appledaily.com.tw/rss/newcreate/kind/rnews/type/new")
                .put("TechCrunch Japan", "http://feed.rssad.jp/rss/techcrunch/feed")
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = newsList.keySet().asList().get(0);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mNavigationDrawerFragment.setNewsTitles(newsList.keySet().asList());
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        if (position == newsList.size()) {
            mTitle = "Default View";
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PreferenceFragment.newInstance())
                    .commit();
            return;
        }

        mTitle = newsList.keySet().asList().get(position);
        String url = newsList.values().asList().get(position);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, ChannelFragment.newInstance(url, getLayout()))
//                .commit();

        fragmentManager.beginTransaction()
                .replace(R.id.container, BaseFragment.newInstance(url, getLayout()))
                .commit();
    }

    private int getLayout()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        switch (sp.getString("prefs_layout", "list")) {
        case "title_only":
            return R.layout.item_rss_title_only;
        case "card":
            return R.layout.item_rss_card;
        case "list":
        default:
            return R.layout.item_rss_list;
        }
    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
