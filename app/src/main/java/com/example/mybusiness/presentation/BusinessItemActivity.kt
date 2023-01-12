package com.example.mybusiness.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mybusiness.R

class BusinessItemActivity : AppCompatActivity() {

    private var screenMode = ""
    private var businessItemId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_item)
        parseIntent()
        if (savedInstanceState == null)
            launchRightMode()
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> BusinessItemFragment.newInstanceEditItem(businessItemId)
            MODE_ADD -> BusinessItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("error")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.business_item_container, fragment)
            .commit()
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
            businessItemId = intent.getIntExtra(EXTRA_BUSINESS_ITEM_ID, 0)
        }


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



