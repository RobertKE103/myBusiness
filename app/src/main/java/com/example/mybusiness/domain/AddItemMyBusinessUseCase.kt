package com.example.mybusiness.domain

class AddItemMyBusinessUseCase(private val myBusinessRepository: MyBusinessRepository) {

    suspend fun addItemMyBusiness(BusinessItem: BusinessItem){
        myBusinessRepository.addItemMyBusiness(BusinessItem)
    }


}