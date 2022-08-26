package com.example.mybusiness.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mybusiness.domain.BusinessItem
import com.example.mybusiness.domain.MyBusinessRepository

object MyBusinessRepositoryImpl: MyBusinessRepository {

    private val myBusinessLD = MutableLiveData<List<BusinessItem>>()
    private val businessList = mutableListOf<BusinessItem>()
    private var autoIncrementId = 0


    override fun addItemMyBusiness(BusinessItem: BusinessItem) {
        if (BusinessItem.id == com.example.mybusiness.domain.BusinessItem.ID_USER)
            BusinessItem.id = autoIncrementId++
        businessList.add(BusinessItem)
        updateList()

    }

    override fun deleteItemMyBusiness(BusinessItem: BusinessItem) {
        businessList.remove(BusinessItem)
        updateList()
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

    override fun getBusinessList(): LiveData<List<BusinessItem>> {
        return myBusinessLD
    }

    private fun updateList(){
        myBusinessLD.value = businessList.toList()
    }


}