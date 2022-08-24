package com.example.mybusiness.data

import com.example.mybusiness.domain.BusinessItem
import com.example.mybusiness.domain.MyBusinessRepository

object MyBusinessRepositioryImpl: MyBusinessRepository {

    private val businessList = mutableListOf<BusinessItem>()
    private var autoIncrementId = 0

    override fun addItemMyBusiness(BusinessItem: BusinessItem) {
        if (BusinessItem.id == com.example.mybusiness.domain.BusinessItem.ID_USER)
            BusinessItem.id = autoIncrementId++
        businessList.add(BusinessItem)
    }

    override fun deleteItemMyBusiness(BusinessItem: BusinessItem) {
        businessList.remove(BusinessItem)
    }

    override fun editItemMyBusiness(businessItem: BusinessItem) {
        val oldElement = getItemMyBusiness(businessItem.id)
        businessList.remove(oldElement)
        addItemMyBusiness(businessItem)
    }

    override fun getItemMyBusiness(id: Int): BusinessItem {
        return businessList.find {
            it.id == id
        } ?: throw RuntimeException("Null")
    }

    override fun getBusinessList(): List<BusinessItem> {
        return businessList.toList()
    }


}