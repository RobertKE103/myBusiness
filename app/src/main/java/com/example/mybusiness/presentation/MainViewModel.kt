package com.example.mybusiness.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybusiness.data.MyBusinessRepositoryImpl
import com.example.mybusiness.domain.BusinessItem
import com.example.mybusiness.domain.DeleteItemMyBusinessUseCase
import com.example.mybusiness.domain.EditItemMyBusinessUseCase
import com.example.mybusiness.domain.GetMyBusinessListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var repository = MyBusinessRepositoryImpl(application)
    private val getMyBusinessListUseCase = GetMyBusinessListUseCase(repository)
    private val deleteMyBusinessItemUseCase = DeleteItemMyBusinessUseCase(repository)
    private val editMyBusinessItemUseCase = EditItemMyBusinessUseCase(repository)

    val businessList = getMyBusinessListUseCase.getBusinessList()


    fun deleteMyBusinessItem(businessItem: BusinessItem){
        viewModelScope.launch {
            deleteMyBusinessItemUseCase.deleteItemMyBusiness(businessItem)
        }
    }

    fun changeEnabledState(businessItem: BusinessItem){
        val newItem = businessItem.copy(enabled = !businessItem.enabled)
        viewModelScope.launch {
            editMyBusinessItemUseCase.editItemMyBusiness(newItem)

        }

    }
}