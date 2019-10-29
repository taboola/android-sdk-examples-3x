package com.taboola.android.sdksamples;

import android.app.Application;

import com.taboola.android.PublisherInfo;
import com.taboola.android.Taboola;
import com.taboola.android.sdksamples.demo_infra.data.Const;

/**
 *  Actual integration requires:
 *      - A single, global initialization in Application level, see Taboola.init() bellow.
 *      - Per screen integration code, see different Fragments, generally 1 Fragment per 1 use case.
 *
 *  All fragments were placed in 1 of 3 packages, portraying the 3 different integration types of the Taboola SDK:
 *      - Taboola Native: Most common, Java for Android integration.
 *      - Taboola Standard: Mobile-Web content handled by Taboola's own WebView and helper classes.
 *      - Taboola Web: Your WebView, your Mobile-Web content and your integration of Taboola's content inside, at JS level.
 *
 *  Taboola requires initialization of SDK as early as possible, usually at Application creation level.
 */
public class TaboolaSampleApplication extends Application {
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



        // Initialize Taboola SDK. Provide your mandatory and optional publisher details using the PublisherInfo object.
        Taboola.init(new PublisherInfo(Const.DefaultProperties.PUBLISHER).setApiKey(Const.DefaultProperties.API_KEY));
    }

}