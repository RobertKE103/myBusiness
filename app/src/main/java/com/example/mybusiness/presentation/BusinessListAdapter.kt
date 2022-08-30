package com.example.mybusiness.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mybusiness.R
import com.example.mybusiness.domain.BusinessItem

class BusinessListAdapter : RecyclerView.Adapter<BusinessListAdapter.MyViewHolder>() {

    var businessList = listOf<BusinessItem>()
        set(value) {
            val callback = BusinessListDiffCallback(businessList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onBusinessItemLongClickListener: ((BusinessItem) -> Unit)? = null
    var onBusinessItemClickListener: ((BusinessItem) -> Unit)? = null

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameT = view.findViewById<TextView>(R.id.nameItemBusiness)!!
        val countT = view.findViewById<TextView>(R.id.countBusinessItem)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = when (viewType) {
            TYPE_ENABLED -> R.layout.item_business_enabled
            TYPE_DISABLED -> R.layout.item_business_disabled
            else -> throw RuntimeException("")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val businessItem = businessList[position]
        holder.nameT.text = businessItem.name
        holder.countT.text = businessItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onBusinessItemLongClickListener?.invoke(businessItem)
            true
        }
        holder.itemView.setOnClickListener {
            onBusinessItemClickListener?.invoke(businessItem)
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = businessList[position]
        return if (item.enabled){
            TYPE_ENABLED
        }else{
            TYPE_DISABLED
        }
    }

    override fun getItemCount(): Int = businessList.size

    companion object {
        const val TYPE_ENABLED = 101
        const val TYPE_DISABLED = 100
    }
}