package com.taboola.sdk3example.sdk_classic;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taboola.sdk3example.R;
import com.taboola.android.TBLClassicPage;
import com.taboola.android.TBLClassicUnit;
import com.taboola.android.Taboola;
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE;
import com.taboola.android.listeners.TBLClassicListener;
import com.taboola.android.utils.TBLSdkDetailsHelper;

import java.util.HashMap;
import java.util.List;

public class FeedWithMiddleArticleInsideRecyclerViewFragment extends Fragment  {

    private static final String TAG = "FeedWithMiddleArticle";
    private static final String TABOOLA_VIEW_ID = "123456";

    private static TBLClassicUnit mMiddleTaboolaWidget;
    private static TBLClassicUnit mBottomTaboolaWidget;

//    private GlobalNotificationReceiver mGlobalNotificationReceiver = new GlobalNotificationReceiver();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMiddleTaboolaWidget = createTaboolaWidget(inflater.getContext(), false);
        mBottomTaboolaWidget = createTaboolaWidget(inflater.getContext(), true);

        buildMiddleArticleWidget(mMiddleTaboolaWidget);
        return inflater.inflate(R.layout.fragment_rv_sample, container, false);
    }

    static TBLClassicUnit createTaboolaWidget(Context context, boolean infiniteWidget) {
        TBLClassicPage tblClassicPage =
                Taboola.getClassicPage(context, "https://blog.taboola.com", "text");

        TBLClassicUnit tblClassicUnit = tblClassicPage.build("Mid Article", "alternating-widget-without-video-1x1", TBL_PLACEMENT_TYPE.FEED, new TBLClassicListener() {
                    @Override
                    public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                        return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
                    }

            @Override
            public void onAdReceiveSuccess() {
                super.onAdReceiveSuccess();
                Log.d(TAG,"onAdReceiveSuccess");
                    buildBottomArticleWidget(mBottomTaboolaWidget); //fetch content for the 2nd taboola asset only after completion of 1st item

            }
        });

        int height = infiniteWidget ? TBLSdkDetailsHelper.getDisplayHeight(context) * 2 : ViewGroup.LayoutParams.WRAP_CONTENT;
        tblClassicUnit.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        return tblClassicUnit;
    }


    private static void buildMiddleArticleWidget(TBLClassicUnit taboolaWidget) {
        taboolaWidget
                .setPublisherName("sdk-tester-demo")
                .setPageType("article")
                .setPageUrl("https://blog.taboola.com")
                .setPlacement("Mid Article")
                .setMode("alternating-widget-without-video-1x1")
                .setTargetType("mix")
                .setPageId(TABOOLA_VIEW_ID);

        HashMap<String, String> extraProperties = new HashMap<>();
        extraProperties.put("useOnlineTemplate", "true");
        extraProperties.put("detailedErrorCodes", "true");

        taboolaWidget.setUnitExtraProperties(extraProperties);
        taboolaWidget.fetchContent();
    }

    private static void buildBottomArticleWidget(TBLClassicUnit taboolaWidget) {
        taboolaWidget
                .setPublisherName("sdk-tester-demo")
                .setPageType("article")
                .setPageUrl("https://blog.taboola.com")
                .setPlacement("Feed without video")
                .setMode("thumbs-feed-01")
                .setTargetType("mix")
                .setPageId(TABOOLA_VIEW_ID);

        taboolaWidget.setInterceptScroll(true);

        HashMap<String, String> extraProperties = new HashMap<>();
        extraProperties.put("useOnlineTemplate", "true");

        extraProperties.put("detailedErrorCodes", "true");

        taboolaWidget.setUnitExtraProperties(extraProperties);
        taboolaWidget.fetchContent();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.feed_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapter(mMiddleTaboolaWidget, mBottomTaboolaWidget));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<ListItemsGenerator.FeedListItem> mData;
        private final TBLClassicUnit mMiddleTaboolaWidget;
        private final TBLClassicUnit mBottomTaboolaWidget;


        RecyclerViewAdapter(TBLClassicUnit taboolaWidget, TBLClassicUnit taboolaWidgetBottom) {
            mData = ListItemsGenerator.getGeneratedDataForWidgetDynamic(true);
            mMiddleTaboolaWidget = taboolaWidget;
            mBottomTaboolaWidget = taboolaWidgetBottom;
        }


        @Override
        public int getItemViewType(int position) {
            ListItemsGenerator.FeedListItem item = getItem(position);
            return item.type;
        }


        @Override
        public int getItemCount() {
            return mData.size();
        }

        @NonNull
        private ListItemsGenerator.FeedListItem getItem(int position) {
            return mData.get(position);
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType) {

                case ListItemsGenerator.FeedListItem.ItemType.TABOOLA_MID_ITEM:
                    return new ViewHolderTaboola(mMiddleTaboolaWidget);

                case ListItemsGenerator.FeedListItem.ItemType.TABOOLA_ITEM:
                    return new ViewHolderTaboola(mBottomTaboolaWidget);

                default:
                case ListItemsGenerator.FeedListItem.ItemType.RANDOM_ITEM:
                    View appCompatImageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_item, parent, false);
                    return new RandomImageViewHolder(appCompatImageView);
            }
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ListItemsGenerator.FeedListItem item = getItem(position);

            if (item.type == ListItemsGenerator.FeedListItem.ItemType.RANDOM_ITEM) {
                RandomImageViewHolder vh = (RandomImageViewHolder) holder;
                ListItemsGenerator.RandomItem randomItem = (ListItemsGenerator.RandomItem) item;
                final ImageView imageView = vh.imageView;
                imageView.setBackgroundColor(randomItem.color);
                vh.textView.setText(randomItem.randomText);
            }
        }


        static class RandomImageViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            private final TextView textView;

            RandomImageViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.feed_item_iv);
                textView = view.findViewById(R.id.feed_item_tv);
            }
        }

        static class ViewHolderTaboola extends RecyclerView.ViewHolder {

            ViewHolderTaboola(View view) {
                super(view);
            }
        }
    }

}