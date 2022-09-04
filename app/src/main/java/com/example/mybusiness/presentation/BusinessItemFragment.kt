package com.example.mybusiness.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mybusiness.R
import com.google.android.material.textfield.TextInputLayout


class BusinessItemFragment(
    private val screenMode: String = "",
    private val businessItemId: Int = -1
) : Fragment() {

    private lateinit var viewModel: BusinessItemViewModel
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonAddBusinessItem: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)[BusinessItemViewModel::class.java]
        initViews(view)

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        etCount.addTextChangedListener {
            tilCount.error = null
        }

        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Invalid count"
            } else {
                null
            }
            tilCount.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Invalid name"
            } else {
                null
            }
            tilName.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }
    }

    private fun launchEditMode() {
        viewModel.getBusinessItem(businessItemId)
        viewModel.businessItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonAddBusinessItem.setOnClickListener {
            viewModel.editBusinessItem(etName.text?.toString(), etCount.text?.toString())
        }

    }

    private fun launchAddMode() {
        buttonAddBusinessItem.setOnClickListener {
            viewModel.addBusinessItem(etName.text?.toString(), etCount.text?.toString())
        }
    }


    private fun parseParams() {
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
            throw RuntimeException("error")
        }
        if (screenMode == MODE_EDIT && businessItemId == -1) {
            throw RuntimeException("error")
        }


    }

    private fun initViews(view: View) {
        with(view) {
            tilName = findViewById(R.id.addNameBusinessItem)
            etName = findViewById(R.id.addNameBusinessItemEdit)
            tilCount = findViewById(R.id.addCountBusinessItem)
            etCount = findViewById(R.id.addCountBusinessItemEdit)
            buttonAddBusinessItem = findViewById(R.id.saveBusinessItem)
        }

    }


    companion object {

        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

        fun newInstanceAddItem(): BusinessItemFragment {
            return BusinessItemFragment(MODE_ADD)
        }

        fun newInstanceEditItem(businessItemId: Int): BusinessItemFragment {
            return BusinessItemFragment(MODE_EDIT, businessItemId)
        }

    }

}