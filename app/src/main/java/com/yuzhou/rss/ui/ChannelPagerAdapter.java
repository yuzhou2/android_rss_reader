package com.yuzhou.rss.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yuzhou.rss.R;
import com.yuzhou.rss.parser.RssItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kovenliao on 9/2/15.
 */
public class ChannelPagerAdapter extends PagerAdapter {

    private Activity activity;
    private List<RssItem> items;
    private LayoutInflater inflater;

    // constructor
    public ChannelPagerAdapter(Activity activity, int resource, List<RssItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view;
        TextView title;
        Button btnClose;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.item_rss_card, container, false);

        view = (ImageView) viewLayout.findViewById(R.id.item__image);
        title = (TextView) viewLayout.findViewById(R.id.item__title);
//        view.setRotation(-90f);
        title.setText(items.get(position).getTitle());
//        title.setRotation(-90f);
//        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);


        List<String> images = items.get(position).getImages();
        if (!images.isEmpty()) {
            Picasso.with(activity).load(images.get(0)).into(view);
        }

//        Bitmap bitmap = BitmapFactory.decodeFile(items.get(position), options);
//        imgDisplay.setImageBitmap(bitmap);
//
//        // close button click event
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                _activity.finish();
//            }
//        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
