package com.example.mybusiness.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mybusiness.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BusinessListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRv()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.businessList.observe(this) {
            adapter.businessList = it
        }

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_business_item)
        buttonAddItem.setOnClickListener {
            val intent = BusinessItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRv() {
        val rvBusinessList = findViewById<RecyclerView>(R.id.rv_business_list)
        adapter = BusinessListAdapter()
        rvBusinessList.adapter = adapter
        setupLongClickListener()
        setupClickListener()
        setupSwipeClickListener(rvBusinessList)
    }

    private fun setupSwipeClickListener(rvBusinessList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.businessList[viewHolder.adapterPosition]
                viewModel.deleteMyBusinessItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvBusinessList)
    }

    private fun setupClickListener() {
        adapter.onBusinessItemClickListener = {
//            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            val intent = BusinessItemActivity.newIntentEditItem(this, it.id )
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        adapter.onBusinessItemLongClickListener = {
            viewModel.changeEnabledState(it)
        }
    }
}