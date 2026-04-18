package com.example.effectivemobileproject.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.effectivemobileproject.R
import com.example.effectivemobileproject.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PagerSnapHelper()

        _binding = FragmentFavoritesBinding.bind(view)
    }
}