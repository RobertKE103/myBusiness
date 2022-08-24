package com.example.mybusiness.domain

class GetMyBusinessItemUseCase(private val myBusinessRepository: MyBusinessRepository) {

    fun getItemMyBusiness(id: Int): BusinessItem {
        return myBusinessRepository.getItemMyBusiness(id)
    }

}