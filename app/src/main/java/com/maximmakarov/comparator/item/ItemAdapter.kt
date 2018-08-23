package com.maximmakarov.comparator.item

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.ext.onClick
import com.maximmakarov.comparator.data.model.Item
import kotlinx.android.synthetic.main.item_item.view.*


class ItemAdapter : ListAdapter<Item, ItemAdapter.ViewHolder>(ItemAdapter.DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.itemView.apply {
                name.text = it.name
                score.text = it.score.toString()
                score.setTextColor(ArgbEvaluator().evaluate(
                        (it.score?.toFloat() ?: 0f) / 10f, Color.RED, Color.GREEN) as Int)
                item.onClick {
                    val action = ItemsFragmentDirections.actionAddOrViewItem(it)
                    findNavController(item).navigate(action)
                }

                edit.onClick {
                    val action = ItemsFragmentDirections.actionAddOrViewItem(it)
                    findNavController(edit).navigate(action)
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item) =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Item, newItem: Item) =
                oldItem.name == newItem.name && oldItem.score == newItem.score
    }
}