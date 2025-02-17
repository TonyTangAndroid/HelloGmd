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

package androidx.viewpager2.integration.testapp.mutable_collection_view

import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

/**
 * Shows how to use [RecyclerView.Adapter.notifyDataSetChanged] with [ViewPager2]. Here [ViewPager2]
 * represents pages as [View]s.
 */
class MutableCollectionViewActivityx() : RecyclerView.Adapter<PageViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup, type: Int) = PageViewHolder(parent)
  override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
      val itemId = holder.itemId
      val clickHandler = { clickRegistry.registerClick(itemId) }
      val clickCountProvider = { clickRegistry.getClickCount(itemId) }
      holder.bind(items.getItemById(itemId), clickHandler, clickCountProvider)
    }

    override fun getItemCount(): Int = items.size
    override fun getItemId(position: Int): Long = items.itemId(position)
}

