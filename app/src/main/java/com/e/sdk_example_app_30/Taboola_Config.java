package com.e.sdk_example_app_30;

import android.app.Application;

import com.taboola.android.TBLPublisherInfo;
import com.taboola.android.Taboola;


import com.taboola.android.tblweb.TBLWebUnit;

public class Taboola_Config extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        /*
         * Taboola Integration Verifier is a special mode that helps you check your Taboola
         * SDK integration into your project
         *
         * Important: Do not distribute your app with the verifier enabled.
         *
         * Use the following link for more information about the Integration Verifier:
         * https://sdk.taboola.com/docs/android-sdk-integration-verifier
         *
         * Uncomment the line below to enable this tool.
         */

        // IntegrationVerifier.getInstance().verifyIntegration(this, true);

        // Required when using TaboolaJS integration

//        TaboolaJs.getInstance().init(getApplicationContext());


        // Required when using TaboolaApi (Native Android) integration
//        TaboolaApi.getInstance().init(getApplicationContext(), "sdk-tester-demo", "30dfcf6b094361ccc367bbbef5973bdaa24dbcd6");
        TBLPublisherInfo mTaboolaApi = new TBLPublisherInfo("sdk-tester-demo").setApiKey("30dfcf6b094361ccc367bbbef5973bdaa24dbcd6");



        Taboola.init(mTaboolaApi);
    }

}