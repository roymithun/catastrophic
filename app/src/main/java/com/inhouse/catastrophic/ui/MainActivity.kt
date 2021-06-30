package com.inhouse.catastrophic.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        binding.mainViewModel = mainViewModel
        configurePhotoRecyclerView()
    }

    private fun configurePhotoRecyclerView() {
        photoAdapter = PhotoAdapter()
        val recyclerView = binding.rvCatPhotos
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.adapter = photoAdapter
    }
}