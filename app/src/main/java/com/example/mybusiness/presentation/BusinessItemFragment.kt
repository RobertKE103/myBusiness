package com.example.mybusiness.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mybusiness.databinding.FragmentBusinessItemBinding


class BusinessItemFragment(
    private val screenMode: String = "",
    private val businessItemId: Int = 0
) : Fragment() {

    private var _binding: FragmentBusinessItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BusinessItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)[BusinessItemViewModel::class.java]

        binding.addNameBusinessItemEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.addCountBusinessItemEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.addCountBusinessItemEdit.addTextChangedListener {
            binding.addCountBusinessItem.error = null
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
            binding.addCountBusinessItem.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Invalid name"
            } else {
                null
            }
            binding.addNameBusinessItem.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }
    }

    private fun launchEditMode() {
        viewModel.getBusinessItem(businessItemId)
        viewModel.businessItem.observe(viewLifecycleOwner) {
            binding.addNameBusinessItemEdit.setText(it.name)
            binding.addCountBusinessItemEdit.setText(it.count.toString())
        }
        binding.saveBusinessItem.setOnClickListener {
            viewModel.editBusinessItem(binding.addNameBusinessItemEdit.text?.toString(),
                binding.addCountBusinessItemEdit.text?.toString())
        }

    }

    private fun launchAddMode() {
        binding.saveBusinessItem.setOnClickListener {
            viewModel.addBusinessItem(binding.addNameBusinessItemEdit.text?.toString(),
                binding.addCountBusinessItemEdit.text?.toString())
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