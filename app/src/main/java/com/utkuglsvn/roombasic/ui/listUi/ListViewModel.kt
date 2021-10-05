package com.utkuglsvn.roombasic.ui.listUi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utkuglsvn.roombasic.core.local.room.entity.MyListItem
import com.utkuglsvn.roombasic.core.repository.MyListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repo: MyListRepository) : ViewModel() {

    private val _list = MutableLiveData<List<MyListItem>>()
    val list: LiveData<List<MyListItem>>
        get() = _list


    fun insertItem(item: MyListItem) {
        viewModelScope.launch {
            repo.insert(item)
        }
    }

    fun updateItem(item: MyListItem) {
        viewModelScope.launch {
            repo.update(item)
        }
    }

    fun deleteItem(item: MyListItem) {
        viewModelScope.launch {
            repo.delete(item)
        }
    }

    fun listItem() {
        viewModelScope.launch {
            repo.getAllItems().flowOn(Dispatchers.Default)
                .onEach {
                    _list.postValue(it)
                }
                .launchIn(viewModelScope)
        }
    }
}