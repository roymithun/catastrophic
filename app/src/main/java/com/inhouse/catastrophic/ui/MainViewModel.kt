package com.inhouse.catastrophic.ui

import android.util.Log
import androidx.lifecycle.*
import com.inhouse.catastrophic.ui.data.Cat
import com.inhouse.catastrophic.ui.data.CatsApi
import kotlinx.coroutines.launch

class MainViewModel(private val catsApi: CatsApi) : ViewModel() {
    companion object {
        const val TAG: String = "MainViewModel"
        const val RESPONSE_LIMIT = 20
    }

    class MainViewModelFactory(private val catsApi: CatsApi) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(catsApi) as T
            }
            throw IllegalArgumentException("ViewModel is not assignable")
        }

    }

    private val _catsList = MutableLiveData<List<Cat>>()
    val catList: LiveData<List<Cat>>
        get() = _catsList

    private var pageIdx: Int = 1

    init {
        getCats()
    }

    private fun getCats() {
        viewModelScope.launch {
            try {
                val result: List<Cat>? = catsApi.getCats(RESPONSE_LIMIT, pageIdx, "png")
                Log.d(TAG, "result = $result")
                result?.let { _catsList.value = result }
            } catch (e: Exception) {
                Log.d(TAG, "error = $e")
            }

        }
    }
}