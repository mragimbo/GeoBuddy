package com.k8596.ownproject

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "location_list_table")
data class LocationListItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String?,
    var lat: Double?,
    var lng: Double?
)