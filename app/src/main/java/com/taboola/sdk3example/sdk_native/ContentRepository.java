package com.taboola.sdk3example.sdk_native;


import android.net.Uri;

import com.taboola.android.TBLPublisherInfo;
import com.taboola.android.listeners.TBLNativeListener;
import com.taboola.android.tblnative.TBLNativePage;
import com.taboola.android.tblnative.TBLNativeUnit;
import com.taboola.android.tblnative.TBLPlacement;
import com.taboola.android.tblnative.TBLPlacementRequest;
import com.taboola.android.tblnative.TBLRecommendationRequestCallback;
import com.taboola.android.tblnative.TBLRecommendationsRequest;
import com.taboola.android.tblnative.TBLRecommendationsResponse;
import com.taboola.android.tblnative.TBLRequestData;

import java.util.ArrayList;
import java.util.List;

public class ContentRepository {

    public interface ContentFetchCallback {
        void onRecommendationsFetched(TBLPlacement placement);

        void onRecommendationsFailed(Throwable t);
    }

    public static void getFirstContentBatch(TBLPublisherInfo publisher,
                                            TBLNativePage unit,
                                            final String placementName,
                                            int numberOfItems,
                                            int imageWidth,
                                            int imageHeight,
                                            TBLRequestData data,
                                            final ContentFetchCallback callback) {
        TBLRecommendationsRequest request =
                new TBLRecommendationsRequest("http://example.com", "text"); // todo set your parameters

        TBLPlacementRequest placementRequest =
                new TBLPlacementRequest(placementName, numberOfItems)
                        .setAvailable(true)
                        .setThumbnailSize(imageWidth, imageHeight);
        request.addPlacementRequest(placementRequest);




       // Taboola.init(publisher);


        TBLNativeUnit unit2=unit.build(placementName, new TBLNativeListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }

            @Override
            public void onImageLoadFailed(Uri uri, Exception e) {
                super.onImageLoadFailed(uri, e);
            }

            @Override
            public void onEvent(int actionType, String data) {
                super.onEvent(actionType, data);
            }
        });
        unit2.fetchRecommendations(data, new TBLRecommendationRequestCallback() {
                    @Override
                    public void onRecommendationsFetched(TBLRecommendationsResponse response) {
                        TBLPlacement firstPlacement = response.getPlacementsMap().get("AC-sports-");
                        callback.onRecommendationsFetched(firstPlacement);
                    }

                    @Override
                    public void onRecommendationsFailed(Throwable t) {
                        callback.onRecommendationsFailed(t);
                    }
                });
    }

    public static void getNextBatchForPlacement(TBLPublisherInfo publisher,
                                                TBLNativePage unit,
                                                TBLPlacement lastUsedPlacement
            , TBLRequestData data,
                                                final ContentFetchCallback callback) {



        TBLNativeUnit unit2=unit.build("AC-sports-", new TBLNativeListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }

            @Override
            public void onImageLoadFailed(Uri uri, Exception e) {
                super.onImageLoadFailed(uri, e);
            }

            @Override
            public void onEvent(int actionType, String data) {
                super.onEvent(actionType, data);
            }
        });
        unit2.fetchRecommendations(data, new TBLRecommendationRequestCallback() {
            @Override
            public void onRecommendationsFetched(TBLRecommendationsResponse response) {
                TBLPlacement nextPlacement = response.getPlacementsMap().values().iterator().next();
                callback.onRecommendationsFetched(nextPlacement);
            }

            @Override
            public void onRecommendationsFailed(Throwable t) {
                callback.onRecommendationsFailed(t);
            }
        });
    }

    public static List<FakeItemModel> getPlaceholderContent(int numberOfItems) {
        List<FakeItemModel> list = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            list.add(new FakeItemModel("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                    "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    ""));
        }
        return list;
    }

}