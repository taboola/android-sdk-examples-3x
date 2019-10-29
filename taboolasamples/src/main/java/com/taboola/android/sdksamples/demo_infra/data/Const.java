package com.taboola.android.sdksamples.demo_infra.data;

import android.support.annotation.NonNull;

public class Const {

    public static class DefaultProperties {

        public static final String PUBLISHER = "sdk-tester";
        public static final String API_KEY = "d39df1418f5a4819c9eae2ca02595d57de98c246";
        public static final String PLACEMENT_MIDDLE = "Mid Article";
        public static final String SOURCE_TYPE = "text";
        static final String PAGE_TYPE = "article";
        static final String PAGE_URL = "https://blog.taboola.com";
        static final String TARGET_TYPE = "mix";
    }


    /**
     * Feed with Carousel in >4 pos (we don't handle pos 1..3 well).
     */
    @NonNull
    public static Properties getFeedWithCarouselProperties() {
        Properties properties = new Properties();
        properties.pageType = DefaultProperties.PAGE_TYPE;
        properties.pageUrl = DefaultProperties.PAGE_URL;
        properties.targetType = DefaultProperties.TARGET_TYPE;
        properties.publisher = DefaultProperties.PUBLISHER;
        properties.apiKey = DefaultProperties.API_KEY;
        properties.sourceType = DefaultProperties.SOURCE_TYPE;
        properties.mode = "thumbs-feed-01-d";
        properties.placement = "Feed with video and carousel";
        return properties;
    }

    /**
     * Widget with Carousel in >4 pos (we don't handle pos 1..3 well).
     */
    @NonNull
    public static Properties getWidgetWithCarouselProperties() {
        Properties properties = new Properties();
        properties.pageType = DefaultProperties.PAGE_TYPE;
        properties.pageUrl = DefaultProperties.PAGE_URL;
        properties.targetType = "mix";
        properties.publisher = DefaultProperties.PUBLISHER;
        properties.apiKey = DefaultProperties.API_KEY;
        properties.sourceType = DefaultProperties.SOURCE_TYPE;
        properties.mode = "alternating-thumbnails-a";
        properties.placement = "Below Article Thumbnails";
        return properties;
    }

    /**
     * Feed without video
     *
     * @return
     */
    @NonNull
    public static Properties getFeedWithoutVideoProperties() {
        Properties properties = new Properties();
        properties.pageType = DefaultProperties.PAGE_TYPE;
        properties.pageUrl = DefaultProperties.PAGE_URL;
        properties.targetType = DefaultProperties.TARGET_TYPE;
        properties.publisher = DefaultProperties.PUBLISHER;
        properties.apiKey = DefaultProperties.API_KEY;
        properties.sourceType = DefaultProperties.SOURCE_TYPE;
        properties.mode = "thumbs-feed-01";
        properties.placement = "Feed without video";
        return properties;
    }

    /**
     * Feed with video
     *
     * @return
     */
    @NonNull
    public static Properties getFeedWithVideoProperties() {
        Properties properties = new Properties();
        properties.pageType = DefaultProperties.PAGE_TYPE;
        properties.pageUrl = DefaultProperties.PAGE_URL;
        properties.targetType = DefaultProperties.TARGET_TYPE;
        properties.publisher = DefaultProperties.PUBLISHER;
        properties.apiKey = DefaultProperties.API_KEY;
        properties.sourceType = DefaultProperties.SOURCE_TYPE;
        properties.mode = "thumbs-feed-01";
        properties.placement = "Feed with video";
        return properties;
    }

    /**
     * Feed without video
     *
     * @return
     */
    @NonNull
    public static Properties getFeedWithThirdPartyCard() {
        Properties properties = new Properties();
        properties.pageType = DefaultProperties.PAGE_TYPE;
        properties.pageUrl = DefaultProperties.PAGE_URL;
        properties.targetType = DefaultProperties.TARGET_TYPE;
        properties.publisher = DefaultProperties.PUBLISHER;
        properties.apiKey = DefaultProperties.API_KEY;
        properties.sourceType = DefaultProperties.SOURCE_TYPE;
        properties.mode = "thumbs-feed-01";
        properties.placement = "Feed without video (weather card)";
        return properties;
    }

    /**
     * 1x4 Widget without video
     */
    @NonNull
    public static Properties getBelowArticleWidgetWithoutVideoProperties() {
        Properties properties = new Properties();
        properties.pageType = DefaultProperties.PAGE_TYPE;
        properties.pageUrl = DefaultProperties.PAGE_URL;
        properties.targetType = DefaultProperties.TARGET_TYPE;
        properties.publisher = DefaultProperties.PUBLISHER;
        properties.apiKey = DefaultProperties.API_KEY;
        properties.sourceType = DefaultProperties.SOURCE_TYPE;
        properties.mode = "alternating-widget-without-video";
        properties.placement = "Below Article";
        return properties;
    }

    /**
     * 1x4 Widget with video
     *
     * @return
     */
    @NonNull
    public static Properties getBelowArticleWidgetWithVideoProperties() {
        Properties properties = new Properties();
        properties.pageType = DefaultProperties.PAGE_TYPE;
        properties.pageUrl = DefaultProperties.PAGE_URL;
        properties.targetType = DefaultProperties.TARGET_TYPE;
        properties.publisher = DefaultProperties.PUBLISHER;
        properties.apiKey = DefaultProperties.API_KEY;
        properties.sourceType = DefaultProperties.SOURCE_TYPE;
        properties.mode = "alternating-widget-with-video";
        properties.placement = "Below Article";
        return properties;
    }

    @NonNull
    public static Properties getMidArticleWidgetWithoutVideoProperties() {
        Properties properties = new Properties();
        properties.pageType = DefaultProperties.PAGE_TYPE;
        properties.pageUrl = DefaultProperties.PAGE_URL;
        properties.targetType = DefaultProperties.TARGET_TYPE;
        properties.publisher = DefaultProperties.PUBLISHER;
        properties.apiKey = DefaultProperties.API_KEY;
        properties.sourceType = DefaultProperties.SOURCE_TYPE;
        properties.mode = "alternating-widget-without-video-1-on-1";
        properties.placement = DefaultProperties.PLACEMENT_MIDDLE;
        return properties;
    }

    @NonNull
    public static Properties getMidArticleWidgetWithVideoProperties() {
        Properties properties = new Properties();
        properties.pageType = DefaultProperties.PAGE_TYPE;
        properties.pageUrl = DefaultProperties.PAGE_URL;
        properties.targetType = DefaultProperties.TARGET_TYPE;
        properties.publisher = DefaultProperties.PUBLISHER;
        properties.apiKey = DefaultProperties.API_KEY;
        properties.sourceType = DefaultProperties.SOURCE_TYPE;
        properties.mode = "alternating-widget-with-video-1-on-1";
        properties.placement = DefaultProperties.PLACEMENT_MIDDLE;
        return properties;
    }

    @NonNull
    public static Properties getSaveForLaterProperties() {
        Properties properties = new Properties();
        properties.pageType = DefaultProperties.PAGE_TYPE;
        properties.pageUrl = DefaultProperties.PAGE_URL;
        properties.targetType = DefaultProperties.TARGET_TYPE;
        properties.publisher = DefaultProperties.PUBLISHER;
        properties.apiKey = DefaultProperties.API_KEY;
        properties.sourceType = DefaultProperties.SOURCE_TYPE;
        properties.mode = "thumbnails-c";
        properties.placement = "Save for Later";
        return properties;
    }
}