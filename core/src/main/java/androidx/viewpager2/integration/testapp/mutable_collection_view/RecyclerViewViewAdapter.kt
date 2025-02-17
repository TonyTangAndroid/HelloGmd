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

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.integration.testapp.ItemsViewModel

class RecyclerViewViewAdapter(private val clickRegistry: ClickRegistry, private val items: ItemsViewModel) :
  RecyclerView.Adapter<PageViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, type: Int) = PageViewHolder(parent)
  override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
    val itemId = holder.itemId
    val clickHandler = { clickRegistry.registerClick(itemId) }
    val clickCountProvider = { clickRegistry.getClickCount(itemId) }
    val itemText: String = items.getItemById(itemId)
    holder.bind(itemText, clickHandler, clickCountProvider)
  }

  override fun getItemCount(): Int = items.size
  override fun getItemId(position: Int): Long = items.itemId(position)
}

