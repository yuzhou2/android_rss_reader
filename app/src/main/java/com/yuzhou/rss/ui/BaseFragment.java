package com.yuzhou.rss.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.google.common.eventbus.EventBus;
import com.yuzhou.rss.R;

/**
 * Created by kovenliao on 9/2/15.
 */
public class BaseFragment extends Fragment {
    protected static final String ARG_NEWS_URL = "news_url";
    protected static final String ARG_RESOURCE_LAYOUT = "layout";

    protected EventBus eventBus;

    protected MainActivity activity;

    public static BaseFragment newInstance(String url, int layout)
    {
        BaseFragment fragment;

        switch (layout) {
//            case R.layout.item_rss_title_only:
//            case R.layout.item_rss_list:
            case R.layout.item_rss_card:
//                fragment = new PagerFragment();
                fragment = new PagerFragment();
                break;
            default:
                fragment = new ChannelFragment();
        }

        Bundle args = new Bundle();
        args.putString(ARG_NEWS_URL, url);
        args.putInt(ARG_RESOURCE_LAYOUT, layout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        eventBus = new EventBus();
        eventBus.register(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        eventBus.unregister(this);
    }
}
