package com.yuzhou.rss.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.common.eventbus.EventBus;
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
 * A placeholder fragment containing a simple view.
 */
public class ChannelFragment extends BaseFragment
{
//    private static final String ARG_NEWS_URL = "news_url";
//    private static final String ARG_RESOURCE_LAYOUT = "layout";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
//    public static ChannelFragment newInstance(String url, int layout)
//    {
//        ChannelFragment fragment = new ChannelFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_NEWS_URL, url);
//        args.putInt(ARG_RESOURCE_LAYOUT, layout);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    private EventBus eventBus;
//    private MainActivity activity;
    private ChannelAdapter adapter;
    private ListView listView;

    public ChannelFragment()
    {
    }

//    @Override
//    public void onAttach(Activity activity)
//    {
//        super.onAttach(activity);
//        this.activity = (MainActivity) activity;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        eventBus = new EventBus();
//        eventBus.register(this);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        int layout = getArguments().getInt(ARG_RESOURCE_LAYOUT);

        ArrayList<RssItem> items = new ArrayList<>();
        adapter = new ChannelAdapter(activity, layout, items);
        listView.setAdapter(adapter);

        ContentFetcher fetcher = new ContentFetcher(eventBus);
        fetcher.execute(getArguments().getString(ARG_NEWS_URL));
    }

//    @Override
//    public void onDestroy()
//    {
//        super.onDestroy();
//        eventBus.unregister(this);
//    }

    @Subscribe
    public void updateDownloadResult(List<String> result)
    {
        try {
            RssFeed feed = RssReader.read(result.get(0));
            adapter.clear();
            adapter.addAll(feed.getRssItems());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
