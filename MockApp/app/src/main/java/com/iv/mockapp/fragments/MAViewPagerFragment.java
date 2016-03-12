package com.iv.mockapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iv.mockapp.R;
import com.iv.mockapp.utils.view.MAViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vineeth on 09/09/16
 */
public class MAViewPagerFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    @Bind(R.id.title_textview)
    TextView mTitleTextView;

    public static MAViewPagerFragment init(int position) {
        MAViewPagerFragment fragment = new MAViewPagerFragment();
        // Supply value input as an argument.
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.item_viewpager_layout,
                container, false);

        ButterKnife.bind(this, rootView);

        final int position = getArguments() != null ? getArguments().getInt(KEY_POSITION) : 0;
        final String[] titles = {"ViewPager 1", "ViewPager 2", "ViewPager 3", "ViewPager 4"};

        if (position < titles.length) {
            mTitleTextView.setText(titles[position]);

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MAViewUtil.showToastMessage(getActivity(), titles[position], Toast.LENGTH_SHORT);
                }
            });
        }

        return rootView;
    }
}
