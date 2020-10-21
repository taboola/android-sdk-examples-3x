package com.e.sdk_example_app_30.sdk_web;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.sdk_example_app_30.R;
import com.taboola.android.Taboola;
import com.taboola.android.listeners.TBLWebListener;
import com.taboola.android.tblweb.TBLWebPage;
import com.taboola.android.tblweb.TBLWebUnit;
import com.taboola.android.utils.TBLAssetUtil;

public class SDK_Web_Split_Feed extends AppCompatActivity {
    private static final String TAG = "DEBUG";
    private static final String HTML_CONTENT_FILE_TITLE = "sampleContentPageSplitFeed.html";
    private static final String base_url = "https://example.com";
    WebView webView;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_d_k__web);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setLogo(R.drawable.ic_taboola);
        getSupportActionBar().setTitle("SDK Web Split Feed");
        resetToolbarTitle();
        webView = findViewById(R.id.webview);

//        TBLPublisherInfo publisherInfo = new TBLPublisherInfo("sdk-tester").setApiKey("d39df1418f5a4819c9eae2ca02595d57de98c246");
//        Taboola.init(publisherInfo);

        TBLWebPage tblWebPage = Taboola.getWebPage();
//        TBLWebUnit tblWebUnit= new TBLWebUnit();
        TBLWebUnit taboolaWeb = tblWebPage.build(webView, new TBLWebListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, @Nullable String customData) {
                return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData);
            }
        });

        initWebViewSettings(webView);
        loadHtml();

    }
    private void resetToolbarTitle() {
        mToolbar.setTitle("SDK Web Split Feed");
    }





    private static void initWebViewSettings(WebView webView) {
        final WebSettings settings = webView.getSettings();
        settings.setLoadsImagesAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        CookieManager.getInstance().setAcceptCookie(true);
    }



    private void loadHtml () {
        String htmlContent = null;
        try {
            htmlContent = TBLAssetUtil.getHtmlTemplateFileContent(getApplicationContext(), HTML_CONTENT_FILE_TITLE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to read asset file: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        webView.loadDataWithBaseURL(base_url, htmlContent, "text/html", "UTF-8", "");
    }


}