package com.taboola.kotlin.examples.screens.native

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.taboola.android.tblnative.TBLImageView
import com.taboola.android.tblnative.TBLTextView

/**
 * To implement a Taboola Feed in "Native Integration" we use a Compose LazyColumn to layout incoming items.
 */
class NativeComposeFeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        val taboolaNativeFeedWrapperViewModel = TaboolaNativeFeedWrapperViewModel()

        setContent {
            //Add TBLClassicUnit to the UI (to layout)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(taboolaNativeFeedWrapperViewModel.getArticles()) { article ->
                    Column() {
                        ArticleTitle(tblTextView = article.title)
                        ArticleImage(tblImageView = article.image)
                    }
                }
            }
        }
        //Fetching recommendations
        taboolaNativeFeedWrapperViewModel.fetchRecommendation(requireContext())
    }

    @Composable
    fun ArticleImage(tblImageView: TBLImageView?) {
        tblImageView?.let {
            AndroidView(factory = { _ ->
                it
            }, modifier = Modifier.fillMaxWidth())
        }
    }

    @Composable
    fun ArticleTitle(tblTextView: TBLTextView?) {
        tblTextView?.let {
            AndroidView(factory = { _ -> it })
        }
    }
}