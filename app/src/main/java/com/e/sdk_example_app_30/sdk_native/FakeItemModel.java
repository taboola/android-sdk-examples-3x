package com.e.sdk_example_app_30.sdk_native;

public class FakeItemModel {

    private String title;
    private String imageUrl;

    FakeItemModel(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
