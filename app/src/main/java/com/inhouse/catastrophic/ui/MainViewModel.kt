package com.inhouse.catastrophic.ui

import android.util.Log
import androidx.lifecycle.*
import com.inhouse.catastrophic.app.repo.CatRepository
import com.inhouse.catastrophic.app.utils.Resource
import com.inhouse.catastrophic.app.utils.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val repositoryDefault: CatRepository) : ViewModel() {
    companion object {
        const val TAG: String = "MainViewModel"
    }

    private val _networkErrorStatus = MutableLiveData<Boolean?>()
    val networkError: LiveData<Boolean?> = _networkErrorStatus

    class MainViewModelFactory(private val repositoryDefault: CatRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repositoryDefault) as T
            }
            throw IllegalArgumentException("ViewModel is not assignable")
        }

    }

    private var pageIdx: Int = 1

    init {
        getCats()
    }

    val catsList = repositoryDefault.catsList()

    private fun getCats() {
        viewModelScope.launch {
            val result: Resource<Void> = repositoryDefault.fetchAndInsertCatsIntoDb(pageIdx)
            delay(100)
            _networkErrorStatus.postValue(
                when (result.status) {
                    Status.ERROR -> {
                        Log.d(
                            TAG,
                            "gibow catsList.value.isEmpty() = ${catsList.value?.isEmpty()} or null = ${catsList.value}"
                        )
                        catsList.value.isNullOrEmpty()
                    }
                    else -> false
                }
            )
        }
    }

    fun refreshForInitialDataFetch() {
        getCats()
    }

    fun resetNetworkErrorStatus() {
        _networkErrorStatus.value = null
    }

    fun loadMore() {
        pageIdx++
        getCats()
    }
}