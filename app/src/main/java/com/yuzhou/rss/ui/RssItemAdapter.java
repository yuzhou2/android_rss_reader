package com.yuzhou.rss.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yuzhou.rss.R;
import com.yuzhou.rss.parser.RssItem;

import java.util.List;

/**
 * Created by yuzhou on 2015/09/01.
 */
public class RssItemAdapter extends ArrayAdapter<RssItem>
{
    private int resource;

    public RssItemAdapter(Context context, int resource, List<RssItem> items)
    {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.item__title);
            holder.desc  = (TextView) convertView.findViewById(R.id.item__description);
            convertView.setTag(holder);
        }

        RssItem item = getItem(position);
        holder.title.setText(item.getTitle());
        holder.desc.setText(item.getDescription());

        return convertView;
    }

    private static class ViewHolder
    {
        private TextView title;
        private TextView desc;
    }

}
