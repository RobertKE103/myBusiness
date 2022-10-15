package com.example.mybusiness.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mybusiness.data.MyBusinessRepositoryImpl
import com.example.mybusiness.domain.AddItemMyBusinessUseCase
import com.example.mybusiness.domain.BusinessItem
import com.example.mybusiness.domain.EditItemMyBusinessUseCase
import com.example.mybusiness.domain.GetMyBusinessItemUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BusinessItemViewModel(application: Application) : AndroidViewModel(application) {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean> get() = _errorInputCount

    private val _businessItem = MutableLiveData<BusinessItem>()
    val businessItem: LiveData<BusinessItem> get() = _businessItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit> get() = _shouldCloseScreen

    private val repository = MyBusinessRepositoryImpl(application)
    private val getMyBusinessItemUseCase = GetMyBusinessItemUseCase(repository)
    private val editItemMyBusinessUseCase = EditItemMyBusinessUseCase(repository)
    private val addItemMyBusinessUseCase = AddItemMyBusinessUseCase(repository)

    fun getBusinessItem(businessItemId: Int) {
        viewModelScope.launch {
            val item = getMyBusinessItemUseCase.getItemMyBusiness(businessItemId)
            _businessItem.postValue(item)
        }
    }

    fun addBusinessItem(name: String?, count: String?) {
        viewModelScope.launch {
            val inputName = parseName(name)
            val countB = parseCount(count)
            val fieldsValid = validateInput(inputName, countB)
            if (fieldsValid) {
                val businessItem = BusinessItem(inputName, countB, true)
                addItemMyBusinessUseCase.addItemMyBusiness(businessItem)
                closeScreen()
            }
        }


    }

    fun editBusinessItem(name: String?, count: String?) {
        viewModelScope.launch {
            val inputName = parseName(name)
            val countB = parseCount(count)
            val fieldsValid = validateInput(inputName, countB)
            if (fieldsValid) {
                _businessItem.value?.let {
                    val item = it.copy(name = inputName, count = countB)
                    editItemMyBusinessUseCase.editItemMyBusiness(item)
                    closeScreen()
                }
            }
        }
    }


    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun closeScreen() {
        _shouldCloseScreen.postValue(Unit)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}