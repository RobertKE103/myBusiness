package com.example.mybusiness.data

import com.example.mybusiness.domain.BusinessItem
import com.example.mybusiness.domain.BusinessItemDbModel

class BusinessListMapper {

    fun mapEntityToDbModel(businessItem: BusinessItem): BusinessItemDbModel = BusinessItemDbModel(
        id = businessItem.id,
        name = businessItem.name,
        count = businessItem.count,
        enabled = businessItem.enabled
    )

    fun mapDbModelToEntity(businessItemDbModel: BusinessItemDbModel): BusinessItem = BusinessItem(
        id = businessItemDbModel.id,
        name = businessItemDbModel.name,
        count = businessItemDbModel.count,
        enabled = businessItemDbModel.enabled
    )

    fun mapListDbModelToListEntity(listBusinessItemDbModel: List<BusinessItemDbModel>) =
        listBusinessItemDbModel.map {
            mapDbModelToEntity(it)
        }

}