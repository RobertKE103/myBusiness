package com.example.mybusiness.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mybusiness.domain.BusinessItemDbModel

@Dao
interface BusinessListDao {

    @Query("SELECT * FROM business_item")
    fun getBusinessList(): LiveData<List<BusinessItemDbModel>>

    @Query("SELECT * FROM business_item WHERE id=:businessItemId LIMIT 1")
    suspend fun getBusinessItem(businessItemId: Int): BusinessItemDbModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBusinessItem(businessItemDbModel: BusinessItemDbModel)

    @Query("DELETE FROM business_item WHERE id=:businessItemId")
    suspend fun deleteBusinessItem(businessItemId: Int)


}