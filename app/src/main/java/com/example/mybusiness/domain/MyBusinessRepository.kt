package com.example.mybusiness.domain

import androidx.lifecycle.LiveData


interface MyBusinessRepository {

    fun addItemMyBusiness(BusinessItem: BusinessItem)

    fun deleteItemMyBusiness(BusinessItem: BusinessItem)

    fun editItemMyBusiness(businessItem: BusinessItem)

    fun getItemMyBusiness(id: Int): BusinessItem

    fun getBusinessList(): LiveData<List<BusinessItem>>

}