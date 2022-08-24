package com.example.mybusiness.domain

class AddItemMyBusinessUseCase(private val myBusinessRepository: MyBusinessRepository) {

    fun addItemMyBusiness(BusinessItem: BusinessItem){
        myBusinessRepository.addItemMyBusiness(BusinessItem)
    }


}