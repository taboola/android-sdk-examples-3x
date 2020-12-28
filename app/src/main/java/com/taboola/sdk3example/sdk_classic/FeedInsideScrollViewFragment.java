package com.taboola.sdk3example.sdk_classic;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.taboola.sdk3example.R;
import com.taboola.sdk3example.tabs.BaseTaboolaFragment;
import com.taboola.android.TBLClassicPage;
import com.taboola.android.TBLClassicUnit;
import com.taboola.android.Taboola;
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE;
import com.taboola.android.listeners.TBLClassicListener;
import com.taboola.android.utils.TBLSdkDetailsHelper;
import java.util.HashMap;


public class FeedInsideScrollViewFragment extends BaseTaboolaFragment {
    private TBLClassicUnit tblClassicUnit;
    private boolean shouldFetch;
    private boolean isTaboolaFetched = false;
    private static String TAG="FeedInsideScrollViewFragment";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_feed_inside_sv, container, false);
        tblClassicUnit = view.findViewById(R.id.taboola_widget_below_article);
        buildBelowArticleWidget(inflater.getContext());
        return view;
    }

    @Override
    public void onPageSelected() {
        // In ScrollView the widget will need to be rendered only when page is selected, no need to fetch if user didn't see taboola view
        // this is the most common use for view pager and you should follow this example
        // unless you use RecyclerView, then you need to follow FeedInsideRecyclerViewFragment example
        Log.d(TAG,"onPageSelected");
        if (!isTaboolaFetched) {
            shouldFetch = true;
            if (tblClassicUnit != null) {
                fetchContent();
            }
        }
    }

    private void buildBelowArticleWidget(Context context) {

        tblClassicUnit
                .setPublisherName("sdk-tester-demo")
                .setPageType("article")
                .setPageUrl("https://blog.taboola.com")
                .setPlacement("Feed without video")
                .setMode("thumbs-feed-01")
                .setTargetType("mix")
                .setInterceptScroll(true);

        //optional
        if (!TextUtils.isEmpty(getViewId)) {
            tblClassicUnit.setPageId(getViewId);
        }

        //used for enable horizontal scroll
        HashMap<String, String> extraProperties = new HashMap<>();
        extraProperties.put("enableHorizontalScroll", "true");
        extraProperties.put("useOnlineTemplate", "true");

        tblClassicUnit.getLayoutParams().height = TBLSdkDetailsHelper.getDisplayHeight(context);

        TBLClassicPage tblClassicPage= Taboola.getClassicPage("https://blog.taboola.com", "article");

        tblClassicUnit.setUnitExtraProperties(extraProperties);
        TBLClassicListener tblClassicListener;
        tblClassicListener=new TBLClassicListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }
        };
        tblClassicPage.addUnitToPage(tblClassicUnit,"Feed without video","thumbs-feed-01", TBL_PLACEMENT_TYPE.FEED,tblClassicListener);
        fetchContent();
    }

    private void fetchContent() {
        Log.d(TAG,"onContentFetch");
        if (shouldFetch) {
            shouldFetch = false;
            tblClassicUnit.fetchContent();
            isTaboolaFetched = true;
        }
    }

    public static FeedInsideScrollViewFragment getInstance(String viewId) {
        FeedInsideScrollViewFragment baseTaboolaFragment = new FeedInsideScrollViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIEW_ID, viewId);
        baseTaboolaFragment.setArguments(bundle);
        return baseTaboolaFragment;
    }

}
