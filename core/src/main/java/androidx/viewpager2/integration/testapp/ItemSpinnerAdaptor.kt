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

package androidx.viewpager2.integration.testapp

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2

/**
 * Shows how to use notifyDataSetChanged with [ViewPager2]
 */
class ItemSpinnerAdaptor(private val dataModel: ItemsViewModel) : BaseAdapter() {

  override fun getItem(position: Int): String = dataModel.getItemById(getItemId(position))
  override fun getItemId(position: Int): Long = dataModel.itemId(position)
  override fun getCount(): Int = dataModel.size
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
    ((convertView as TextView?) ?: TextView(parent.context)).apply {
      textDirection = View.TEXT_DIRECTION_LOCALE
      text = getItem(position)
    }
}

