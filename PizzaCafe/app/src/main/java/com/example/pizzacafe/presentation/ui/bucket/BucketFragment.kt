package com.example.pizzacafe.presentation.ui.bucket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pizzacafe.databinding.FragmentBucketBinding

class BucketFragment : Fragment() {

    private var _binding: FragmentBucketBinding? = null
    private val binding: FragmentBucketBinding
        get() = _binding ?: throw RuntimeException("FragmentBucketBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBucketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}