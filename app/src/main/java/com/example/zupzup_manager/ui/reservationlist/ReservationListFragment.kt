package com.example.zupzup_manager.ui.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zupzup_manager.databinding.FragmentReservationListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationListFragment : Fragment() {


    private val reservationListViewModel: ReservationListViewModel by viewModels()
    private lateinit var binding: FragmentReservationListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReservationListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding) {
            rcvReservationList.layoutManager = LinearLayoutManager(context)
            rcvReservationList.addItemDecoration(ReservationListItemDecorator())
            adapter = ReservationListRcvAdapter()
        }
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = reservationListViewModel
        }
    }
}