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

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.integration.testapp.mutable_collection.MutableCollectionBaseActivity
import androidx.viewpager2.widget.ViewPager2

const val KEY_ITEM_TEXT = "androidx.viewpager2.integration.testapp.KEY_ITEM_TEXT"
const val KEY_CLICK_COUNT = "androidx.viewpager2.integration.testapp.KEY_CLICK_COUNT"

/**
 * Shows how to use [FragmentStateAdapter.notifyDataSetChanged] with [ViewPager2]. Here [ViewPager2]
 * represents pages as [Fragment]s.
 */
class MutableCollectionFragmentActivity : MutableCollectionBaseActivity() {
  override fun createViewPagerAdapter(): RecyclerView.Adapter<*> {
    return PageFragmentStateAdapter(dataModel, this)
  }
}

