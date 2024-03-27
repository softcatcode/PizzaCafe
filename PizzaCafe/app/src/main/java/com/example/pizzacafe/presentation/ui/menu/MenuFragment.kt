package com.example.pizzacafe.presentation.ui.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pizzacafe.PizzaCafeApplication
import com.example.pizzacafe.R
import com.example.pizzacafe.databinding.FragmentMenuBinding
import com.example.pizzacafe.presentation.ViewModelFactory
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
        recyclerView.recycledViewPool.setMaxRecycledViews(MenuItemListAdapter.MENU_ITEM_VIEW_TYPE, 10)
        menuAdapter = MenuItemListAdapter()
        binding.recyclerView.adapter = menuAdapter
    }

    private fun setupObservers() {
        setupMenuStateObserver()
        setupToolBarStateObserver()
    }

    private fun setupMenuStateObserver() {
        viewModel.menuState.observe(viewLifecycleOwner) {
            setCategoryHighlight(binding.pizzaSectionText, it.category == MenuSection.Pizza)
            setCategoryHighlight(binding.comboSectionText, it.category == MenuSection.Combo)
            setCategoryHighlight(binding.dessertsSectionText, it.category == MenuSection.Desserts)
            setCategoryHighlight(binding.drinksSectionText, it.category == MenuSection.Drinks)

            if (it is LoadingMenuItemsState) {
                binding.menuProgressBar.visibility = View.VISIBLE
                menuAdapter.submitList(null)
                disableCategoryClicks()
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setCategoryHighlight(textView: TextView, enabled: Boolean) {
        val designId = when (enabled) {
            true -> R.drawable.enabled_menu_section_label_design
            false -> R.drawable.disabled_menu_section_label_design
        }
        val textColorId = when(enabled) {
            true -> R.color.enabled_menu_section_text_color
            false -> R.color.disabled_menu_section_text_color
        }
        context?.getDrawable(designId)?.let { textView.background = it }
        context?.getColor(textColorId)?.let { textView.setTextColor(it) }
    }

    private fun disableCategoryClicks() {
        binding.pizzaSection.setOnClickListener {}
        binding.comboSection.setOnClickListener {}
        binding.dessertsSection.setOnClickListener {}
        binding.drinksSection.setOnClickListener {}
    }

    private fun setupCategoryClickListeners() {
        binding.pizzaSection.setOnClickListener {
            viewModel.loadMenu(MenuSection.Pizza)
        }
        binding.comboSection.setOnClickListener {
            viewModel.loadMenu(MenuSection.Combo)
        }
        binding.dessertsSection.setOnClickListener {
            viewModel.loadMenu(MenuSection.Desserts)
        }
        binding.drinksSection.setOnClickListener {
            viewModel.loadMenu(MenuSection.Drinks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}