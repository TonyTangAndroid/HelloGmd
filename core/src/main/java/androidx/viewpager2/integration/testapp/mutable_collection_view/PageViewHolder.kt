package androidx.viewpager2.integration.testapp.mutable_collection_view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.integration.testapp.R

class PageViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_mutable_collection, parent, false)
    ) {
    private val textViewItemId: TextView = itemView.findViewById(R.id.textViewItemText)
    private val textViewCount: TextView = itemView.findViewById(R.id.textViewCount)
    private val buttonCountIncrease: Button = itemView.findViewById(R.id.buttonCountIncrease)

    fun bind(itemText: String, registerClick: () -> Unit, getClickCount: () -> Int) {
        textViewItemId.text = itemText
        val updateClickText = { textViewCount.text = "${getClickCount()}" }
        updateClickText()

        buttonCountIncrease.setOnClickListener {
            registerClick()
            updateClickText()
        }
    }
}