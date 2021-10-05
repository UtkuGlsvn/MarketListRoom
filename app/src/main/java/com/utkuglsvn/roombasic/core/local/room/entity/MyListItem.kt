package com.utkuglsvn.roombasic.core.local.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "my_list_items")
data class MyListItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "itemName")
    var itemName: String,
    @ColumnInfo(name = "itemQuantity")
    var itemQuantity: Int
) : Parcelable