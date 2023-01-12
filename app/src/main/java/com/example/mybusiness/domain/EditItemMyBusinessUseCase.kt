package com.example.mybusiness.domain

class EditItemMyBusinessUseCase(private val myBusinessRepository: MyBusinessRepository) {

    suspend fun editItemMyBusiness(businessItem: BusinessItem){
        myBusinessRepository.editItemMyBusiness(businessItem)
    }


}