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
import com.taboola.android.TBLOnScrollChangedListenerImpl;
import com.taboola.android.Taboola;
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE;
import com.taboola.android.listeners.TBLClassicListener;
import com.taboola.android.utils.TBLSdkDetailsHelper;
import java.util.List;


public class FeedInsideRecyclerViewCustomFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private TBLOnScrollChangedListenerImpl scrollChangedListener;
    private static String TAG="FeedInsideReccylerViewCustomFragment";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new CustomAdapter(scrollChangedListener);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rv_sample, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.feed_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
       scrollChangedListener =new TBLOnScrollChangedListenerImpl(view);
        initScrollListeners();


    }

    private void initScrollListeners() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    // In this case: there is nothing to scroll in publisher content and taboola should get the scroll control
                    mAdapter.enableWidgetScrolling(true);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        TBLClassicPage tblClassicPage =
                Taboola.getClassicPage("https://blog.taboola.com", "text");

        TBLClassicUnit taboolaWidget = tblClassicPage.build(this.getContext(),"Mid Article","alternating-widget-1x2",TBL_PLACEMENT_TYPE.FEED,new TBLClassicListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                Log.d(TAG,"onItemClick"+itemId);
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);

            }

            @Override
            public void onTaboolaWidgetOnTop() {
                super.onTaboolaWidgetOnTop();
                mAdapter.enableWidgetScrolling(false);
            }
        });
    }



    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onScrollListenerRemoved");
        mAdapter.removeOnScrollListener();
    }


    static class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        private final List<ListItemsGenerator.FeedListItem> mData;
        private TBLClassicUnit tblClassicUnit;
        private TBLOnScrollChangedListenerImpl tblOnScrollChangedListener;
        private boolean EnableWidgetScroll;




        CustomAdapter(TBLOnScrollChangedListenerImpl scrollToTopListener) {
            mData = ListItemsGenerator.getGeneratedData(false);
            this.tblOnScrollChangedListener = scrollToTopListener;

        }

        public boolean isEnableWidgetScroll() {
            return EnableWidgetScroll;
        }

        public void removeOnScrollListener() {
            if (tblClassicUnit != null) {
//                tblClassicUnit.removeListener();
                tblClassicUnit = null;
            }
        }

        public   void enableWidgetScrolling(boolean enableWidgetScroll) {
            EnableWidgetScroll = enableWidgetScroll;

            if (tblClassicUnit != null) {
                tblClassicUnit.setInterceptScroll(EnableWidgetScroll);
                tblClassicUnit.setScrollEnabled(EnableWidgetScroll);
            }

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
                case ListItemsGenerator.FeedListItem.ItemType.TABOOLA_ITEM:
                    TBLClassicPage tblClassicPage =
                            Taboola.getClassicPage("https://blog.taboola.com", "text");

                    TBLClassicUnit taboolaWidget = tblClassicPage.build(parent.getContext(), "Mid Article","alternating-widget-1x2",TBL_PLACEMENT_TYPE.FEED,new TBLClassicListener() {
                        @Override
                        public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                            return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);

                        }

                        @Override
                        public void onTaboolaWidgetOnTop() {
                            super.onTaboolaWidgetOnTop();
                            enableWidgetScrolling(false);
                        }
                    });
                    return new ViewHolderTaboola(taboolaWidget);

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
            } else if (item.type == ListItemsGenerator.FeedListItem.ItemType.TABOOLA_ITEM) {
                ViewHolderTaboola viewHolderTaboola = (ViewHolderTaboola) holder;
                if (viewHolderTaboola.tblClassicUnit.isScrolledToTop() != EnableWidgetScroll) {
                    viewHolderTaboola.tblClassicUnit.setScrollEnabled(EnableWidgetScroll);
                }
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
            private TBLClassicUnit tblClassicUnit;


            ViewHolderTaboola(TBLClassicUnit taboolaWidget) {
                super(taboolaWidget);
                tblClassicUnit = taboolaWidget;
                int height = TBLSdkDetailsHelper.getDisplayHeight(taboolaWidget.getContext()) * 2;
                ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                taboolaWidget.setLayoutParams(params);
                taboolaWidget
                        .setPublisherName("sdk-tester-demo")
                        .setPageType("article")
                        .setPageUrl("https://blog.taboola.com")
                        .setPlacement("Feed without video")
                        .setMode("thumbs-feed-01")
                        .setTargetType("mix");
                taboolaWidget.fetchContent();
            }
        }

    }

}

