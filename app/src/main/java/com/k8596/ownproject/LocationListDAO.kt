package com.k8596.ownproject

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface LocationListDao {

    @Query("SELECT * from location_list_table")
    fun getAll(): MutableList<LocationListItem>

    @Insert
    fun insert(item: LocationListItem) : Long

    @Query("DELETE FROM location_list_table WHERE id = :itemId")
    fun delete(itemId: Int)

}
