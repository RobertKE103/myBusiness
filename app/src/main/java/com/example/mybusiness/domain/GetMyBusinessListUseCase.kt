package com.example.mybusiness.domain

class GetMyBusinessListUseCase(private val myBusinessRepository: MyBusinessRepository) {

    fun getBusinessList(): List<BusinessItem>{
        return myBusinessRepository.getBusinessList()
    }

}