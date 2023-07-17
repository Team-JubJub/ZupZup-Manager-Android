package com.example.zupzup_manager.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zupzup_manager.databinding.FragmentStoreBinding
import com.example.zupzup_manager.ui.setting.SettingFragmentDirections
import com.example.zupzup_manager.ui.store.StoreFragmentArgs.Companion.fromBundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding
    private val storeViewModel: StoreViewModel by viewModels()

    private val storeClickListener = object : StoreClickListener {
        override fun navigateToBackStack() {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            clickListener = storeClickListener
            store = StoreFragmentArgs.fromBundle(requireArguments()).store
        }
    }

    interface StoreClickListener {
        fun navigateToBackStack()
    }
}