package com.yuzhou.rss.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yuzhou.rss.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreferenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreferenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreferenceFragment extends Fragment
{
    private Button bnTitleOnly;
    private Button bnList;
    private Button bnCard;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PreferenceFragment.
     */
    public static PreferenceFragment newInstance()
    {
        PreferenceFragment fragment = new PreferenceFragment();
        return fragment;
    }

    private SharedPreferences sp;

    public PreferenceFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preference, container, false);
        bnTitleOnly = (Button) view.findViewById(R.id.prefs__layout_title_only);
        bnList = (Button) view.findViewById(R.id.prefs__layout_list);
        bnCard = (Button) view.findViewById(R.id.prefs__layout_card);

        initButtonListener();
        refreshSelected();
        return view;
    }

    private void initButtonListener()
    {
        bnTitleOnly.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("prefs_layout", "title_only");
                editor.commit();
                refreshSelected();
            }
        });
        bnList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("prefs_layout", "list");
                editor.commit();
                refreshSelected();
            }
        });
        bnCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("prefs_layout", "card");
                editor.commit();
                refreshSelected();
            }
        });
    }

    private void refreshSelected()
    {
        int _default = getResources().getColor(R.color.lighter_gray);
        int _selected = getResources().getColor(R.color.lighter_blue);

        bnTitleOnly.setTextColor(_default);
        bnList.setTextColor(_default);
        bnCard.setTextColor(_default);

        switch (sp.getString("prefs_layout", "list")) {
        case "title_only":
            bnTitleOnly.setTextColor(_selected);
            break;
        case "card":
            bnCard.setTextColor(_selected);
            break;
        case "list":
        default:
            bnList.setTextColor(_selected);
        }
    }

}
