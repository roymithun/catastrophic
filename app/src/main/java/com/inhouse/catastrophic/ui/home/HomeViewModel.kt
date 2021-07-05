package com.inhouse.catastrophic.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.inhouse.catastrophic.app.repo.CatRepository
import com.inhouse.catastrophic.app.utils.Resource
import com.inhouse.catastrophic.app.utils.Status
import com.inhouse.catastrophic.ui.data.Cat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val repositoryDefault: CatRepository) : ViewModel() {
    companion object {
        const val TAG: String = "HomeViewModel"
    }

    private val _networkErrorStatus = MutableLiveData<Boolean?>()
    val networkError: LiveData<Boolean?> = _networkErrorStatus

    private val _navigateToCatDetail = MutableLiveData<Cat?>()
    val navigateToCatDetail: LiveData<Cat?> = _navigateToCatDetail

    class HomeViewModelFactory(private val repositoryDefault: CatRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repositoryDefault) as T
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
            Log.d(TAG, "pageIdx = $pageIdx")
            val result: Resource<Void> = repositoryDefault.fetchAndInsertCatsIntoDb(pageIdx)
            delay(100)
            _networkErrorStatus.postValue(
                when (result.status) {
                    Status.ERROR -> {
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

    fun displayCatPhoto(cat: Cat) {
        Log.d(TAG, "gibow displayCatPhoto cat = $cat")
        _navigateToCatDetail.value = cat
    }

    fun completeNavigationToDetail() {
        _navigateToCatDetail.value = null
    }

    fun loadMore() {
        pageIdx++
        getCats()
    }
}