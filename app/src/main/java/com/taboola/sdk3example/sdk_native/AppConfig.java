package com.taboola.sdk3example.sdk_native;

public class AppConfig {
    private String publisher;
    private String apiKey;


    AppConfig(String publisher,String apiKey){
        this.publisher=publisher;
        this.apiKey=apiKey;


    }

    public String getPublisher() {
        return publisher;
    }

    public String getApiKey() {
        return apiKey;
    }

}