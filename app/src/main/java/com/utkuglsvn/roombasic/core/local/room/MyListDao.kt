package com.utkuglsvn.roombasic.core.local.room

import androidx.room.*
import com.utkuglsvn.roombasic.core.local.room.entity.MyListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListItem(item: MyListItem)

    @Delete
    suspend fun deleteItem(item: MyListItem)

    @Query("SELECT * FROM my_list_items")
    fun getListItem(): Flow<List<MyListItem>>

    @Update
    suspend fun updateItem(item: MyListItem)
}