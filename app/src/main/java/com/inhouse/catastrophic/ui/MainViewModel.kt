package com.inhouse.catastrophic.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.inhouse.catastrophic.app.repo.CatRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repositoryDefault: CatRepository) : ViewModel() {
    companion object {
        const val TAG: String = "MainViewModel"
    }

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

    val catsList = repositoryDefault.catsList()

    private var pageIdx: Int = 1

    init {
        getCats()
    }

    private fun getCats() {
        viewModelScope.launch {
            try {
                repositoryDefault.fetchAndInsertCatsIntoDb(pageIdx)
            } catch (e: Exception) {
                Log.d(TAG, "error = $e")
            }

        }
    }

    fun loadMore() {
        getCats()
    }
}