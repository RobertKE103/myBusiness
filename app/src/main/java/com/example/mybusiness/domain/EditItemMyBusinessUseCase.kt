package com.example.mybusiness.domain

class EditItemMyBusinessUseCase(private val myBusinessRepository: MyBusinessRepository) {

    fun editItemMyBusiness(businessItem: BusinessItem){
        myBusinessRepository.editItemMyBusiness(businessItem)
    }


}