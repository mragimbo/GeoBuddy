package com.k8596.ownproject

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [LocationListItem::class], version = 1)
abstract class LocationListRoomDatabase : RoomDatabase() {
    abstract fun locationListDao(): LocationListDao
}