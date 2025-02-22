package androidx.viewpager2.integration.testapp

import androidx.lifecycle.ViewModel

/** A very simple collection of items. Optimized for simplicity (i.e. not performance). */
class ItemsViewModel : ViewModel() {
    private var nextValue = 1L

    private val items = (1..9).map { longToItem(nextValue++) }.toMutableList()


    fun positionByItemId(itemId: Long): Int= (0 until size).indexOfFirst { itemId(it) == itemId }

    fun getItemById(id: Long): String = items.first { itemToLong(it) == id }
    fun itemId(position: Int): Long = itemToLong(items[position])
    fun contains(itemId: Long): Boolean = items.any { itemToLong(it) == itemId }
    fun addNewAt(position: Int) = items.add(position, longToItem(nextValue++))
    fun removeAt(position: Int) = items.removeAt(position)
    fun createIdSnapshot(): List<Long> = (0 until size).map { position -> itemId(position) }
    val size: Int get() = items.size

    private fun longToItem(value: Long): String = "item#$value"
    private fun itemToLong(value: String): Long = value.split("#")[1].toLong()
}