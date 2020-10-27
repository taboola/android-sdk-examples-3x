package com.taboola.sdk3example.sdk_classic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.taboola.sdk3example.R;
import com.taboola.android.TBLClassicPage;
import com.taboola.android.TBLClassicUnit;
import com.taboola.android.Taboola;
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE;
import com.taboola.android.listeners.TBLClassicListener;
import com.taboola.android.utils.TBLSdkDetailsHelper;

import java.util.HashMap;

public class OCClickHandlerFragment extends Fragment  {


    private static String TAG="OCClickHandlerFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_oc_click, container, false);
        buildMiddleArticleWidget(view.findViewById(R.id.taboola_widget_middle));
        return view;
    }

    private void buildMiddleArticleWidget(TBLClassicUnit tblClassicUnit) {
        tblClassicUnit
                .setPublisherName("sdk-tester-demo")
                .setPageType("article")
                .setPageUrl("https://blog.taboola.com")
                .setPlacement("Feed without video")
                .setMode("thumbs-feed-01")
                .setTargetType("mix")
                .setInterceptScroll(true);

        HashMap<String, String> extraProperties = new HashMap<>();
        extraProperties.put("useOnlineTemplate", "true");
        tblClassicUnit.setUnitExtraProperties(extraProperties);

        final int height = TBLSdkDetailsHelper.getDisplayHeight(tblClassicUnit.getContext());
        ViewGroup.LayoutParams params = tblClassicUnit.getLayoutParams();

        if (params == null) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            tblClassicUnit.setLayoutParams(params);
        } else {
            params.height = height;
        }

        TBLClassicListener tblClassicListener;
        tblClassicListener=new TBLClassicListener() {
            @Override
            public boolean onItemClick(String placementName, String itemId, String clickUrl, boolean isOrganic, String customData) {
                if (isOrganic && getActivity() != null) {

                    Log.d(TAG,"onItemClick"+itemId);
                    Toast.makeText(getContext(), "mock load url: " + clickUrl, Toast.LENGTH_LONG).show();
                    //Returning false - the click's default behavior is aborted. The app should display the Taboola Recommendation content on its own (for example, using an in-app browser).
                    return false;
                }

                //Returning true - the click is a standard one and is sent to the Android OS for default behavior.
                return true;
            }
        };
        TBLClassicPage tblClassicPage= Taboola.getClassicPage(getContext(), "https://blog.taboola.com", "article");
        tblClassicPage.addUnitToPage(tblClassicUnit,"Feed without video","thumbs-feed-01", TBL_PLACEMENT_TYPE.FEED,tblClassicListener);
        tblClassicUnit.setUnitExtraProperties(extraProperties);
        tblClassicUnit.fetchContent();

    }



}
