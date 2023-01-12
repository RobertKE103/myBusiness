package com.example.mybusiness.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mybusiness.data.room.AppDatabase
import com.example.mybusiness.domain.BusinessItem
import com.example.mybusiness.domain.MyBusinessRepository

class MyBusinessRepositoryImpl(
    application: Application
) : MyBusinessRepository {

    private var businessListDao = AppDatabase.getInstance(application).businessListDao()
    private val mapper = BusinessListMapper()

    override suspend fun addItemMyBusiness(BusinessItem: BusinessItem) {
        businessListDao.addBusinessItem(mapper.mapEntityToDbModel(BusinessItem))
    }

    override suspend fun deleteItemMyBusiness(BusinessItem: BusinessItem) {
        businessListDao.deleteBusinessItem(BusinessItem.id)
    }

    override suspend fun editItemMyBusiness(businessItem: BusinessItem) {
        businessListDao.addBusinessItem(mapper.mapEntityToDbModel(businessItem))
    }

    override suspend fun getItemMyBusiness(id: Int): BusinessItem {
        val dbModel = businessListDao.getBusinessItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getBusinessList(): LiveData<List<BusinessItem>> =
        MediatorLiveData<List<BusinessItem>>().apply {
            addSource(businessListDao.getBusinessList()) {
                value = mapper.mapListDbModelToListEntity(it)
            }
        }


}