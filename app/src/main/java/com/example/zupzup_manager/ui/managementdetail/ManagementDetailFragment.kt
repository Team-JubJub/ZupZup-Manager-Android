package com.example.zupzup_manager.ui.managementdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zupzup_manager.databinding.FragmentManagementDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementDetailFragment : Fragment() {

    private lateinit var binding: FragmentManagementDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentManagementDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}