package com.taboola.android.sdksamples.js_integration;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.taboola.android.Taboola;
import com.taboola.android.js.TaboolaWeb;
import com.taboola.android.listeners.TaboolaWebListener;
import com.taboola.android.utils.AssetUtil;

/**
 * This Fragment shows a basic use case for the Taboola "Web" Widget.
 *
 * It shows:
 *  - The creation of a WebView that represents your own WebView.
 *  - Wrapping that WebView with a TaboolaWeb instance.
 *  - Loading <HTML_CONTENT_FILE_TITLE>, the Html page representing your content.
 */
public class WebBasicMidPage extends Fragment {

    private static final String TAG = "DEBUG";
    private static final String HTML_CONTENT_FILE_TITLE = "sampleContentPageBasicMidPage.html";
    private static final String BASE_URL = "https://example.com";

    private WebView mWebView;
    private TaboolaWeb mTaboolaWeb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context context = inflater.getContext();
        mWebView = new WebView(context);

        //Initialize TaboolaWeb instance with a reference to your WebView
        mTaboolaWeb = Taboola.getTaboolaWebBuilder().build(mWebView, new TaboolaWebListener() {
            @Override
            public void onRenderSuccessful(String placementName, int height) {
                Log.d(TAG, "onRenderSuccessful() called with: placementName = [" + placementName + "], height = [" + height + "]");
            }

            @Override
            public void onRenderFailed(String placementName, String errorMessage) {
                Log.d(TAG, "onRenderFailed() called with: placementName = [" + placementName + "], errorMessage = [" + errorMessage + "]");
            }

            @Override
            public void onResize(String placementName, int height) {
                Log.d(TAG, "onResize() called with: placementName = [" + placementName + "], height = [" + height + "]");
            }

            @Override
            public void onOrientationChange(int height) {
                Log.d(TAG, "onOrientationChange() called with: height = [" + height + "]");
            }
        });

        //Apply settings to the WebView instance to improve experience.
        initWebViewSettings(mWebView);

        //
        loadHtml();

        return mWebView;
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

    private void loadHtml() {
        //Publisher should load its url here instead
        String htmlContent = null;
        try {
            htmlContent = AssetUtil.getHtmlTemplateFileContent(getContext(), HTML_CONTENT_FILE_TITLE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to read asset file: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        mWebView.loadDataWithBaseURL(BASE_URL, htmlContent, "text/html", "UTF-8", "");
    }

    @Override
    public void onDetach() {
        mTaboolaWeb.clear();
        mTaboolaWeb = null;
        super.onDetach();
    }
}
