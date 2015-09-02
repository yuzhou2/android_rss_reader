package com.yuzhou.rss.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuzhou.rss.R;
import com.yuzhou.rss.parser.RssItem;

/**
 * Created by yuzhou on 2015/09/02.
 */
public class ItemFragment extends ListFragment
{
    int mNum;

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    static ItemFragment newInstance(RssItem item, int num)
    {
        ItemFragment f = new ItemFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putParcelable("rss_item", item);
        f.setArguments(args);

        return f;
    }

    private RssItem rssItem;

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
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
        TextView tv = (TextView) v.findViewById(R.id.text);
        ((TextView)tv).setText("Fragment #" + mNum);
        tv.setText(rssItem.getTitle());
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, new String[]{"a", "b"}));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Log.i("FragmentList", "Item clicked: " + id);
    }

}
