package com.taboola.android.sdksamples.demo_infra.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecommendationItemExtraData {

    private static final String THUMBNAIL = "thumbnail";
    private static final String DESCRIPTION = "description";
    private static final String NAME = "name";
    private static final String BRANDING = "branding";
    private static final String URL = "url";
    private static final String TYPE = "type";
    private static final String CREATED = "created";


    public RecommendationItemExtraData(HashMap<String, String> hashMap) {
        description = hashMap.get(DESCRIPTION);
        type = hashMap.get(TYPE);
        created = hashMap.get(CREATED);
        name = hashMap.get(NAME);
        branding = hashMap.get(BRANDING);
        url = hashMap.get(URL);
        final Gson gson = new Gson();
        thumbnail = gson.fromJson(hashMap.get(THUMBNAIL), new TypeToken<ArrayList<Thumbnail>>() {
        }.getType());
    }

    @SerializedName(THUMBNAIL)
    private List<Thumbnail> thumbnail;


    private String description;

    private String type;

    @SerializedName(CREATED)
    private String created;

    @SerializedName(NAME)
    private String name;

    @SerializedName(BRANDING)
    private String branding;

    @SerializedName(URL)
    private String url;

    public List<Thumbnail> getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }

    public String getBranding() {
        return branding;
    }

    public String getUrl() {
        return url;
    }

    public static class Thumbnail {

        @SerializedName("url")
        private String url;

        @SerializedName("width")
        private String width;

        @SerializedName("height")
        private String height;


        public String getUrl() {
            return url;
        }

        public String getWidth() {
            return width;
        }

        public String getHeight() {
            return height;
        }
    }

}
