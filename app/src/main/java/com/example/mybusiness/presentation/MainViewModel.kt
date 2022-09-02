package com.example.mybusiness.presentation

import androidx.lifecycle.ViewModel
import com.example.mybusiness.data.MyBusinessRepositoryImpl
import com.example.mybusiness.domain.BusinessItem
import com.example.mybusiness.domain.DeleteItemMyBusinessUseCase
import com.example.mybusiness.domain.EditItemMyBusinessUseCase
import com.example.mybusiness.domain.GetMyBusinessListUseCase

class MainViewModel: ViewModel() {

    private var repository = MyBusinessRepositoryImpl
    private val getMyBusinessListUseCase = GetMyBusinessListUseCase(repository)
    private val deleteMyBusinessItemUseCase = DeleteItemMyBusinessUseCase(repository)
    private val editMyBusinessItemUseCase = EditItemMyBusinessUseCase(repository)

    val businessList = getMyBusinessListUseCase.getBusinessList()


    fun deleteMyBusinessItem(businessItem: BusinessItem){
        deleteMyBusinessItemUseCase.deleteItemMyBusiness(businessItem)
    }

    fun editMyBusinessItem(businessItem: BusinessItem){
        val newItem = businessItem.copy()
        editMyBusinessItemUseCase.editItemMyBusiness(newItem)
    }


    fun changeEnabledState(businessItem: BusinessItem){
        val newItem = businessItem.copy(enabled = !businessItem.enabled)
        editMyBusinessItemUseCase.editItemMyBusiness(newItem)
    }

}