package com.taboola.sdk3example.sdk_native;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.taboola.sdk3example.R;
import com.taboola.android.TBLPublisherInfo;
import com.taboola.android.Taboola;
import com.taboola.android.tblnative.TBLNativePage;
import com.taboola.android.tblnative.TBLPlacement;
import com.taboola.android.tblnative.TBLRequestData;


import java.util.ArrayList;
import java.util.List;


public class TabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final int NUMBER_OF_ITEMS = 4;

    private AppConfig appConfig;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;


    private FeedAdapter feedAdapter;
    private LinearLayoutManager layoutManager;

    private TBLPlacement lastUsedPlacement;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tab, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.rv_main_category_tab);

        appConfig = new SampleApplication().getAppConfig(getContext());

        feedAdapter = new FeedAdapter(getContext());

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        });

        fetchInitialContent();
        fetchNextPage();
        return view;
    }

    public void onItemClicked(@NonNull String clickUrl) {
        if (feedAdapter != null) {
            feedAdapter.onItemClicked(clickUrl);
        }
    }

    private void fetchInitialContent() {
        int thumbnailHeight = (int) getContext().getResources()
                .getDimension(R.dimen.height_feed_article_short_thumbnail);
        int thumbnailWidth = (int) getContext().getResources()
                .getDimension(R.dimen.width_feed_article_short_thumbnail);


        TBLNativePage tblNativePage= Taboola.getNativePage("text","http://example.com");


        TBLPublisherInfo info=new TBLPublisherInfo(appConfig.getPublisher());

        TBLRequestData tblRequestData=new TBLRequestData();
        tblRequestData.setRecCount(1);


        ContentRepository.getFirstContentBatch(info, tblNativePage,getPlacementName(), NUMBER_OF_ITEMS,
                thumbnailWidth, thumbnailHeight, tblRequestData,
                new ContentRepository.ContentFetchCallback() {
                    @Override
                    public void onRecommendationsFetched(TBLPlacement placement) {
                        onContentFetched(placement);
                    }

                    @Override
                    public void onRecommendationsFailed(Throwable t) {
                        onContentFetchFailed(t);
                    }
                });
    }

    private void fetchNextPage() {

        TBLPublisherInfo info=new TBLPublisherInfo(appConfig.getPublisher());

        TBLRequestData tblRequestData=new TBLRequestData();
        tblRequestData.setRecCount(5);

        TBLNativePage tblNativePage= Taboola.getNativePage("text","http://example.com");

        ContentRepository.getNextBatchForPlacement(info, tblNativePage,lastUsedPlacement,
                tblRequestData,
                new ContentRepository.ContentFetchCallback() {
                    @Override
                    public void onRecommendationsFetched(TBLPlacement placement) {
                        onContentFetched(placement);
                    }

                    @Override
                    public void onRecommendationsFailed(Throwable t) {
                        onContentFetchFailed(t);
                    }
                });
    }

    private void onContentFetched(TBLPlacement placement) {
        swipeRefreshLayout.setRefreshing(false);
        lastUsedPlacement = placement;
        lastUsedPlacement.prefetchThumbnails();


        List<Object> itemsAsObjectsList = new ArrayList<>(placement.getItems().size());

        itemsAsObjectsList.addAll(placement.getItems());
        itemsAsObjectsList.addAll(ContentRepository.getPlaceholderContent(4));

        feedAdapter.addItems(itemsAsObjectsList);
        feedAdapter.notifyDataSetChanged();
    }

    private void onContentFetchFailed(Throwable t) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), "Failed to get items: " + t.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        feedAdapter.clearItems();
        feedAdapter.notifyDataSetChanged();
        fetchInitialContent();
        fetchNextPage();
    }

    @Nullable
    public String getPlacementName() {

            return "AC-sports-";

    }
}
