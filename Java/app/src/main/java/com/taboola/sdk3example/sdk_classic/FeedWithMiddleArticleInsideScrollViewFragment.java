package com.taboola.sdk3example.sdk_classic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taboola.sdk3example.R;
import com.taboola.android.TBLClassicPage;
import com.taboola.android.TBLClassicUnit;
import com.taboola.android.Taboola;
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE;
import com.taboola.android.listeners.TBLClassicListener;
import com.taboola.android.utils.TBLSdkDetailsHelper;
import java.util.HashMap;


public class FeedWithMiddleArticleInsideScrollViewFragment extends Fragment  {
    private static final String TAG = "DEBUG";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_standard, container, false);

        TBLClassicPage tblClassicPage=Taboola.getClassicPage(getContext(), "https://blog.taboola.com", "article");
        buildMiddleArticleWidget(view.findViewById(R.id.taboola_widget_middle), tblClassicPage);
        buildBelowArticleWidget(view.findViewById(R.id.taboola_widget_below_article), tblClassicPage);
        return view;
    }
    private void buildMiddleArticleWidget(TBLClassicUnit tblClassicUnit, TBLClassicPage tblClassicPage) {
        tblClassicUnit
                .setPublisherName("sdk-tester-demo")
                .setPageType("article")
                .setPageUrl("https://blog.taboola.com")
                .setPlacement("Mid Article")
                .setMode("alternating-widget-without-video-1x1")
                .setTargetType("mix"); // setViewId - used in order to prevent duplicate recommendations between widgets on the same page view


        TBLClassicListener tblClassicListener;
        tblClassicListener=new TBLClassicListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }
        };
        tblClassicPage.addUnitToPage(tblClassicUnit,"Mid Article","alternating-widget-without-video-1x1", TBL_PLACEMENT_TYPE.FEED,tblClassicListener);
        tblClassicUnit.fetchContent();

    }

    private void buildBelowArticleWidget(TBLClassicUnit tblClassicUnit, TBLClassicPage tblClassicPage) {
        tblClassicUnit
                .setPublisherName("sdk-tester-demo")
                .setPageType("article")
                .setPageUrl("https://blog.taboola.com")
                .setPlacement("Feed without video")
                .setMode("thumbs-feed-01")
                .setTargetType("mix")
                .setInterceptScroll(true);

        tblClassicUnit.getLayoutParams().height = TBLSdkDetailsHelper.getDisplayHeight( tblClassicUnit.getContext()) * 2;
        HashMap<String, String> extraProperties = new HashMap<>();
        extraProperties.put("useOnlineTemplate", "true");
        extraProperties.put("detailedErrorCodes", "true");
        TBLClassicListener tblClassicListener;
        tblClassicListener=new TBLClassicListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                Log.d(TAG,"onItemClick");
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }
        };

        tblClassicUnit.setUnitExtraProperties(extraProperties);
        tblClassicPage.addUnitToPage( tblClassicUnit,"Feed without video","thumbs-feed-01", TBL_PLACEMENT_TYPE.FEED,tblClassicListener);
        tblClassicUnit.setUnitExtraProperties(extraProperties);
        tblClassicUnit.fetchContent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
}