package com.e.sdk_example_app_30.sdk_native.utils;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.taboola.android.tblnative.TBLRecommendationItem;
import com.taboola.android.utils.TBLUrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemUtil {


    private static final String TAG = "ItemUtil";
    private static final String TB_ITEM_KEY_THUMBNAIL = "thumbnail";


    @Nullable
    public static String getItemThumbnailUrl(@NonNull TBLRecommendationItem item, int imageWidth, int imageHeight) {
        try {
            final String thumbnail = item.getExtraDataMap().get(TB_ITEM_KEY_THUMBNAIL);
            if (TextUtils.isEmpty(thumbnail)) {
                Log.e(TAG, "getItemThumbnailUrl: missing thumbnail url");
                return null;
            }
            final JSONArray thumbnailJsonArray = new JSONArray(thumbnail);
            if (thumbnailJsonArray.length() > 0) {
                JSONObject thumbnailJsonObj = (JSONObject) thumbnailJsonArray.get(0);
                String imageUrl = thumbnailJsonObj.getString("url");
                return TBLUrlUtils.replaceImageSizeInUrl(imageUrl, imageHeight, imageWidth);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getItemThumbnailUrl: ", e);
        }
        return null;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
