package com.yuzhou.rss.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yuzhou.rss.R;
import com.yuzhou.rss.parser.RssItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzhou on 2015/09/01.
 */
public class ChannelAdapter extends ArrayAdapter<RssItem>
{
    private final int resource;
    private final ArrayList<RssItem> rssItems;

    public ChannelAdapter(Context context, int resource, ArrayList<RssItem> items)
    {
        super(context, resource, items);
        this.resource = resource;
        this.rssItems = items; // call by reference for lazy loading
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item__image);
            holder.title = (TextView) convertView.findViewById(R.id.item__title);
            holder.desc  = (TextView) convertView.findViewById(R.id.item__description);
            convertView.setTag(holder);
        }

        final RssItem item = getItem(position);

        final List<String> images = item.getImages();
        if (!images.isEmpty()) {
            Picasso.with(getContext()).load(images.get(0)).into(holder.image);
        }
        holder.title.setText(item.getTitle());
        holder.desc.setText(android.text.Html.fromHtml(item.getDescription()).toString());

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("rss_items", rssItems);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private static class ViewHolder
    {
        private ImageView image;
        private TextView title;
        private TextView desc;
    }

}
