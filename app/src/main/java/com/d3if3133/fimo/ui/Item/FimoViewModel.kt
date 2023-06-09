package com.d3if3133.fimo.ui.Item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.d3if3133.fimo.data.ItemDao
import com.d3if3133.fimo.model.Bank
import com.d3if3133.fimo.model.Item
import com.d3if3133.fimo.network.BankApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the Inventory repository and an up-to-date list of all items.
 *
 */
class FimoViewModel(private val itemDao: ItemDao) : ViewModel() {
    private val listBank = MutableLiveData<List<Bank>>()

    val  data = itemDao.getItems()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO){
            try {
                val result = BankApi.service.getBank()
                Log.d("FimoViewModel", "Success: $result")
            } catch (e: Exception) {
                Log.d("FimoViewModel", "Failure: ${e.message}")
            }
        }
    }

    // Cache all items form the database using LiveData.
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    /**
     * Updates an existing Item in the database.
     */
    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice)
        updateItem(updatedItem)
    }


    /**
     * Launching a new coroutine to update an item in a non-blocking way
     */
    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    /**
     * Inserts the new Item into database.
     */
    fun addNewItem(itemName: String, itemPrice: String) {
        val newItem = getNewItemEntry(itemName, itemPrice)
        insertItem(newItem)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    /**
     * Launching a new coroutine to delete an item in a non-blocking way
     */
    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun deleteAllItems() {
        viewModelScope.launch {
            itemDao.deleteAll()
        }
    }

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(itemName: String, itemPrice: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Returns an instance of the [Item] entity class with the item info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewItemEntry(itemName: String, itemPrice: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble()
        )
    }

    /**
     * Called to update an existing entry in the Inventory database.
     * Returns an instance of the [Item] entity class with the item info updated by the user.
     */
    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String
    ): Item {
        return Item(
            id = itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble()
        )
    }
}
