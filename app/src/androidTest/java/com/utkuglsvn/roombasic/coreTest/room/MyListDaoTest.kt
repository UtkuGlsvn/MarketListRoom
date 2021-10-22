package com.utkuglsvn.roombasic.coreTest.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.utkuglsvn.roombasic.core.local.room.MyListDao
import com.utkuglsvn.roombasic.core.local.room.MyListDatabase
import com.utkuglsvn.roombasic.core.local.room.entity.MyListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class MyListDaoTest {
    private lateinit var database: MyListDatabase
    private lateinit var dao: MyListDao

    @ExperimentalCoroutinesApi
    var testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRole = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyListDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.getMyList()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun insertItem() = runBlockingTest {
        val listItem = MyListItem(1, "test", 1000)
        dao.insertListItem(listItem)

        val allListItem = dao.getListItem().first()
        assertThat(allListItem).contains(listItem)
    }

    @Test
    fun deleteItem() = runBlockingTest {
        val listItem = MyListItem(1, "test", 1000)
        dao.insertListItem(listItem)
        dao.deleteItem(listItem)

        val allListItem = dao.getListItem().first()
        assertThat(allListItem).doesNotContain(listItem)
    }

    @Test
    fun updateItem() = runBlockingTest {
        val listItem = MyListItem(1, "test", 1000)
        dao.insertListItem(listItem)
        val copyListItem = listItem.copy(1, "test2", 100)
        dao.updateItem(copyListItem)
        
        val allListItem = dao.getListItem().first()
        assertThat(allListItem).contains(copyListItem)
    }

    @After
    fun finish() {
        database.close()
        testDispatcher.cleanupTestCoroutines()
    }
}