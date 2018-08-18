package com.maximmakarov.comparator.item.form

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.data.repository.ItemDataWithAttr
import kotlinx.android.synthetic.main.page_item.view.*


class PageAdapter : ListAdapter<ItemDataWithAttr, PageAdapter.ViewHolder>(PageAdapter.DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.page_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.itemView.apply {
                title.text = it.attribute.name
                title.setTypeface(title.typeface, if (it.attribute.isImportant) Typeface.BOLD else Typeface.NORMAL)
                comment.setText(it.data.answer)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class DiffCallback : DiffUtil.ItemCallback<ItemDataWithAttr>() {
        override fun areItemsTheSame(oldItem: ItemDataWithAttr, newItem: ItemDataWithAttr) =
                oldItem.data.id == newItem.data.id

        override fun areContentsTheSame(oldItem: ItemDataWithAttr, newItem: ItemDataWithAttr) =
                oldItem.attribute == newItem.attribute && oldItem.data == newItem.data
    }
}