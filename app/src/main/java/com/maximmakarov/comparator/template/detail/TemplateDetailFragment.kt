package com.maximmakarov.comparator.template.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.core.ext.onClick
import com.maximmakarov.comparator.core.ext.visibleOrGone
import kotlinx.android.synthetic.main.template_detail_fragment.*
import kotlinx.coroutines.experimental.launch


class TemplateDetailFragment : BaseFragment() {

    override val layoutId = R.layout.template_detail_fragment

    private lateinit var viewModel: TemplateDetailViewModel

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TemplateDetailViewModel::class.java)

        val templateId = TemplateDetailFragmentArgs.fromBundle(arguments).templateId
        if (templateId != 0) viewModel.setArgs(templateId)
    }

    override fun initView() {
        val templateId = TemplateDetailFragmentArgs.fromBundle(arguments).templateId
        attributes.visibleOrGone(templateId == 0)

        submit.onClick {
            launch {
                if (templateId != 0) {
                    viewModel.editTemplateName(templateId, name.text.toString()) //todo check whether name was changed or not
                } else {
                    viewModel.addTemplate(name.text.toString(), attributes.text.toString())
                }
            }
            findNavController(submit).popBackStack()
        }
    }

    override fun subscribeUi() {
        viewModel.templateData.observe(this, Observer {
            name.setText(it.name)
        })
    }
}