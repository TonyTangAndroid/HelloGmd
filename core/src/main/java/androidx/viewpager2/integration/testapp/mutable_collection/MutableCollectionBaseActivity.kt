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

package androidx.viewpager2.integration.testapp.mutable_collection

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.integration.testapp.DiffUtilCallback
import androidx.viewpager2.integration.testapp.ItemSpinnerAdaptor
import androidx.viewpager2.integration.testapp.ItemsViewModel
import androidx.viewpager2.integration.testapp.R
import androidx.viewpager2.widget.ViewPager2

/**
 * Shows how to use notifyDataSetChanged with [ViewPager2]
 */
abstract class MutableCollectionBaseActivity : FragmentActivity() {
  private lateinit var buttonAddAfter: Button
  private lateinit var buttonAddBefore: Button
  private lateinit var buttonGoTo: Button
  private lateinit var buttonRemove: Button
  private lateinit var itemSpinner: Spinner
  private lateinit var checkboxDiffUtil: CheckBox
  private lateinit var viewPager: ViewPager2

  val dataModel: ItemsViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_mutable_collection)
    initUI()
    setupListener()
  }

  abstract fun createViewPagerAdapter(): RecyclerView.Adapter<*>

  private fun adapter(): RecyclerView.Adapter<*> {
    return viewPager.adapter!!
  }

  private fun setupListener() {
    buttonGoTo.setOnClickListener {
      viewPager.setCurrentItem(itemSpinner.selectedItemPosition, true)
    }

    buttonRemove.setOnClickListener {
      changeDataSet { dataModel.removeAt(itemSpinner.selectedItemPosition) }
    }

    buttonAddBefore.setOnClickListener {
      changeDataSet { dataModel.addNewAt(itemSpinner.selectedItemPosition) }
    }

    buttonAddAfter.setOnClickListener {
      changeDataSet { dataModel.addNewAt(itemSpinner.selectedItemPosition + 1) }
    }
  }

  private fun changeDataSet(performChanges: () -> Unit) {
    if (checkboxDiffUtil.isChecked) {
      applyDeltaUpdate(performChanges)
    } else {
      applyFullUpdate(performChanges)
    }
    // item spinner update
    (itemSpinner.adapter as BaseAdapter).notifyDataSetChanged()
  }

  private fun initUI() {
    buttonAddAfter = findViewById(R.id.buttonAddAfter)
    buttonAddBefore = findViewById(R.id.buttonAddBefore)
    buttonGoTo = findViewById(R.id.buttonGoTo)
    buttonRemove = findViewById(R.id.buttonRemove)
    itemSpinner = findViewById(R.id.itemSpinner)
    checkboxDiffUtil = findViewById(R.id.useDiffUtil)
    viewPager = findViewById(R.id.viewPager)
    viewPager.adapter = createViewPagerAdapter()
    itemSpinner.adapter = ItemSpinnerAdaptor(dataModel)
  }

  /**
   * Key takeaway here:
   * The [ViewPager2.getCurrentItem] reflects the source of truth of the item being selected. And We should track this position to
   * continously reflect the same item after the data set changed.
   */
  @SuppressLint("NotifyDataSetChanged")
  private fun applyFullUpdate(performChanges: () -> Unit) {
    val oldPosition: Int = viewPager.currentItem
    val currentItemId: Long = dataModel.itemId(oldPosition)
    performChanges.invoke()
    adapter().notifyDataSetChanged()
    if (dataModel.contains(currentItemId)) {
      val newPosition = dataModel.positionByItemId(currentItemId)
      viewPager.setCurrentItem(newPosition, false)
    }
  }

  private fun applyDeltaUpdate(performChanges: () -> Unit) {
    /** using [DiffUtil] */
    val oldIdList: List<Long> = dataModel.createIdSnapshot()
    performChanges.invoke()
    val newIdList = dataModel.createIdSnapshot()
    DiffUtil.calculateDiff(DiffUtilCallback(oldIdList, newIdList), true).dispatchUpdatesTo(adapter())
  }

}

