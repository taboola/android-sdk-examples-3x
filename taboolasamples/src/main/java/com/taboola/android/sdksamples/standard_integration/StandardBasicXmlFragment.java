package com.taboola.android.sdksamples.standard_integration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taboola.android.TaboolaHybrid;
import com.taboola.android.sdksamples.R;

/**
 * This Fragment shows a "Standard" Widget inflated from its definition in XML (see fragment_simple_widget.xml).
 * This represents the most minimal integration possible for "Standard" XML integration.
 */
public class StandardBasicXmlFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_widget, container, false);
        TaboolaHybrid taboolaHybrid = view.findViewById(R.id.taboola_view);

        taboolaHybrid.fetchContent();
        return view;
    }
}
