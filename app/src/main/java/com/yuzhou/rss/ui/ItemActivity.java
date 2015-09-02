package com.yuzhou.rss.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.yuzhou.rss.R;
import com.yuzhou.rss.parser.RssItem;

import java.util.ArrayList;

public class ItemActivity extends FragmentActivity
{
    private final ArrayList<RssItem> rssItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        initActionBar();
        initView();
    }

    private void initActionBar()
    {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    private void initView()
    {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        rssItems.addAll(intent.<RssItem>getParcelableArrayListExtra("rss_items"));

        ItemAdapter adapter = new ItemAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.item__view_pager);
        pager.setAdapter(adapter);

        pager.setCurrentItem(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ItemAdapter extends FragmentStatePagerAdapter
    {
        public ItemAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getCount()
        {
            return rssItems.size();
        }

        @Override
        public Fragment getItem(int position)
        {
            RssItem rssItem = rssItems.get(position);
            return ItemFragment.newInstance(rssItem);
        }
    }

}
