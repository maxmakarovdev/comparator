package com.maximmakarov.comparator.presentation.items.form

import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.domain.model.AttributeData
import kotlinx.android.synthetic.main.page_item.view.*


class PageAdapter : ListAdapter<AttributeData, PageAdapter.ViewHolder>(PageAdapter.DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.page_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { item ->
            holder.itemView.apply {
                title.text = item.attribute.name
                title.setTypeface(title.typeface, if (item.attribute.isImportant) Typeface.BOLD else Typeface.NORMAL)
                comment.setText(item.data.answer)
                item.data.score?.let { rating.value = it }

                rating.setOnValueChangedListener { _, _, newValue -> item.data.score = newValue }
                comment.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        item.data.answer = s?.toString() ?: ""
                    }
                })
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class DiffCallback : DiffUtil.ItemCallback<AttributeData>() {
        override fun areItemsTheSame(oldItem: AttributeData, newItem: AttributeData) =
                oldItem.data.id == newItem.data.id

        override fun areContentsTheSame(oldItem: AttributeData, newItem: AttributeData) =
                oldItem.attribute == newItem.attribute && oldItem.data == newItem.data
    }
}