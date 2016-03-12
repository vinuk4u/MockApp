package com.iv.mockapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iv.mockapp.R;
import com.iv.mockapp.adapters.MAHeaderListItemsAdapter;
import com.iv.mockapp.adapters.MAViewPagerAdapter;
import com.iv.mockapp.utils.view.MADividerItemDecoration;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vineeth on 11/09/16
 */
public class MAScenario1Fragment extends Fragment {

    @Bind(R.id.items_recyclerview)
    RecyclerView mHeaderItemsRecyclerView;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.viewpager_indicator)
    CirclePageIndicator mViewPagerIndicator;
    @Bind(R.id.display_item_textview)
    TextView mDisplayItemTextView;
    @Bind(R.id.color_linearlayout)
    LinearLayout colorLinearLayout;
    private final ArrayList<String> mHeaderItemsDataSet = new ArrayList<>();

    public MAScenario1Fragment() {
    }

    public static MAScenario1Fragment newInstance() {
        return new MAScenario1Fragment();
    }

    @OnClick(R.id.red_color_button)
    void onRedClick() {
        colorLinearLayout.setBackgroundColor(Color.RED);
    }

    @OnClick(R.id.blue_color_button)
    void onBlueClick() {
        colorLinearLayout.setBackgroundColor(Color.BLUE);
    }

    @OnClick(R.id.green_color_button)
    void onGreenClick() {
        colorLinearLayout.setBackgroundColor(Color.GREEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_scenario1_layout, container, false);

        ButterKnife.bind(this, rootView);

        initViews();

        return rootView;
    }

    private void initViews() {
        initHeaderItems();

        initViewPager();
    }

    private void initHeaderItems() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mHeaderItemsRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHeaderItemsRecyclerView.setLayoutManager(linearLayoutManager);
        mHeaderItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHeaderItemsRecyclerView.addItemDecoration(new MADividerItemDecoration(getActivity()));

        final MAHeaderListItemsAdapter mHeaderItemsAdapter = new MAHeaderListItemsAdapter(mHeaderItemsDataSet,
                new MAHeaderListItemsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String item) {
                        mDisplayItemTextView.setText(item);
                    }
                });
        mHeaderItemsRecyclerView.setAdapter(mHeaderItemsAdapter);

        // populate 5 items in the header
        for (int i = 0; i < 5; i++) {
            mHeaderItemsDataSet.add("Item " + (i + 1));
        }
    }

    private void initViewPager() {
        mViewPager.setAdapter(new MAViewPagerAdapter(getActivity().getSupportFragmentManager()));
        mViewPagerIndicator.setViewPager(mViewPager);
    }

}
