package com.example.mybusiness.domain

interface MyBusinessRepository {

    fun addItemMyBusiness(BusinessItem: BusinessItem)

    fun deleteItemMyBusiness(BusinessItem: BusinessItem)

    fun editItemMyBusiness(businessItem: BusinessItem)

    fun getItemMyBusiness(id: Int): BusinessItem

    fun getBusinessList(): List<BusinessItem>

}