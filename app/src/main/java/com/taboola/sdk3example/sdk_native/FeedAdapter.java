package com.taboola.sdk3example.sdk_native;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.taboola.sdk3example.R;
import com.taboola.sdk3example.sdk_native.utils.DateTimeUtil;
import com.taboola.sdk3example.sdk_native.utils.ItemUtil;
import com.taboola.android.tblnative.TBLImageView;
import com.taboola.android.tblnative.TBLRecommendationItem;
import com.taboola.android.tblnative.TBLTextView;


import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_SHORT_ITEM = 0;
    private final int TYPE_FAKE_ITEM = 1;

    private List<Object> feedItems = new ArrayList<>();

    private Context context;

    FeedAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_SHORT_ITEM: {
                ConstraintLayout shortItemLayout = (ConstraintLayout) inflater
                        .inflate(R.layout.item_article_short, parent, false);
                return new ShortItemViewHolder(shortItemLayout);
            }
            case TYPE_FAKE_ITEM: {
                ConstraintLayout fakeItemLayout = (ConstraintLayout) inflater
                        .inflate(R.layout.item_fake, parent, false);
                return new FakeItemViewHolder(fakeItemLayout);
            }
            default: {
                throw new IllegalStateException("Unknown view type: " + viewType);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ShortItemViewHolder) {
            ((ShortItemViewHolder) viewHolder).bind((TBLRecommendationItem) feedItems.get(position));
        } else if (viewHolder instanceof FakeItemViewHolder) {
            ((FakeItemViewHolder) viewHolder).bind((FakeItemModel) feedItems.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (feedItems.get(position) instanceof TBLRecommendationItem) {
            return TYPE_SHORT_ITEM;
        } else if (feedItems.get(position) instanceof FakeItemModel) {
            return TYPE_FAKE_ITEM;
        } else {
            throw new RuntimeException("Unknown item type");
        }
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof ShortItemViewHolder) {
            ((ShortItemViewHolder) holder).onViewRecycled();
        }
    }

    void addItems(List<Object> newItems) {
        feedItems.addAll(newItems);
    }

    void clearItems() {
        feedItems.clear();
    }

    void onItemClicked(@NonNull final String clickUrl) {
        TBLRecommendationItem clickedItem = findItemByClickUrl(feedItems, clickUrl);
        if (clickedItem == null) {
            return; // item that was clicked is not in this fragment
        }




        Bundle bundle = new Bundle();
        int imageHeight = (int) context.getResources().getDimension(R.dimen.summary_page_thumbnail_height);
        bundle.putString("url", ItemUtil.getItemThumbnailUrl(clickedItem, ItemUtil.getScreenWidth(), imageHeight));
        bundle.putString("clickUrl", clickUrl);
        bundle.putParcelable("clickedItem", clickedItem);

        Intent intent = new Intent(context, SummaryActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }



    @Nullable
    private TBLRecommendationItem findItemByClickUrl(List<Object> feedItems, String clickUrl) {
        for (Object feedItem : feedItems) {
            if (feedItem instanceof TBLRecommendationItem) {
                TBLRecommendationItem tbRecommendationItem = (TBLRecommendationItem) feedItem;

                String currentItemUrl = (tbRecommendationItem).getExtraDataMap().get("url");
                if (("\"" + clickUrl + "\"").equals(currentItemUrl)) {
                    return tbRecommendationItem;
                }

            } // else it's a fake item
        }
        return null;
    }

    class ShortItemViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout itemTitleContainer;
        private FrameLayout itemImageContainer;
        private FrameLayout itemBrandingContainer;
        private TextView itemTime;

        ShortItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitleContainer = itemView.findViewById(R.id.container_title);
            itemImageContainer = itemView.findViewById(R.id.container_image);
            itemBrandingContainer = itemView.findViewById(R.id.container_branding);
            itemTime = itemView.findViewById(R.id.tv_time);
        }

        void bind(TBLRecommendationItem item) {
            itemTitleContainer.removeAllViews();
            itemImageContainer.removeAllViews();
            itemBrandingContainer.removeAllViews();

            int thumbnailHeight = (int) context.getResources().getDimension(R.dimen.height_feed_article_short_thumbnail);
            int thumbnailWidth = (int) context.getResources().getDimension(R.dimen.width_feed_article_short_thumbnail);

            // customize the look of Taboola views to fit your app's theme
            final TBLImageView thumbnail = item.getThumbnailView(context, thumbnailHeight, thumbnailWidth);
            thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

            TBLTextView title = item.getTitleView(context);
            title.setMaxLines(3);
            title.setEllipsize(TextUtils.TruncateAt.END);

            TBLTextView brandingLabel = item.getBrandingView(context);
            brandingLabel.setMaxLines(1);
            brandingLabel.setLayoutParams(new FrameLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            brandingLabel.setEllipsize(TextUtils.TruncateAt.END);
            brandingLabel.setGravity(Gravity.CENTER);

            String timestamp = DateTimeUtil.getTimeBetween(item.getExtraDataMap().get("created"));
            itemTime.setText(timestamp);

            // remove our views from previous containers if necessary
            if (thumbnail.getParent() != null) {
                ((ViewGroup) thumbnail.getParent()).removeView(thumbnail);
            }
            itemImageContainer.addView(thumbnail);

            if (title.getParent() != null) {
                ((ViewGroup) title.getParent()).removeView(title);
            }
            itemTitleContainer.addView(title);

            if (brandingLabel.getParent() != null) {
                ((ViewGroup) brandingLabel.getParent()).removeView(brandingLabel);
            }
            itemBrandingContainer.addView(brandingLabel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // handle a click of a user on an empty space within the item layout
                    thumbnail.handleClick();
                }
            });

        }

        void onViewRecycled() {
            itemTitleContainer.removeAllViews();
            itemImageContainer.removeAllViews();
            itemBrandingContainer.removeAllViews();
            itemView.setOnClickListener(null);
        }
    }

    class FakeItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemDescription;
        private ImageView itemImage;

        FakeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemDescription = itemView.findViewById(R.id.fake_description);
            itemImage = itemView.findViewById(R.id.fake_background);
        }

        void bind(FakeItemModel item) {
            itemDescription.setText(item.getTitle());
            itemImage.setBackgroundColor(Color.BLUE);
        }
    }
}
