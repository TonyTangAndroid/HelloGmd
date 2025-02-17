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

import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.widget.ViewPager2

/**
 * Shows how to use notifyDataSetChanged with [ViewPager2]
 */
class DiffUtilCallback(private val oldIdList: List<Long>, private val newIdList: List<Long>) : DiffUtil.Callback() {

  override fun getOldListSize(): Int = oldIdList.size
  override fun getNewListSize(): Int = newIdList.size

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldIdList[oldItemPosition] == newIdList[newItemPosition]
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return areItemsTheSame(oldItemPosition, newItemPosition)
  }

}

