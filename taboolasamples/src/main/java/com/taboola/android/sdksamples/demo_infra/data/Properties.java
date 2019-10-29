package com.taboola.android.sdksamples.demo_infra.data;


import android.os.Parcel;
import android.os.Parcelable;


public class Properties implements Parcelable{

     String pageType;
     String pageUrl;
     String mode;
     String placement;
     String targetType;
     String publisher;
     String apiKey;
     String sourceType;

    public Properties() {
    }

    public Properties(String publisher, String mode, String placement, String pageUrl, String pageType, String targetType) {
        this.pageType = pageType;
        this.pageUrl = pageUrl;
        this.mode = mode;
        this.placement = placement;
        this.targetType = targetType;
        this.publisher = publisher;
    }

    public Properties(String pageType, String pageUrl, String mode, String placement, String targetType, String publisher, String apiKey, String sourceType) {
        this.pageType = pageType;
        this.pageUrl = pageUrl;
        this.mode = mode;
        this.placement = placement;
        this.targetType = targetType;
        this.publisher = publisher;
        this.apiKey = apiKey;
        this.sourceType = sourceType;
    }

    public Properties(Properties other) {
        this.pageType = other.pageType;
        this.pageUrl = other.pageUrl;
        this.mode = other.mode;
        this.placement = other.placement;
        this.targetType = other.targetType;
        this.publisher = other.publisher;
    }

    public String getPageType() {
        return pageType;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public String getMode() {
        return mode;
    }

    public String getPlacement() {
        return placement;
    }

    public String getTargetType() {
        return targetType;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSourceType() {
        return sourceType;
    }

    public Properties setPageType(String pageType) {
        this.pageType = pageType;
        return this;
    }

    public Properties setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
        return this;
    }

    public Properties setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public Properties setPlacement(String placement) {
        this.placement = placement;
        return this;
    }

    public Properties setTargetType(String targetType) {
        this.targetType = targetType;
        return this;
    }

    public Properties setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }


    public Properties setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public Properties setSourceType(String sourceType) {
        this.sourceType = sourceType;
        return this;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pageType);
        dest.writeString(this.pageUrl);
        dest.writeString(this.mode);
        dest.writeString(this.placement);
        dest.writeString(this.targetType);
        dest.writeString(this.publisher);
        dest.writeString(this.apiKey);
        dest.writeString(this.sourceType);
    }

    protected Properties(Parcel in) {
        this.pageType = in.readString();
        this.pageUrl = in.readString();
        this.mode = in.readString();
        this.placement = in.readString();
        this.targetType = in.readString();
        this.publisher = in.readString();
        this.apiKey = in.readString();
        this.sourceType = in.readString();
    }

    public static final Creator<Properties> CREATOR = new Creator<Properties>() {
        @Override
        public Properties createFromParcel(Parcel source) {
            return new Properties(source);
        }

        @Override
        public Properties[] newArray(int size) {
            return new Properties[size];
        }
    };
}
