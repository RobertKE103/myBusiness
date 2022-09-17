package com.example.mybusiness.domain

import androidx.lifecycle.LiveData


interface MyBusinessRepository {

    suspend fun addItemMyBusiness(BusinessItem: BusinessItem)

    suspend fun deleteItemMyBusiness(BusinessItem: BusinessItem)

    suspend fun editItemMyBusiness(businessItem: BusinessItem)

    suspend fun getItemMyBusiness(id: Int): BusinessItem

    fun getBusinessList(): LiveData<List<BusinessItem>>

}