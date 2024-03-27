package com.example.pizzacafe.presentation.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pizzacafe.PizzaCafeApplication
import com.example.pizzacafe.databinding.FragmentMenuBinding
import com.example.pizzacafe.presentation.ViewModelFactory
import com.example.pizzacafe.presentation.adapters.MenuCategoryAdapter
import com.example.pizzacafe.presentation.adapters.MenuItemListAdapter
import com.example.pizzacafe.presentation.ui.state.DisplayingMenuItemsState
import com.example.pizzacafe.presentation.ui.state.ErrorState
import com.example.pizzacafe.presentation.ui.state.LoadingMenuItemsState
import javax.inject.Inject

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentMenuBinding is null")

    private lateinit var menuAdapter: MenuItemListAdapter
    private lateinit var categoryAdapter: MenuCategoryAdapter

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
        setupCategoryClickListeners()
    }

    private fun setupRecyclerView() = with(binding) {
        menuRecyclerView.recycledViewPool.setMaxRecycledViews(MenuItemListAdapter.MENU_ITEM_VIEW_TYPE, 10)
        categoryRecyclerView.recycledViewPool.setMaxRecycledViews(MenuCategoryAdapter.ENABLED_TYPE, 1)
        categoryRecyclerView.recycledViewPool.setMaxRecycledViews(MenuCategoryAdapter.DISABLED_TYPE, 5)
        menuAdapter = MenuItemListAdapter()
        categoryAdapter = MenuCategoryAdapter()
        menuRecyclerView.adapter = menuAdapter
        categoryRecyclerView.adapter = categoryAdapter
    }

    private fun setupObservers() {
        setupMenuStateObserver()
        setupToolBarStateObserver()
        setupCategoriesObserver()
    }

    private fun setupMenuStateObserver() {
        viewModel.menuState.observe(viewLifecycleOwner) {
            if (it is LoadingMenuItemsState) {
                binding.menuProgressBar.visibility = View.VISIBLE
                menuAdapter.submitList(null)
                categoryAdapter.onClickListener = {}
            } else {
                binding.menuProgressBar.visibility = View.INVISIBLE
                setupCategoryClickListeners()
            }
            if (it is ErrorState)
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
            if (it is DisplayingMenuItemsState)
                menuAdapter.submitList(it.list)
        }
    }

    private fun setupToolBarStateObserver() {
        viewModel.toolBarState.observe(viewLifecycleOwner) {
            if (it.firstBanner != null) {
                binding.firstImage.setImageBitmap(it.firstBanner.image)
                binding.firstImageProgress.visibility = View.INVISIBLE
            } else
                binding.firstImageProgress.visibility = View.VISIBLE

            if (it.secondBanner != null) {
                binding.secondImage.setImageBitmap(it.secondBanner.image)
                binding.secondImageProgress.visibility = View.INVISIBLE
            } else
                binding.secondImageProgress.visibility = View.VISIBLE
        }
    }

    private fun setupCategoriesObserver() {
        viewModel.categoryState.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it.list)
        }
    }

    private fun setupCategoryClickListeners() {
        categoryAdapter.onClickListener = {
            viewModel.selectCategory(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}