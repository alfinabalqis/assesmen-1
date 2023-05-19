package org.d3if3133.fimo.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3133.fimo.db.ItemDao

class FimoViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FimoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FimoViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}