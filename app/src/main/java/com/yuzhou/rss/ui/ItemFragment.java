package com.yuzhou.rss.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuzhou.rss.R;
import com.yuzhou.rss.parser.RssItem;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by yuzhou on 2015/09/02.
 */
public class ItemFragment extends Fragment implements Html.ImageGetter
{

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    static ItemFragment newInstance(RssItem item)
    {
        ItemFragment f = new ItemFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("rss_item", item);
        f.setArguments(args);

        return f;
    }

    private RssItem rssItem;
    private TextView tvContent;

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        rssItem = getArguments().getParcelable("rss_item");
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_item_list, container, false);
        TextView title = (TextView) v.findViewById(R.id.article__title);
        tvContent = (TextView) v.findViewById(R.id.article__content);

        title.setText(rssItem.getTitle());
        Spanned spanned = Html.fromHtml(rssItem.getContent(), this, null);
        tvContent.setText(spanned);

        return v;
    }

    @Override
    public Drawable getDrawable(String source)
    {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.color.white);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);
        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap>
    {
        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params)
        {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                CharSequence t = tvContent.getText();
                tvContent.setText(t);
            }
        }
    }

}
