package com.example.zupzup_manager.ui.management

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zupzup_manager.databinding.FragmentManagementBinding
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.ui.common.UiEventState
import com.example.zupzup_manager.ui.management.recyclerview.ManagementRcvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManagementFragment : Fragment() {
    private var _binding: FragmentManagementBinding? = null
    private val binding get() = _binding!!

    private val managementViewModel: ManagementViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManagementBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initRecyclerView()
    }

    private fun navigateToManagementDetail(store: StoreModel) {
        val action =
            ManagementFragmentDirections.actionFragManagementToManagementDetailFragment(
                store = store
            )
        findNavController().navigate(action)
    }

    private fun initBinding() {
        with(binding) {
            adapter = ManagementRcvAdapter(this@ManagementFragment::navigateToManagementDetail)
            viewModel = managementViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun initRecyclerView() {
        with(binding.rcvManagement) {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}