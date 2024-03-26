package com.example.pizzacafe.presentation.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pizzacafe.PizzaCafeApplication
import com.example.pizzacafe.databinding.FragmentMenuBinding
import com.example.pizzacafe.presentation.ViewModelFactory
import com.example.pizzacafe.presentation.adapters.MenuItemListAdapter
import javax.inject.Inject

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentMenuBinding is null")

    private lateinit var pizzaListAdapter: MenuItemListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MenuViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as PizzaCafeApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        pizzaListAdapter = MenuItemListAdapter()
        binding.recyclerView.adapter = pizzaListAdapter
        binding.recyclerView.recycledViewPool.setMaxRecycledViews(MenuItemListAdapter.PIZZA_VIEW_TYPE, 10)
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}