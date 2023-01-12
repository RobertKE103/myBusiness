package com.example.mybusiness.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.mybusiness.domain.BusinessItem

class BusinessListDiffCallback(
    private val oldList: List<BusinessItem>,
    private val newList: List<BusinessItem>
    ): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}