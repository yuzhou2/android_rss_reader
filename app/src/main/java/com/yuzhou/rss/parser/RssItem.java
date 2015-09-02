package com.yuzhou.rss.parser;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

public class RssItem implements Parcelable
{
    @Getter @Setter private RssFeed feed;
    @Getter @Setter private String title;
    @Getter @Setter private String link;
    @Getter @Setter private Date pubDate;
    @Getter @Setter private String description;
    @Getter @Setter private String content;
    @Getter private ArrayList images;

    public RssItem()
    {
        images = new ArrayList();
    }

    public RssItem(Parcel source)
    {
        Bundle data = source.readBundle();
        title = data.getString("title");
        link = data.getString("link");
        pubDate = (Date) data.getSerializable("pubDate");
        description = data.getString("description");
        content = data.getString("content");
        feed = data.getParcelable("feed");
        images = data.getParcelableArrayList("images");
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
        data.putSerializable("pubDate", pubDate);
        data.putString("description", description);
        data.putString("content", content);
        data.putParcelable("feed", feed);
        data.putParcelableArrayList("images", images);
        dest.writeBundle(data);
    }

    public static final Creator<RssItem> CREATOR = new Creator<RssItem>()
    {
        public RssItem createFromParcel(Parcel data)
        {
            return new RssItem(data);
        }

        public RssItem[] newArray(int size)
        {
            return new RssItem[size];
        }
    };

    public void addImage(String url)
    {
        images.add(url);
    }

    public void setPubDate(String pubDate)
    {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            this.pubDate = dateFormat.parse(pubDate);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
    }

}
