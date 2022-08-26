package com.example.mybusiness.domain

import androidx.lifecycle.LiveData

class GetMyBusinessListUseCase(private val myBusinessRepository: MyBusinessRepository) {

    fun getBusinessList(): LiveData<List<BusinessItem>>{
        return myBusinessRepository.getBusinessList()
    }

}