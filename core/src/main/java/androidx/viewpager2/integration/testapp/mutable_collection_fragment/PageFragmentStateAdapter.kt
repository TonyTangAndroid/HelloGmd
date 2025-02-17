/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.viewpager2.integration.testapp.mutable_collection_fragment

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.integration.testapp.ItemsViewModel

class PageFragmentStateAdapter(private val items: ItemsViewModel, activity: FragmentActivity) :
  FragmentStateAdapter(activity) {
  override fun createFragment(position: Int): PageFragment {
    val itemId = items.itemId(position)
    val itemText = items.getItemById(itemId)
    return PageFragment.newFragment(itemText)
  }

  override fun getItemCount(): Int = items.size
  override fun getItemId(position: Int): Long = items.itemId(position)
  override fun containsItem(itemId: Long): Boolean = items.contains(itemId)
}

