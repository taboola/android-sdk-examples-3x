package com.e.sdk_example_app_30.sdk_classic;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.e.sdk_example_app_30.R;
import com.taboola.android.TBLClassicPage;
import com.taboola.android.TBLClassicUnit;
import com.taboola.android.Taboola;
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE;
import com.taboola.android.listeners.TBLClassicListener;
import com.taboola.android.utils.TBLSdkDetailsHelper;

import java.util.HashMap;


public class PullToRefreshFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TBLClassicUnit mTaboolaWidget;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_pull_to_refresh, container, false);
        mTaboolaWidget = view.findViewById(R.id.taboola_view);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
//        TBLClassicUnit taboolaWidget = view.findViewById(R.id.taboola_view);
//        HashMap<String, String> extraProperties = new HashMap<>();
//        extraProperties.put("useOnlineTemplate", "true");
//        taboolaWidget.setUnitExtraProperties(extraProperties);
//        taboolaWidget.fetchContent();
        TBLClassicPage tblClassicPage=Taboola.getClassicPage(getContext(), "https://blog.taboola.com", "article");
//        HashMap<String, String> extraProperties = new HashMap<>();
//        extraProperties.put("useOnlineTemplate", "true");
//        mTaboolaWidget.setUnitExtraProperties(extraProperties);

        tblClassicPage.addUnitToPage(mTaboolaWidget, "Below Article", "alternating-widget-without-video-1x4", TBL_PLACEMENT_TYPE.FEED, new TBLClassicListener() {
            @Override
            public void onUpdateContentCompleted() {
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });
        mTaboolaWidget.fetchContent();
        return view;
    }

//    private void buildBelowArticleWidget(TBLClassicUnit mTaboolaWidget) {
//
//         mTaboolaWidget
//                .setPublisherName("sdk-tester-demo")
//                .setPageType("article")
//                .setPageUrl("https://blog.taboola.com")
//                .setPlacement("Feed without video")
//                .setMode("thumbs-feed-01")
//                .setTargetType("mix")
//                .setInterceptScroll(true);
//
//
//        int displayHeight = TBLSdkDetailsHelper.getDisplayHeight(mTaboolaWidget.getContext());
//        int height = displayHeight * 2;
//        ViewGroup.LayoutParams params = mTaboolaWidget.getLayoutParams();
//        if (params == null) {
//            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
//            mTaboolaWidget.setLayoutParams(params);
//        } else {
//            params.height = height;
//        }
//       mTaboolaWidget.fetchContent();
//
//    }

    @Override
    public void onRefresh() {
        mTaboolaWidget.fetchContent();
        mSwipeRefreshLayout.setRefreshing(false);
        //Ask Taboola to update its content (refresh)
    }


}
