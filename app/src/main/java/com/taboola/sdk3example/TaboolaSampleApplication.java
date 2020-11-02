package com.taboola.sdk3example;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.taboola.android.TBLPublisherInfo;
import com.taboola.android.Taboola;

import java.util.HashMap;
import java.util.Map;


public class TaboolaSampleApplication extends  Application{


   public  TBLPublisherInfo tblPublisherInfo;
    @Override
    public void onCreate() {
        super.onCreate();

         tblPublisherInfo  = new TBLPublisherInfo("sdk-tester-demo").setApiKey("30dfcf6b094361ccc367bbbef5973bdaa24dbcd6");
        Map<String, String> taboolaExtraProperties = new HashMap<>();
        taboolaExtraProperties.put("enableFullRawDataResponse", "true");
        taboolaExtraProperties.put("allowNonOrganicClickOverride", "true");
        Drawable imagePlaceholder = ResourcesCompat.getDrawable(getApplicationContext().getResources(), R.drawable.image_placeholder, null);
        Taboola.init(tblPublisherInfo);
    }

}