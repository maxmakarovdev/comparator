package com.maximmakarov.comparator.template

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximmakarov.comparator.BaseFragment
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.ext.onClick
import kotlinx.android.synthetic.main.templates_fragment.*

class TemplatesFragment : BaseFragment() {

    override val layoutId = R.layout.templates_fragment

    private lateinit var viewModel: TemplatesViewModel
    private lateinit var adapter: TemplateAdapter

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TemplatesViewModel::class.java)
    }

    override fun initView() {
        adapter = TemplateAdapter()
        templates.adapter = adapter
        templates.itemAnimator = DefaultItemAnimator()
        templates.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        add.onClick {
            val action = TemplatesFragmentDirections.actionAddOrViewTemplate()
            add?.findNavController()?.navigate(action)
        }
    }

    override fun subscribeUi() {
        viewModel.getTemplates().observe(this, Observer { adapter.submitList(it) })
    }
}
