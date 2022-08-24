package com.example.mybusiness.domain

class DeleteItemMyBusinessUseCase(private val myBusinessRepository: MyBusinessRepository) {

    fun deleteItemMyBusiness(BusinessItem: BusinessItem){
        myBusinessRepository.deleteItemMyBusiness(BusinessItem)
    }

}