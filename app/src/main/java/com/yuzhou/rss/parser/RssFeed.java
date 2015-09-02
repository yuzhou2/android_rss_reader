package com.yuzhou.rss.parser;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class RssFeed implements Parcelable
{
    @Getter @Setter private String title;
    @Getter @Setter private String link;
    @Getter @Setter private String description;
    @Getter @Setter private String language;
    @Getter private ArrayList<RssItem> rssItems;

    public RssFeed()
    {
        rssItems = new ArrayList<RssItem>();
    }

    public RssFeed(Parcel source)
    {
        Bundle data = source.readBundle();
        title = data.getString("title");
        link = data.getString("link");
        description = data.getString("description");
        language = data.getString("language");
        rssItems = data.getParcelableArrayList("rssItems");
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

        Bundle data = new Bundle();
        data.putString("title", title);
        data.putString("link", link);
        data.putString("description", description);
        data.putString("language", language);
        data.putParcelableArrayList("rssItems", rssItems);
        dest.writeBundle(data);
    }

    public static final Creator<RssFeed> CREATOR = new Creator<RssFeed>()
    {
        public RssFeed createFromParcel(Parcel data)
        {
            return new RssFeed(data);
        }

        public RssFeed[] newArray(int size)
        {
            return new RssFeed[size];
        }
    };

    public void addRssItem(RssItem rssItem)
    {
        rssItem.setFeed(this);
        rssItems.add(rssItem);
    }

}
