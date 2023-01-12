package com.example.mybusiness.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mybusiness.R
import com.example.mybusiness.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BusinessListAdapter
    private var businessItemContainer: FragmentContainerView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        businessItemContainer = binding.fragmentContainerFromMain
        setupRv()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.businessList.observe(this) {
            adapter.businessList = it
        }

        binding.buttonAddBusinessItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = BusinessItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(BusinessItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStackImmediate()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_from_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode(): Boolean{
        return businessItemContainer == null
    }

    private fun setupRv() {
        val rvBusinessList = binding.rvBusinessList
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
                val item = adapter.businessList[viewHolder.bindingAdapterPosition]
                viewModel.deleteMyBusinessItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvBusinessList)
    }

    private fun setupClickListener() {
        adapter.onBusinessItemClickListener = {
            if (isOnePaneMode()) {
                val intent = BusinessItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(BusinessItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        adapter.onBusinessItemLongClickListener = {
            viewModel.changeEnabledState(it)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}