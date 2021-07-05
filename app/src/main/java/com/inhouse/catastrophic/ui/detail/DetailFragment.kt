package com.inhouse.catastrophic.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import coil.load
import com.inhouse.catastrophic.R

class DetailFragment : Fragment(R.layout.fragment_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedCat = DetailFragmentArgs.fromBundle(requireArguments()).selectedCat
        val ivPhoto: ImageView = view.findViewById(R.id.iv_photo)
        ivPhoto.load(selectedCat.url) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}