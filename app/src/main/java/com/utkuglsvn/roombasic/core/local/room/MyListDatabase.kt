package com.utkuglsvn.roombasic.core.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.utkuglsvn.roombasic.core.local.room.entity.MyListItem

@Database(entities = [MyListItem::class], version = 1)
abstract class MyListDatabase : RoomDatabase() {

    abstract fun getMyList(): MyListDao

    companion object {
        const val DATABASE_NAME = "my_list_db"
    }

}