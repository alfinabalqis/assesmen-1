package com.d3if3133.fimo.ui.bank

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.d3if3133.fimo.model.Bank
import com.d3if3133.fimo.network.ApiStatus
import com.d3if3133.fimo.network.BankApi
import com.d3if3133.fimo.network.UpdateWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class BankViewModel : ViewModel() {
    private val data = MutableLiveData<List<Bank>>()
    private val status = MutableLiveData<ApiStatus>()

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    init {
        retrieveData()
    }

    private fun retrieveData(){
        viewModelScope.launch (Dispatchers.IO){
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(BankApi.service.getBank())
                status.postValue(ApiStatus.SUCCESS)

            } catch (e: java.lang.Exception){
                Log.d("BankViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getData(): LiveData<List<Bank>> = data
    fun getStatus(): LiveData<ApiStatus> = status
    
}