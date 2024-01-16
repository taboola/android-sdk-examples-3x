package com.taboola.sdk3example.sdk_classic;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.taboola.sdk3example.R;
import com.taboola.android.TBLClassicPage;
import com.taboola.android.TBLClassicUnit;
import com.taboola.android.Taboola;
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE;
import com.taboola.android.listeners.TBLClassicListener;
import com.taboola.android.utils.TBLSdkDetailsHelper;


import java.util.List;

public class EcommWidgetBelowArticleFragment extends Fragment {

    private static final String TAG = "EcommWidgetBelowArticle";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lv_sample, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TBLClassicPage tblClassicPage =
                Taboola.getClassicPage("https://blog.taboola.com", "article");

        TBLClassicUnit tblClassicUnitMiddle = createTaboolaWidget(tblClassicPage);

        ListView listView = view.findViewById(R.id.feed_lv);
        listView.setAdapter(new ListViewAdapter(tblClassicUnitMiddle));
    }



    public TBLClassicUnit createTaboolaWidget(TBLClassicPage tblClassicPage) {
        String uiMode = "ecomm-carousel-1";
        String placement = "Ecommerce widget";
        TBLClassicUnit tblClassicUnit = tblClassicPage.build(getContext(), placement, uiMode,
                TBL_PLACEMENT_TYPE.PAGE_BOTTOM, new TBLClassicListener() {
                    @Override
                    public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                        return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
                    }
                    @Override
                    public void onAdReceiveSuccess() {
                        super.onAdReceiveSuccess();
                        Log.d(TAG,"onAdReceiveSuccess");
                    }

                    @Override
                    public void onAdReceiveFail(String error) {
                        super.onAdReceiveFail(error);
                        Log.d(TAG,"onAdReceiveFail: " + error);
                    }
                });
        tblClassicUnit.fetchContent();
        return tblClassicUnit;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }


    static class ListViewAdapter extends BaseAdapter {

        private final List<ListItemsGenerator.FeedListItem> mData;
        private TBLClassicUnit tblClassicUnitMiddle;


        ListViewAdapter(TBLClassicUnit tblClassicUnitmiddleWidget) {
            mData = ListItemsGenerator.getGeneratedData(true);
            tblClassicUnitMiddle = tblClassicUnitmiddleWidget;
        }


        @Override
        public @ListItemsGenerator.FeedListItem.ItemType
        int getItemViewType(int position) {
            ListItemsGenerator.FeedListItem item = getItem(position);
            return item.type;
        }


        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public ListItemsGenerator.FeedListItem getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType) {
                case ListItemsGenerator.FeedListItem.ItemType.TABOOLA_ITEM:
                    return new ViewHolderTaboola(tblClassicUnitMiddle, viewType);
                default:
                case ListItemsGenerator.FeedListItem.ItemType.RANDOM_ITEM:
                    View appCompatImageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_item, parent, false);
                    return new RandomImageViewHolder(appCompatImageView, viewType);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            @ListItemsGenerator.FeedListItem.ItemType int viewType = getItemViewType(position);
            ViewHolder viewHolder;
            if (convertView == null || convertView.getTag() == null || ((ViewHolder) convertView.getTag()).mViewType != viewType) {
                viewHolder = onCreateViewHolder(parent, viewType);
                convertView = viewHolder.mView;
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            if (viewType == ListItemsGenerator.FeedListItem.ItemType.RANDOM_ITEM) {
                RandomImageViewHolder vh = (RandomImageViewHolder) viewHolder;
                ListItemsGenerator.FeedListItem item = getItem(position);
                ListItemsGenerator.RandomItem randomItem = (ListItemsGenerator.RandomItem) item;
                final ImageView imageView = vh.imageView;
                imageView.setBackgroundColor(randomItem.color);
                vh.textView.setText(randomItem.randomText);
            }


            return convertView;
        }


        static class RandomImageViewHolder extends ViewHolder {
            private final ImageView imageView;
            private final TextView textView;

            RandomImageViewHolder(View view, int viewType) {
                super(view, viewType);
                imageView = view.findViewById(R.id.feed_item_iv);
                textView = view.findViewById(R.id.feed_item_tv);
            }
        }

        static abstract class ViewHolder {

            private final @ListItemsGenerator.FeedListItem.ItemType
            int mViewType;
            View mView;

            ViewHolder(View view, int viewType) {
                mView = view;
                this.mViewType = viewType;
                view.setTag(this);
            }
        }

        static class ViewHolderTaboola extends ViewHolder {
            ViewHolderTaboola(View view, int viewType) {
                super(view, viewType);
            }
        }

    }
}


