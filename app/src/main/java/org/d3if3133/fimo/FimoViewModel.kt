package org.d3if3133.fimo

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.d3if3133.fimo.db.Item
import org.d3if3133.fimo.db.ItemDao

class FimoViewModel(private val itemDao: ItemDao) : ViewModel(){
    val alItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice)
        updateItem(updatedItem)
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
    ): Item {
        return Item(
            id = itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble()
        )
    }

    // update item
    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    fun addNewItem(itemName: String, itemPrice: String) {
        val newItem = getNewItemEntry(itemName, itemPrice)
        insertItem(newItem)
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble()
        )
    }

    // insert item
    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    // delete item
    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    fun isEntryValid(itemName: String, itemPrice: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank()) {
            return false
        }
        return true
    }
}

class FimoViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FimoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FimoViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}