package com.taboola.sdk3example.sdk_classic;

import androidx.annotation.NonNull;

import com.taboola.sdk3example.tabs.BaseTabFragment;
import com.taboola.sdk3example.tabs.BaseTaboolaFragment;
import com.taboola.sdk3example.tabs.FragmentsAdapter;


public class ViewPagerFragment extends BaseTabFragment<BaseTaboolaFragment> {

    @Override
    protected void setupViewPagerAdapter(FragmentsAdapter<BaseTaboolaFragment> adapter) {
        super.setupViewPagerAdapter(adapter);
        String viewId = Long.toString(System.currentTimeMillis());
        adapter.addFragment(FeedInsideRecyclerViewFragment.getInstance(viewId));
        adapter.addFragment(FeedInsideScrollViewFragment.getInstance(viewId));
    }

    @NonNull
    @Override
    protected String getTextForItem(int currentItem) {

        switch (currentItem) {
            case 0:
                return "FeedInsideRecyclerView";
            case 1:
                return "FeedInsideScrollView";

            default:
                return super.getTextForItem(currentItem);
        }
    }
}
