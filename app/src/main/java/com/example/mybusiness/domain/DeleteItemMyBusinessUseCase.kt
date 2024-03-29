package com.example.mybusiness.domain

class DeleteItemMyBusinessUseCase(private val myBusinessRepository: MyBusinessRepository) {

    suspend fun deleteItemMyBusiness(BusinessItem: BusinessItem){
        myBusinessRepository.deleteItemMyBusiness(BusinessItem)
    }

}