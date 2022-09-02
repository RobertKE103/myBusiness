package com.example.mybusiness.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.mybusiness.R
import com.google.android.material.textfield.TextInputLayout

class BusinessItemActivity : AppCompatActivity() {

    private lateinit var viewModel: BusinessItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonAddBusinessItem: Button

    private var screenMode = ""
    private var businessItemId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_item)
        parseIntent()
        viewModel = ViewModelProvider(this)[BusinessItemViewModel::class.java]
        initViews()

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

        viewModel.errorInputCount.observe(this) {
            val message = if (it) {
                "Invalid count"
            } else {
                null
            }
            tilCount.error = message
        }
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                "Invalid name"
            } else {
                null
            }
            tilCount.error = message
        }

        viewModel.shouldCloseScreen.observe(this){
            finish()
        }
    }

    private fun launchEditMode() {
        viewModel.getBusinessItem(businessItemId)
        viewModel.businessItem.observe(this) {
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


    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("error")
        }

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)

        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("error")
        }

        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_BUSINESS_ITEM_ID)) {
                throw RuntimeException("error")
            }
            businessItemId = intent.getIntExtra(EXTRA_BUSINESS_ITEM_ID, -1)
        }


    }

    private fun initViews() {
        tilName = findViewById(R.id.addNameBusinessItem)
        etName = findViewById(R.id.addNameBusinessItemEdit)
        tilCount = findViewById(R.id.addCountBusinessItem)
        etCount = findViewById(R.id.addCountBusinessItemEdit)
        buttonAddBusinessItem = findViewById(R.id.saveBusinessItem)

    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_BUSINESS_ITEM_ID = "extra_business_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, BusinessItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, businessItemId: Int): Intent {
            val intent = Intent(context, BusinessItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_BUSINESS_ITEM_ID, businessItemId)
            return intent
        }
    }
}