package com.taboola.android.sdksamples;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taboola.android.sdksamples.js_integration.WebBasicMidPage;
import com.taboola.android.sdksamples.js_integration.WebBasicMidPageAndFeedBellow;
import com.taboola.android.sdksamples.native_integration.NativeBasicFourItems;
import com.taboola.android.sdksamples.standard_integration.StandardBasicXmlFragment;

/**
 * This class handles displaying the different Fragments on user click selection.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewGroup viewGroup = view.findViewById(R.id.main_menu_lyt);

        //"Standard" integration
        addHeader("Standard Integration", viewGroup);
        addButton("Basic XML", R.id.standard_basic_xml, viewGroup);

        //"Native" integration
        addHeader("Native Integration", viewGroup);
        addButton("Basic - 4 Items", R.id.native_basic_4_items, viewGroup);


        //"Web" integration
        addHeader("Web Integration", viewGroup);
        addButton("Basic - Mid Page", R.id.web_basic_mid_page, viewGroup);
        addButton("Basic - Mid Page And Feed Bellow", R.id.web_basic_mid_page_and_feed_bellow, viewGroup);





    }

    @Override
    public void onClick(View v) {

        String screenName = v.getTag().toString();
        Fragment fragmentToOpen = null;
        switch (v.getId()) {

            case R.id.standard_basic_xml:
                fragmentToOpen = new StandardBasicXmlFragment();
                break;

            case R.id.native_basic_4_items:
                fragmentToOpen = new NativeBasicFourItems();
                break;

            case R.id.web_basic_mid_page:
                fragmentToOpen = new WebBasicMidPage();
                break;

            case R.id.web_basic_mid_page_and_feed_bellow:
                fragmentToOpen = new WebBasicMidPageAndFeedBellow();
                break;

        }

        if (fragmentToOpen != null) {
            openFragment(fragmentToOpen, screenName);
        }
    }


    /***********
     * Utility *
     ***********/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void openFragment(Fragment fragment, String screenName) {
        if (mListener != null) {
            mListener.onMenuItemClicked(fragment, screenName);
        }
    }

    private void addHeader(String title, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.menu_header_item,
                viewGroup, false);
        textView.setText(title);
        viewGroup.addView(textView, viewGroup.getChildCount() - 1);
    }

    private void addButton(String screenName, int id, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.button_item, viewGroup, false);
        textView.setText(screenName);
        textView.setTag(screenName);
        textView.setId(id);
        textView.setOnClickListener(this);

        viewGroup.addView(textView, viewGroup.getChildCount() - 1);
    }

    public interface OnFragmentInteractionListener {
        void onMenuItemClicked(Fragment fragment, String screenName);
    }
}