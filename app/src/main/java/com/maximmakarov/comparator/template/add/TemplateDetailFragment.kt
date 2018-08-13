package com.maximmakarov.comparator.template.add

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.maximmakarov.comparator.BaseFragment
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.ext.gone
import com.maximmakarov.comparator.core.ext.onClick
import kotlinx.android.synthetic.main.template_detail_fragment.*
import kotlinx.coroutines.experimental.launch


class TemplateDetailFragment : BaseFragment() {

    override val layoutId = R.layout.template_detail_fragment

    private lateinit var viewModel: TemplateDetailViewModel

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TemplateDetailViewModel::class.java)
    }

    override fun initView() {
        submit.onClick {
            val templateId = TemplateDetailFragmentArgs.fromBundle(arguments).templateId
            launch {
                if (templateId != 0) {
                    viewModel.editTemplateName(templateId, name.text.toString()) //todo check whether name was changed or not
                } else {
                    viewModel.addTemplate(name.text.toString(), attributes.text.toString())
                }
            }
            submit?.findNavController()?.popBackStack()
        }
    }

    override fun subscribeUi() {
        val templateId = TemplateDetailFragmentArgs.fromBundle(arguments).templateId
        if (templateId != 0) {
            attributes.gone()
            viewModel.getTemplateById(templateId).observe(this, Observer {
                name.setText(it.name)
            })
        }
    }
}