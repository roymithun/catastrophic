package com.inhouse.catastrophic.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inhouse.catastrophic.app.repo.CatRepository
import com.inhouse.catastrophic.app.utils.Resource
import com.inhouse.catastrophic.app.utils.Status
import com.inhouse.catastrophic.ui.data.Cat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repositoryDefault: CatRepository) :
    ViewModel() {
    companion object {
        const val TAG: String = "HomeViewModel"
    }

    private val _networkErrorState = MutableLiveData<Boolean?>()
    val networkErrorState: LiveData<Boolean?> = _networkErrorState

    private val _navigateToCatDetail = MutableLiveData<Cat?>()
    val navigateToCatDetail: LiveData<Cat?> = _navigateToCatDetail

    private var pageIdx: Int = 1

    init {
        getCats()
    }

    val catsList = repositoryDefault.catsList()

    private fun getCats() {
        viewModelScope.launch {
//            Log.d(TAG, "pageIdx = $pageIdx")
            val result: Resource<Void> = repositoryDefault.fetchAndInsertCatsIntoDb(pageIdx)
            delay(100)
            _networkErrorState.postValue(
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
        _networkErrorState.value = null
    }

    fun displayCatPhoto(cat: Cat) {
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