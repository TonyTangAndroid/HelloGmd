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

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_mutable_collection)
    initUI()
    setupViewPager()
    setupSpinner()
    setupListener()
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

  private fun setupViewPager() {
    viewPager.adapter = createViewPagerAdapter()
  }

  private fun setupSpinner() {
    itemSpinner.adapter = object : BaseAdapter() {
      override fun getItem(position: Int): String = dataModel.getItemById(getItemId(position))
      override fun getItemId(position: Int): Long = dataModel.itemId(position)
      override fun getCount(): Int = dataModel.size
      override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        ((convertView as TextView?) ?: TextView(parent.context)).apply {
          textDirection = View.TEXT_DIRECTION_LOCALE
          text = getItem(position)
        }

    }
  }

  private fun initUI() {
    buttonAddAfter = findViewById(R.id.buttonAddAfter)
    buttonAddBefore = findViewById(R.id.buttonAddBefore)
    buttonGoTo = findViewById(R.id.buttonGoTo)
    buttonRemove = findViewById(R.id.buttonRemove)
    itemSpinner = findViewById(R.id.itemSpinner)
    checkboxDiffUtil = findViewById(R.id.useDiffUtil)
    viewPager = findViewById(R.id.viewPager)
  }

  abstract fun createViewPagerAdapter(): RecyclerView.Adapter<*>

  val dataModel: ItemsViewModel by viewModels()

  @SuppressLint("NotifyDataSetChanged")
  private fun applyFullUpdate(performChanges: () -> Unit) {
    val oldPosition = viewPager.currentItem
    val currentItemId = dataModel.itemId(oldPosition)
    performChanges()
    adapter().notifyDataSetChanged()
    if (dataModel.contains(currentItemId)) {
      selectToCurrentItem(currentItemId)
    }
  }

  private fun selectToCurrentItem(currentItemId: Long) {
    val newPosition = (0 until dataModel.size).indexOfFirst { dataModel.itemId(it) == currentItemId }
    viewPager.setCurrentItem(newPosition, false)
  }

  private fun applyDeltaUpdate(performChanges: () -> Unit) {
    /** using [DiffUtil] */
    val oldIdList: List<Long> = dataModel.createIdSnapshot()
    performChanges()
    val newIdList = dataModel.createIdSnapshot()
    DiffUtil.calculateDiff(DiffUtilCallback(oldIdList, newIdList), true).dispatchUpdatesTo(adapter())
  }

  private fun adapter(): RecyclerView.Adapter<*> {
    return viewPager.adapter!!
  }
}

