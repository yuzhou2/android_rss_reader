package com.yuzhou.rss.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.eventbus.Subscribe;
import com.yuzhou.rss.R;
import com.yuzhou.rss.api.ContentFetcher;
import com.yuzhou.rss.parser.RssFeed;
import com.yuzhou.rss.parser.RssItem;
import com.yuzhou.rss.parser.RssReader;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kovenliao on 9/2/15.
 */
public class PagerFragment extends BaseFragment {
    private ViewPager viewPager;
    private List<RssItem> items;
    private ChannelPagerAdapter adapter;
    private int layout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main_vpager, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.vpPager);
//        viewPager.setRotation(90);

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        layout = getArguments().getInt(ARG_RESOURCE_LAYOUT);

        items = new ArrayList<>();
//        adapter = new ChannelPagerAdapter(activity, layout, items);
//        viewPager.setAdapter(adapter);

        ContentFetcher fetcher = new ContentFetcher(eventBus);
        fetcher.execute(getArguments().getString(ARG_NEWS_URL));
    }


//        super.onStart();
//
//        int layout = getArguments().getInt(ARG_RESOURCE_LAYOUT);
//
//        items = new ArrayList<>();
//        adapter = new ChannelAdapter(activity, layout, items);
//        listView.setAdapter(adapter);
//

    @Subscribe
    public void updateDownloadResult(List<String> result)
    {
        try {
            RssFeed feed = RssReader.read(result.get(0));
            items.clear();
            items.addAll(feed.getRssItems());

            adapter = new ChannelPagerAdapter(activity, layout, items);
            viewPager.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
