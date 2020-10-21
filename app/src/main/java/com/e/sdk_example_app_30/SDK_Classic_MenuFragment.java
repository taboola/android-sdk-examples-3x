package com.e.sdk_example_app_30;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.sdk_example_app_30.sdk_classic.FeedInsideRecyclerViewCustomFragment;
import com.e.sdk_example_app_30.sdk_classic.FeedInsideRecyclerViewFragment;
import com.e.sdk_example_app_30.sdk_classic.FeedWithMiddleArticleDarkModeInsideRecyclerViewFragment;
import com.e.sdk_example_app_30.sdk_classic.FeedWithMiddleArticleInsideListViewFragment;
import com.e.sdk_example_app_30.sdk_classic.FeedWithMiddleArticleInsideRecyclerViewFragment;
import com.e.sdk_example_app_30.sdk_classic.FeedWithMiddleArticleInsideScrollViewFragment;
import com.e.sdk_example_app_30.sdk_classic.OCClickHandlerFragment;
import com.e.sdk_example_app_30.sdk_classic.PullToRefreshFragment;
import com.e.sdk_example_app_30.sdk_classic.RecyclerViewPreloadFragment;
import com.e.sdk_example_app_30.sdk_classic.SimpleWidgetFragment;
import com.e.sdk_example_app_30.sdk_classic.ViewPagerFragment;


public class SDK_Classic_MenuFragment extends Fragment implements View.OnClickListener{


    private OnFragmentInteractionListener mListener;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_menu, container, false);

// Inflate the layout for this fragment


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        
        super.onViewCreated(view, savedInstanceState);
        ViewGroup viewGroup = view.findViewById(R.id.main_menu_lyt);



//        addHeader(getString(R.string.sdk_via_native), viewGroup);
      addButton(getString(R.string.std_mid_article_with_feed_lnr), R.id.std_mid_article_with_feed_lnr, viewGroup);
        addButton(getString(R.string.std_mid_article_with_feed_rv), R.id.std_mid_article_with_feed_rv, viewGroup);
        addButton(getString(R.string.std_mid_article_with_feed_rv_manual), R.id.std_mid_article_with_feed_rv_manual, viewGroup);
        addButton(getString(R.string.std_mid_article_preload), R.id.std_widget_preload, viewGroup);
        addButton(getString(R.string.std_view_pager), R.id.std_view_pager, viewGroup);
        addButton(getString(R.string.std_mid_article_with_feed_lv), R.id.std_mid_article_with_feed_lv, viewGroup);
        addButton(getString(R.string.std_widget_xml), R.id.std_widget_xml, viewGroup);
        addButton(getString(R.string.std_widget_oc_click), R.id.std_widget_oc_click, viewGroup);
        addButton(getString(R.string.std_feed_pull_to_refresh), R.id.std_feed_pull_to_refresh, viewGroup);
        addButton(getString(R.string.std_feed_lazy_loading_rv), R.id.std_feed_lazy_loading_rv, viewGroup);
        addButton(getString(R.string.std_mid_article_with_feed_dark_mode_rv), R.id.std_mid_article_with_feed_dark_mode_rv, viewGroup);

//        addHeader(getString(R.string.sdk_via_js), viewGroup);
//        addButton(getString(R.string.js_mid_article_with_feed), R.id.js_mid_article_with_feed, viewGroup);
//        addButton(getString(R.string.js_split), R.id.js_split, viewGroup);
//        addButton(getString(R.string.js_view_pager), R.id.js_view_pager, viewGroup);





    }




    @Override
    public void onClick(View v) {

        String screenName = v.getTag().toString();
        Fragment fragmentToOpen = null;
        switch (v.getId()) {



            case R.id.std_mid_article_with_feed_lnr:
                fragmentToOpen = new FeedWithMiddleArticleInsideScrollViewFragment();
                break;

//            case R.id.js_view_pager:
//                fragmentToOpen = new ViewPagerViaJsFragment();
//                break;

            case R.id.std_view_pager:
                fragmentToOpen = new ViewPagerFragment();
                break;
//
            case R.id.std_mid_article_with_feed_lv:
                fragmentToOpen = new FeedWithMiddleArticleInsideListViewFragment();
                break;

            case R.id.std_mid_article_with_feed_rv:
                fragmentToOpen = new FeedWithMiddleArticleInsideRecyclerViewFragment();
                break;

            case R.id.std_mid_article_with_feed_rv_manual:
                fragmentToOpen = new FeedInsideRecyclerViewCustomFragment();
                break;
//
            case R.id.std_widget_xml:
                fragmentToOpen = new SimpleWidgetFragment();
                break;
//
            case R.id.std_widget_oc_click:
                fragmentToOpen = new OCClickHandlerFragment();
                break;
//
            case R.id.std_widget_preload:
                fragmentToOpen = new RecyclerViewPreloadFragment();
                break;
//
            case R.id.std_feed_pull_to_refresh:
                fragmentToOpen = new PullToRefreshFragment();
                break;
//
            case R.id.std_feed_lazy_loading_rv:
                fragmentToOpen = new FeedInsideRecyclerViewFragment();
                break;

            case R.id.std_mid_article_with_feed_dark_mode_rv:
                fragmentToOpen = new FeedWithMiddleArticleDarkModeInsideRecyclerViewFragment();
                break;

        }

        if (fragmentToOpen != null) {
            openFragment(fragmentToOpen, screenName);
        }
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