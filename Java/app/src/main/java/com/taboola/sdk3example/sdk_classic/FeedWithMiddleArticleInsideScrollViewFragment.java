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
    private static final String TAG = "FeedWithMiddleArticleInsideScrollViewFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_standard, container, false);

        // Create a Taboola page
        TBLClassicPage tblClassicPage=Taboola.getClassicPage(getContext(), "https://blog.taboola.com", "article");

        // Create Taboola units for the page
        TBLClassicUnit tblClassicUnitMiddleArticle = view.findViewById(R.id.taboola_widget_middle);
        TBLClassicUnit tblClassicUnitBelowArticle = view.findViewById(R.id.taboola_widget_below_article);

        // Configure and add the units the page
        configureMiddleArticleWidget(tblClassicUnitMiddleArticle, tblClassicPage);
        configureBelowArticleWidget(tblClassicUnitBelowArticle, tblClassicPage);

        // Fetch content for each unit
        tblClassicUnitMiddleArticle.fetchContent();
        tblClassicUnitBelowArticle.fetchContent();
        return view;
    }
    private void configureMiddleArticleWidget(TBLClassicUnit tblClassicUnit, TBLClassicPage tblClassicPage) {
        tblClassicUnit.setTargetType("mix");

        TBLClassicListener tblClassicListener =new TBLClassicListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }
        };
        tblClassicPage.addUnitToPage(tblClassicUnit,
                "Mid Article",
                "alternating-widget-without-video-1x1",
                TBL_PLACEMENT_TYPE.FEED,
                tblClassicListener);
       ;

    }

    private void configureBelowArticleWidget(TBLClassicUnit tblClassicUnit, TBLClassicPage tblClassicPage) {
        tblClassicUnit.setTargetType("mix");
        tblClassicUnit.setInterceptScroll(true);

        tblClassicUnit.getLayoutParams().height = TBLSdkDetailsHelper.getDisplayHeight( tblClassicUnit.getContext()) * 2;
        HashMap<String, String> extraProperties = new HashMap<>();
        extraProperties.put("useOnlineTemplate", "true");
        extraProperties.put("detailedErrorCodes", "true");
        TBLClassicListener tblClassicListener = new TBLClassicListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                  return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }
        };

        tblClassicUnit.setUnitExtraProperties(extraProperties);
        tblClassicPage.addUnitToPage( tblClassicUnit,"Feed without video","thumbs-feed-01", TBL_PLACEMENT_TYPE.FEED,tblClassicListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}