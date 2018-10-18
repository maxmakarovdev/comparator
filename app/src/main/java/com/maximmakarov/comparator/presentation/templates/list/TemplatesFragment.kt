package com.maximmakarov.comparator.presentation.templates.list

import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.core.extensions.onClick
import com.maximmakarov.comparator.data.model.Template
import kotlinx.android.synthetic.main.templates_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TemplatesFragment : BaseFragment() {

    override val layoutId = R.layout.templates_fragment

    private val viewModel: TemplatesViewModel by viewModel()
    private lateinit var adapter: TemplateAdapter

    override fun initView() {
        setTitle(R.string.templates_title)

        adapter = TemplateAdapter()
        templates.adapter = adapter
        templates.itemAnimator = DefaultItemAnimator()
        templates.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        add.onClick {
            val action = TemplatesFragmentDirections.actionAddOrViewTemplate(Template(null, ""))
            findNavController(add).navigate(action)
        }
    }

    override fun subscribeUi() {
        viewModel.templatesData.observe(this, Observer { adapter.submitList(it) })
    }
}
