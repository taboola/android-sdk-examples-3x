package com.e.sdk_example_app_30.sdk_classic;

import androidx.annotation.NonNull;

import com.e.sdk_example_app_30.tabs.BaseTabFragment;
import com.e.sdk_example_app_30.tabs.BaseTaboolaFragment;
import com.e.sdk_example_app_30.tabs.FragmentsAdapter;


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
