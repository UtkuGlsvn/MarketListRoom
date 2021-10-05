package com.utkuglsvn.roombasic.core.repository

import com.utkuglsvn.roombasic.core.local.room.MyListDao
import com.utkuglsvn.roombasic.core.local.room.entity.MyListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyListRepository @Inject constructor(private val db: MyListDao) {

    suspend fun insert(item: MyListItem) = db.insertListItem(item)
    suspend fun delete(item: MyListItem) = db.deleteItem(item)
    suspend fun update(item: MyListItem) = db.updateItem(item)
    fun getAllItems(): Flow<List<MyListItem>> = db.getListItem()
}