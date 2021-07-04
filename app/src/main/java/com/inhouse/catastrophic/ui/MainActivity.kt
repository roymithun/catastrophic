package com.inhouse.catastrophic.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ScaleGestureDetector
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
    private lateinit var rvCatPhotos: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var gridLayoutManager1: GridLayoutManager
    private lateinit var gridLayoutManager2: GridLayoutManager
    private lateinit var gridLayoutManager3: GridLayoutManager
    private lateinit var currentLayoutManager: RecyclerView.LayoutManager
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        binding.mainViewModel = mainViewModel
        mainViewModel.networkError.observe(this) {
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

    override fun onResume() {
        super.onResume()
        binding.rvCatPhotos.addOnScrollListener(onScrollListener)
    }

    override fun onPause() {
        super.onPause()
        binding.rvCatPhotos.removeOnScrollListener(onScrollListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configurePhotoRecyclerView() {
        rvCatPhotos = binding.rvCatPhotos
        gridLayoutManager1 = GridLayoutManager(this, 1)
        gridLayoutManager2 = GridLayoutManager(this, 2)
        gridLayoutManager3 = GridLayoutManager(this, 3)

        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        binding.rvCatPhotos.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            false
        }

        photoAdapter = PhotoAdapter()
        currentLayoutManager = gridLayoutManager3
        rvCatPhotos.layoutManager = gridLayoutManager3
        rvCatPhotos.setHasFixedSize(true)
        rvCatPhotos.setItemViewCacheSize(20)
        rvCatPhotos.adapter = photoAdapter
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

    /**
     * Gesture detector to manage grid layout column span
     */
    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            if (detector.currentSpan > 200 && detector.timeDelta > 200) {
                val pinchSpan = detector.currentSpan - detector.previousSpan
                if (pinchSpan < -1) {
                    if (currentLayoutManager == gridLayoutManager1) {
                        currentLayoutManager = gridLayoutManager2
                        rvCatPhotos.layoutManager = gridLayoutManager2
                        return true
                    } else if (currentLayoutManager == gridLayoutManager2) {
                        currentLayoutManager = gridLayoutManager3
                        rvCatPhotos.layoutManager = gridLayoutManager3
                        return true
                    }
                } else if (pinchSpan > 1) {
                    if (currentLayoutManager == gridLayoutManager3) {
                        currentLayoutManager = gridLayoutManager2
                        rvCatPhotos.layoutManager = gridLayoutManager2
                        return true
                    } else if (currentLayoutManager == gridLayoutManager2) {
                        currentLayoutManager = gridLayoutManager1
                        rvCatPhotos.layoutManager = gridLayoutManager1
                        return true
                    }
                }
            }
            return false
        }
    }
}