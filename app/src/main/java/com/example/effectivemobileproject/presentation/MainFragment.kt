package com.example.effectivemobileproject.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.effectivemobileproject.R
import com.example.effectivemobileproject.databinding.FragmentMainBinding
import com.example.effectivemobileproject.presentation.adapters.courseAdapterDelegate
import com.example.effectivemobileproject.presentation.state.MainState
import com.example.effectivemobileproject.presentation.viewmodels.MainViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModel()
    
    private val adapter by lazy {
        ListDelegationAdapter(
            courseAdapterDelegate { course ->
                viewModel.toggleFavorite(course.id)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        setupRecyclerView()
        observeViewModel()

        binding.mainFilterTextContainer.setOnClickListener {
            viewModel.toggleSort()
        }
    }

    private fun setupRecyclerView() {
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is MainState.Loading -> {
                            binding.mainProgressBar.isVisible = true
                            binding.mainRecyclerView.isVisible = false
                        }
                        is MainState.Success -> {
                            binding.mainProgressBar.isVisible = false
                            binding.mainRecyclerView.isVisible = true
                            adapter.items = state.courses
                            adapter.notifyDataSetChanged()
                        }
                        is MainState.Error -> {
                            binding.mainProgressBar.isVisible = false
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
