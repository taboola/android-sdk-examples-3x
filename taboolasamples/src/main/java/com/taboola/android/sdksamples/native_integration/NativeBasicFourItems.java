package com.taboola.android.sdksamples.native_integration;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.taboola.android.Taboola;
import com.taboola.android.api.TBPlacement;
import com.taboola.android.api.TBPlacementRequest;
import com.taboola.android.api.TBRecommendationItem;
import com.taboola.android.api.TBRecommendationRequestCallback;
import com.taboola.android.api.TBRecommendationsRequest;
import com.taboola.android.api.TBRecommendationsResponse;
import com.taboola.android.api.TBTextView;
import com.taboola.android.api.TaboolaNative;
import com.taboola.android.listeners.TaboolaNativeListener;
import com.taboola.android.sdksamples.R;
import com.taboola.android.sdksamples.demo_infra.data.Const;

import java.util.Map;

/**
 * This Fragment shows a basic flow of a Taboola "Native" integration.
 * The main idea is that you, as the developer, will pull content information from Taboola and display it yourself, according to your design.
 */
public class NativeBasicFourItems extends Fragment {

    private static final String TAG = NativeBasicFourItems.class.getSimpleName();

    private LinearLayout adContainer; //A layout this demo uses to contain the different Views associated with Taboola "Native".
    private View attributionView; //A View that allows displaying attribution information for content.
    private TaboolaNative mTaboolaNative; //The Actual "Native" Widget instance.

    public NativeBasicFourItems() {
        //Create a "Native" widget
        mTaboolaNative = Taboola.getTaboolaNativeBuilder(Const.DefaultProperties.SOURCE_TYPE, "https://blog.taboola.com")
                .build(Const.DefaultProperties.PLACEMENT_MIDDLE, new TaboolaNativeListener() {});
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_widget_api, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adContainer = view.findViewById(R.id.ad_container);

        //Display attribution information
        attributionView = view.findViewById(R.id.attribution_view);
        attributionView.setOnClickListener(v -> mTaboolaNative.handleAttributionClick(getContext()));

        //Define Widget properties
        String placementName = "article";
        int recCount = 4; // specify how many recommendations should be returned

        Point mScreenSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(mScreenSize); // Screen size to be used as part of ThumbnailSize calculation in placement request

        String pageUrl = "http://example.com";
        String sourceType = "text";

        //Prepare a placement request
        TBPlacementRequest placementRequest = new TBPlacementRequest(placementName, recCount)
                .setThumbnailSize(mScreenSize.x, (mScreenSize.y / 3)); // ThumbnailSize is optional

        //Prepare the recommendations request (wraps the TBPlacementRequest)
        TBRecommendationsRequest recommendationsRequest = new TBRecommendationsRequest(pageUrl, sourceType);
        recommendationsRequest.addPlacementRequest(placementRequest);

        //Fetch recommendations
        mTaboolaNative.fetchRecommendations(recommendationsRequest, new TBRecommendationRequestCallback() {
            @Override
            public void onRecommendationsFetched(TBRecommendationsResponse response) {
                //A map of the placements returned from Taboola servers
                Map<String, TBPlacement> placementsMap = response.getPlacementsMap();

                //This method extracts the different placements from Taboola servers response
                extractViewsFromItems(placementsMap, placementName);
            }

            @Override
            public void onRecommendationsFailed(Throwable throwable) {
                Log.d(TAG, "Failed: " + throwable.getMessage());
            }
        });
    }

    /**
     * Method to extract recommendation items from placements map.
     * Each placement from server response contains item/s that, in turn, contain the different View objects for displaying content.
     *
     * @param placementsMap
     * @param placementName
     */
    private void extractViewsFromItems(Map<String, TBPlacement> placementsMap, String placementName) {
        TBPlacement placement = placementsMap.get(placementName);
        if (placement != null) {
            for (TBRecommendationItem item : placement.getItems()) {

                //Attribution info
                attributionView.setVisibility(View.VISIBLE);

                //Thumbnail, title
                adContainer.addView(item.getThumbnailView(getContext()));
                adContainer.addView(item.getTitleView(getContext()));

                //Branding
                TBTextView brandingView = item.getBrandingView(getContext());
                if (brandingView != null) { // If branding text is not available null is returned
                    adContainer.addView(brandingView);
                }

                // Item Description
                TBTextView descriptionView = item.getDescriptionView(getContext());
                if (descriptionView != null) {
                    adContainer.addView(descriptionView);
                }

                View v = new View(getContext());
                v.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        2
                ));
                v.setBackgroundColor(Color.parseColor("#B3B3B3"));

                adContainer.addView(v);
            }
        }
    }
}
