package com.taboola.kotlin.examples.screens.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.taboola.kotlin.examples.PlacementInfo

/**
 * This is an example for 1 webView and 1 page, if you have another case, then please contact Taboola support team.
 */
class WebComposeFeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            // Add Taboola web feed to your current WebView
            CustomerWebView(PlacementInfo.webFeedProperties())
        }
    }
}


