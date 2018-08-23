package com.maximmakarov.comparator.template

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.ext.onClick
import com.maximmakarov.comparator.data.model.Template
import kotlinx.android.synthetic.main.template_item.view.*


class TemplateAdapter : ListAdapter<Template, TemplateAdapter.ViewHolder>(TemplateAdapter.DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.template_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.itemView.apply {
                name.text = it.name

                item.onClick {
                    val action = TemplatesFragmentDirections.actionSelectTemplate(it)
                    findNavController(item).navigate(action)
                }

                edit.onClick {
                    val action = TemplatesFragmentDirections.actionAddOrViewTemplate(it)
                    findNavController(edit).navigate(action)
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class DiffCallback : DiffUtil.ItemCallback<Template>() {
        override fun areItemsTheSame(oldItem: Template, newItem: Template) =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Template, newItem: Template) =
                oldItem.name == newItem.name
    }
}