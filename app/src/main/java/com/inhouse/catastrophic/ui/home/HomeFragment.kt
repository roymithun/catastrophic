package com.inhouse.catastrophic.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inhouse.catastrophic.databinding.FragmentHomeBinding
import com.inhouse.catastrophic.ui.data.Cat
import com.inhouse.catastrophic.ui.home.adapter.PhotoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var rvCatPhotos: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var gridLayoutManager1: GridLayoutManager
    private lateinit var gridLayoutManager2: GridLayoutManager
    private lateinit var gridLayoutManager3: GridLayoutManager
    private lateinit var currentLayoutManager: RecyclerView.LayoutManager
    private lateinit var scaleGestureDetector: ScaleGestureDetector


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        binding.homeViewModel = homeViewModel
        homeViewModel.networkError.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    binding.rvCatPhotos.visibility = View.GONE
                    binding.llNetworkError.visibility = View.VISIBLE
                } else {
                    binding.rvCatPhotos.visibility = View.VISIBLE
                    binding.llNetworkError.visibility = View.GONE
                }
                homeViewModel.resetNetworkErrorStatus()
            }
        }
        homeViewModel.navigateToCatDetail.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        it
                    )
                )
                homeViewModel.completeNavigationToDetail()
            }
        }
        configurePhotoRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        mainComponent.inject(this)
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
        gridLayoutManager1 = GridLayoutManager(requireContext(), 1)
        gridLayoutManager2 = GridLayoutManager(requireContext(), 2)
        gridLayoutManager3 = GridLayoutManager(requireContext(), 3)

        scaleGestureDetector = ScaleGestureDetector(requireContext(), ScaleListener())
        binding.rvCatPhotos.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            false
        }

        photoAdapter = PhotoAdapter(object : PhotoAdapter.OnClickListener {
            override fun onClick(cat: Cat) {
                homeViewModel.displayCatPhoto(cat)
            }
        })
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
                        homeViewModel.loadMore()
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
            Log.d(
                "HomeFragment",
                "detector.currentSpan = ${detector.currentSpan} | detector.previousSpan = ${detector.previousSpan}"
            )
            if (detector.currentSpan > 200 && detector.timeDelta > 200) {
                val pinchSpan = detector.currentSpan - detector.previousSpan
                if (pinchSpan < -1) {
                    if (currentLayoutManager == gridLayoutManager1) {
                        currentLayoutManager = gridLayoutManager3
                        rvCatPhotos.layoutManager = gridLayoutManager3
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