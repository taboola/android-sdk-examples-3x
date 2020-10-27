package com.taboola.sdk3example;
import android.app.Application;
import com.taboola.android.TBLPublisherInfo;
import com.taboola.android.Taboola;


public class TaboolaSampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TBLPublisherInfo tblPublisherInfo = new TBLPublisherInfo("sdk-tester-demo").setApiKey("30dfcf6b094361ccc367bbbef5973bdaa24dbcd6");
        Taboola.init(tblPublisherInfo);
    }

}