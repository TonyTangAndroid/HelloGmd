package androidx.viewpager2.integration.testapp.mutable_collection_view

import androidx.lifecycle.ViewModel

/**
 * Stores click counts for items. Items are identified by an id.
 */
class ClickRegistry : ViewModel() {
    private val clickCount = mutableMapOf<Long, Int>()
    fun getClickCount(itemId: Long): Int = clickCount[itemId] ?: 0
    fun registerClick(itemId: Long) = clickCount.set(itemId, 1 + getClickCount(itemId))
}