package com.taboola.sdk3example.sdk_native;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.taboola.android.Taboola;
import com.taboola.android.listeners.TBLNativeListener;
import com.taboola.android.tblnative.TBLImageView;
import com.taboola.android.tblnative.TBLNativePage;
import com.taboola.android.tblnative.TBLNativeUnit;
import com.taboola.android.tblnative.TBLPlacement;
import com.taboola.android.tblnative.TBLRecommendationItem;
import com.taboola.android.tblnative.TBLRecommendationRequestCallback;
import com.taboola.android.tblnative.TBLRecommendationsResponse;
import com.taboola.android.tblnative.TBLRequestData;
import com.taboola.android.tblnative.TBLTextView;
import com.taboola.sdk3example.R;

import static java.sql.DriverManager.println;

public class WidgetFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_native_widget, container, false);
        LinearLayout contentLayout = root.findViewById(R.id.data_container);

        // Create and return a Taboola Unit
        TBLNativeUnit nativeUnit = getTaboolaUnit();

        // Fetch content for Unit
        // Note that by passing setRecCount 4 we are asking Taboola to return 4 items
        TBLRequestData requestData = new TBLRequestData().setRecCount(4);
        if (nativeUnit != null) {
            nativeUnit.fetchRecommendations(requestData, new TBLRecommendationRequestCallback() {
                @Override
                public void onRecommendationsFetched(TBLRecommendationsResponse tblRecommendationsResponse) {
                    println("Taboola | fetchInitialContent | onRecommendationsFetched");
                    // Add response content to layout
                    displayContent(contentLayout, tblRecommendationsResponse);
                }

                @Override
                public void onRecommendationsFailed(Throwable throwable) {
                    println(String.format("Taboola | onRecommendationsFailed: %s", throwable.getLocalizedMessage()));
                }
            });
        }

        return root;
    }

    /**
     * Define a Page that represents this screen, get a Unit from it, add it to screen and fetch its content
     * Notice: A Unit of unlimited items, called "Feed" in Taboola, can be set in TBL_PLACEMENT_TYPE.PAGE_BOTTOM only.
     */
    private TBLNativeUnit getTaboolaUnit() {
        // Define a page to control all Unit placements on this screen
        TBLNativePage nativePage = Taboola.getNativePage("text", "https://blog.taboola.com");

        return nativePage.build("list_item", new TBLNativeListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                println(String.format("Taboola | onItemClick | isOrganic = %s", isOrganic));
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }
        });
    }

    /**
     * In Native integrations fetch request returns multiple Android Views.
     * It is up to you to lay them out.
     */
    private void displayContent(LinearLayout contentLayout, TBLRecommendationsResponse tblRecommendationsResponse) {
        if (tblRecommendationsResponse == null || tblRecommendationsResponse.getPlacementsMap() == null) {
            println("Error: No recommendations returned from server.");
            return;
        }
        TBLPlacement placement = tblRecommendationsResponse.getPlacementsMap().values().iterator().next();

        if (placement != null && placement.getItems().size() > 0) {

            // We asked for 4 item so we go over all items returned from Taboola
            // NOTE: In this example, we search for the items relevant for the only placement in this page

            for (TBLRecommendationItem item : placement.getItems()) {
                Context context = contentLayout.getContext();
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TBLImageView thumbnailView = item.getThumbnailView(context);
                TBLTextView titleView = item.getTitleView(context);
                TBLTextView brandingView = item.getBrandingView(context);
                TBLTextView descriptionView = item.getDescriptionView(context);
                contentLayout.addView(thumbnailView);
                contentLayout.addView(titleView, layoutParams);
                if (brandingView != null) {
                    contentLayout.addView(brandingView, layoutParams);
                }
                if (descriptionView != null) {
                    contentLayout.addView(descriptionView, layoutParams);
                }
                contentLayout.addView(new Space(context), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20));
            }
        }
    }
}
