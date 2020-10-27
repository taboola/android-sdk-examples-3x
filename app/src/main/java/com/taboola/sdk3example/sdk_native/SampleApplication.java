package com.taboola.sdk3example.sdk_native;

import android.content.Context;
import android.graphics.drawable.Drawable;


import androidx.core.content.res.ResourcesCompat;

import com.taboola.sdk3example.R;
import com.google.gson.Gson;
import com.taboola.android.TBLPublisherInfo;
import com.taboola.android.Taboola;
import com.taboola.android.utils.TBLAssetUtil;

import java.util.HashMap;
import java.util.Map;

public class SampleApplication  {
    private static final String APP_CONFIG_FILE_TITLE = "app_config.json";
    private AppConfig appConfig;



    public AppConfig getAppConfig(Context context) {

        String appConfigString = TBLAssetUtil.getHtmlTemplateFileContent(context, APP_CONFIG_FILE_TITLE);
        appConfig = new Gson().fromJson(appConfigString, AppConfig.class);



        Map<String, String> taboolaExtraProperties = new HashMap<>();
        taboolaExtraProperties.put("enableFullRawDataResponse", "true");
        taboolaExtraProperties.put("allowNonOrganicClickOverride", "true");

        Drawable imagePlaceholder = ResourcesCompat.getDrawable(context.getResources(), R.drawable.image_placeholder, null);


        TBLPublisherInfo publisherInfo=new TBLPublisherInfo(appConfig.getPublisher()).setApiKey(appConfig.getApiKey());



        Taboola.init(publisherInfo);
        return appConfig;
    }
}
