package com.taboola.sdk3example.sdk_native;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taboola.sdk3example.R;
import com.taboola.sdk3example.sdk_native.utils.DateTimeUtil;
import com.taboola.sdk3example.sdk_native.utils.TBDeviceInfoUtil;
import com.squareup.picasso.Picasso;
import com.taboola.android.Taboola;
import com.taboola.android.listeners.TBLNativeListener;
import com.taboola.android.tblnative.TBLNativePage;
import com.taboola.android.tblnative.TBLNativeUnit;
import com.taboola.android.tblnative.TBLRecommendationItem;
import com.taboola.android.tblnative.TBLRecommendationRequestCallback;
import com.taboola.android.tblnative.TBLRecommendationsResponse;
import com.taboola.android.tblnative.TBLRequestData;
import com.taboola.android.utils.advertising_id.TBLAdvertisingIdClient;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SummaryActivity extends Activity {

    public static final String ANDROID = "Android";
    private TBLRecommendationItem mTbRecommendationItem;
    TBLNativePage taboola_view;
    private RecommendationItemExtraData mRecommendationItemExtraData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        parseRI(getIntent());
        loadTbItemToHeader();
        initTaboolaFeedWidget();
    }

    private void parseRI(Intent intent) {
        if (intent != null && intent.hasExtra("clickedItem")) {
            mTbRecommendationItem = intent.getParcelableExtra("clickedItem");
            mRecommendationItemExtraData = new RecommendationItemExtraData(mTbRecommendationItem.getExtraDataMap());
        }
    }

    private void initTaboolaFeedWidget() {
       taboola_view= Taboola.getNativePage("text","http://example.com");

        HashMap<String, String> optionalPageCommands = new HashMap<>();
        optionalPageCommands.put("useOnlineTemplate", "true");
        TBLRequestData requestData=new TBLRequestData();
        requestData.setRecCount(2);
        requestData.setThumbnailSize(100,100);

        TBLNativeUnit tblNativeUnit=taboola_view.build("AC-stream-trending-", new TBLNativeListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }
        });
        tblNativeUnit.fetchRecommendations(requestData, new TBLRecommendationRequestCallback() {
            @Override
            public void onRecommendationsFetched(TBLRecommendationsResponse tblRecommendationsResponse) {

                TBLRecommendationItem item=tblRecommendationsResponse.getPlacementsMap().get("AC-stream-trending-").getItems().get(0);


            }

            @Override
            public void onRecommendationsFailed(Throwable throwable) {

            }
        });












       // taboolaWidget.setExtraProperties(optionalPageCommands);
      //  taboolaWidget.fetchContent();
    }

    private void loadTbItemToHeader() {
        this.<TextView>findViewById(R.id.tv_summary_title).setText(mRecommendationItemExtraData.getName());
        String timestamp = DateTimeUtil.getTimeBetween(mRecommendationItemExtraData.getCreated());
        this.<TextView>findViewById(R.id.tv_summary_time).setText(timestamp);
        this.<TextView>findViewById(R.id.tv_summary_branding).setText(mRecommendationItemExtraData.getBranding());
        this.<TextView>findViewById(R.id.tv_summary_description).setText(mRecommendationItemExtraData.getDescription());

        ImageView thumbnail = findViewById(R.id.iv_summary_thumbnail);
        Picasso.with(this)
                .load(getIntent().getStringExtra("url"))
                .into(thumbnail);


        this.<LinearLayout>findViewById(R.id.btn_read_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // todo send BI event
                onReadMoreClicked();
            }
        });


        onTbItemFinishToLoad();


    }

    private void onTbItemFinishToLoad() {
        final String eventType = "SummaryRenderEvent";
        mTbRecommendationItem.reportEvent(eventType, getEventMap(eventType), "youmaylike");
    }

    private Map<String, String> getEventMap(String eventType) {
        Map<String, String> map = new HashMap<>();
        map.put("device_id", TBLAdvertisingIdClient.getCachedAdvertisingId(this));
        map.put("event_type", eventType);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        map.put("time", sdf.format(new Date(System.currentTimeMillis())));
        map.put("os_version", Build.VERSION.RELEASE);
        map.put("os_name", ANDROID);
        map.put("platform", ANDROID);
        map.put("device_manufacturer", Build.MANUFACTURER);
        map.put("language", Locale.getDefault().getDisplayLanguage(Locale.US));
        map.put("publisher", mTbRecommendationItem.getPublisherName());
        map.put("device_model", TBDeviceInfoUtil.getDeviceName());
        map.put("app_version", TBDeviceInfoUtil.getAppVersion(this));
        map.put("carrier", TBDeviceInfoUtil.getCarrier(this));
        map.put("sdk_version", com.taboola.android.BuildConfig.VERSION_NAME);
        return map;
    }


    private void onReadMoreClicked() {
        final String eventType = "ReadMoreClickedEvent";
        mTbRecommendationItem.reportEvent(eventType, getEventMap(eventType), "youmaylike");


        String url = getIntent().getStringExtra("clickUrl");

    }

}