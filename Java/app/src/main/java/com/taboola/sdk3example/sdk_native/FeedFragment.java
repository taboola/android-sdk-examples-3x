package com.taboola.sdk3example.sdk_native;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taboola.android.Taboola;
import com.taboola.android.listeners.TBLNativeListener;
import com.taboola.android.tblnative.TBLNativePage;
import com.taboola.android.tblnative.TBLNativeUnit;
import com.taboola.android.tblnative.TBLPlacement;
import com.taboola.android.tblnative.TBLRecommendationRequestCallback;
import com.taboola.android.tblnative.TBLRecommendationsResponse;
import com.taboola.android.tblnative.TBLRequestData;
import com.taboola.sdk3example.R;

import java.util.ArrayList;

import static java.sql.DriverManager.println;


/**
 * To implement a Taboola Feed in "Native Integration" we use a RecyclerView to layout incoming items.
 */
public class FeedFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<Object> mData = new ArrayList<>();
    private TBLNativeUnit mNativeUnit = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_native_feed, container, false);
        mRecyclerView = root.findViewById(R.id.endless_feed_recycler_view);

        // Setup RecyclerView
        setupRecyclerView(root.getContext());

        // Create and return a Taboola Unit
        getTaboolaUnit();

        // Fetch content for Unit
        fetchInitialContent();

        return root;
    }

    /**
     * Basic setup for the RecyclerView with an emphasis on pulling additional items when user scrolls to bottom of View.
     */
    private void setupRecyclerView(Context context) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Load more Taboola content when reaching scroll bottom
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    loadNextRecommendationsBatch();
                }
            }
        });

        mRecyclerView.setAdapter(new NativeFeedAdapter(mData));
    }

    /**
     * Taboola "Native Integration" currently differentiates between first and subsequent content fetch calls.
     * This method fetches the initial few items for this Feed implementation.
     */
    private void fetchInitialContent() {
        // Define a fetch request (with desired number of content items in setRecCount())
        TBLRequestData requestData = new TBLRequestData().setRecCount(4);
        if (mNativeUnit != null) {
            mNativeUnit.fetchRecommendations(requestData, new TBLRecommendationRequestCallback() {
                @Override
                public void onRecommendationsFetched(TBLRecommendationsResponse tblRecommendationsResponse) {
                    println("Taboola | fetchInitialContent | onRecommendationsFetched");
                    // Send content to RecyclerView Adapter
                    addRecommendationToFeed(tblRecommendationsResponse);
                }

                @Override
                public void onRecommendationsFailed(Throwable throwable) {
                    println(String.format("Taboola | onRecommendationsFailed: %s", throwable.getLocalizedMessage()));
                }
            });
        }
    }

    /**
     * Taboola "Native Integration" currently differentiates between first and subsequent content fetch calls.
     * This method fetches additional items for this Feed implementation.
     */
    private void loadNextRecommendationsBatch() {
        mNativeUnit.getNextRecommendationsBatch(null, new TBLRecommendationRequestCallback() {
            @Override
            public void onRecommendationsFetched(TBLRecommendationsResponse tblRecommendationsResponse) {
                println("Taboola | loadNextRecommendationsBatch | onRecommendationsFetched");
                addRecommendationToFeed(tblRecommendationsResponse);
            }

            @Override
            public void onRecommendationsFailed(Throwable throwable) {
                println(String.format("Taboola | onRecommendationsFailed: %s", throwable.getLocalizedMessage()));
            }
        });
    }

    /**
     * This method parses the response with its items and adds them to the RecyclerView Adapter.
     */
    private void addRecommendationToFeed(TBLRecommendationsResponse recommendationsResponse) {
        TBLPlacement placement = recommendationsResponse.getPlacementsMap().values().iterator().next();

        if (placement != null) {
            // Update data structure
            mData.addAll(placement.getItems());

            // Update data in RecyclerView adapter
            int itemCount = mRecyclerView.getAdapter().getItemCount();
            if (itemCount > 0) {
                mRecyclerView.getAdapter().notifyItemRangeInserted(itemCount, placement.getItems().size());
            }
        }
    }

    /**
     * Define a Page that represents this screen, get a Unit from it, add it to screen and fetch its content
     * Notice: A Unit of unlimited items, called "Feed" in Taboola, can be set in TBL_PLACEMENT_TYPE.PAGE_BOTTOM only.
     */
    private void getTaboolaUnit() {
        // Define a page to control all Unit placements on this screen
        TBLNativePage nativePage = Taboola.getNativePage("text", "https://blog.taboola.com");

        // Define a single Unit to display
        mNativeUnit = nativePage.build("list_item", new TBLNativeListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                println(String.format("Taboola | onItemClick | isOrganic = %s", isOrganic));
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }
        });
    }
}
