package com.github.gibbrich.paintfactory.utils

import com.github.gibbrich.paintfactory.matchers.RecyclerViewMatcher

/**
 * Created by dannyroa on 5/9/15.
 */

fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
    return RecyclerViewMatcher(recyclerViewId)
}