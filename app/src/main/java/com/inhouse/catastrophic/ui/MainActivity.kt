package com.inhouse.catastrophic.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inhouse.catastrophic.app.BaseApplication
import com.inhouse.catastrophic.databinding.ActivityMainBinding
import com.inhouse.catastrophic.ui.adapter.PhotoAdapter
import com.inhouse.catastrophic.ui.di.MainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val mainComponent: MainComponent by lazy {
        (application as BaseApplication).appComponent.mainComponent().create(this@MainActivity)
    }

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "gibow onCreate")
        mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        binding.mainViewModel = mainViewModel
        mainViewModel.networkError.observe(this) {
            Log.d("MainActivity", "gibow networkError status = $it")
            it?.let {
                if (it) {
                    binding.rvCatPhotos.visibility = View.GONE
                    binding.llNetworkError.visibility = View.VISIBLE
                } else {
                    binding.rvCatPhotos.visibility = View.VISIBLE
                    binding.llNetworkError.visibility = View.GONE
                }
                mainViewModel.resetNetworkErrorStatus()
            }
        }
        configurePhotoRecyclerView()
    }

    override fun onRestart() {
        Log.d("MainActivity", "gibow onRestart")
        super.onRestart()
    }
    override fun onResume() {
        Log.d("MainActivity", "gibow onResume")
        super.onResume()
        binding.rvCatPhotos.addOnScrollListener(onScrollListener)
    }

    override fun onPause() {
        Log.d("MainActivity", "gibow onPause")
        super.onPause()
        binding.rvCatPhotos.removeOnScrollListener(onScrollListener)
    }

    private fun configurePhotoRecyclerView() {
        photoAdapter = PhotoAdapter()
        val recyclerView = binding.rvCatPhotos
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.adapter = photoAdapter
    }

    private val onScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                recyclerView.adapter?.let {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == it.itemCount - 1) {
                        mainViewModel.loadMore()
                    }
                }
            }
        }
    }
}