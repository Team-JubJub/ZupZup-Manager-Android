package com.example.zupzup_manager.ui.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zupzup_manager.databinding.FragmentReservationListFilterBinding
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.reservationlist.recyclerview.ReservationListItemDecorator
import com.example.zupzup_manager.ui.reservationlist.recyclerview.ReservationListRcvAdapter

class ReservationListFilterFragment : Fragment() {

    private lateinit var binding: FragmentReservationListFilterBinding
    private var filterOption: String? = null
    private val reservationListViewModel: ReservationListViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationListFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFilterOption()
        initBinding()
        initRecyclerView()
    }

    private fun getFilterOption() {
        arguments?.takeIf { it.containsKey("position") }?.apply {
            when (getInt("position")) {
                0 -> filterOption = "NEW"
                1 -> filterOption = "CONFIRM"
                2 -> filterOption = "COMPLETE"
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.rcvReservationList) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ReservationListItemDecorator())
        }
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = reservationListViewModel
            adapter = ReservationListRcvAdapter(::navigateToReservationDetail)
            filter = filterOption
        }
    }

    private fun navigateToReservationDetail(reservation: ReservationModel) {
        val action =
            ReservationListFragmentDirections.actionFragReservationListToFragReservationDetail(
                reservation
            )
        findNavController().navigate(action)
    }
}