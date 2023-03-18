package com.example.zupzup_manager.ui.reservationdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zupzup_manager.databinding.FragmentReservationDetailBinding
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.reservationdetail.models.ReservationDetailHeaderModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationDetailFragment : Fragment() {

    private lateinit var binding: FragmentReservationDetailBinding
    private val reservationDetailViewModel: ReservationDetailViewModel by viewModels()
    private val args: ReservationDetailFragmentArgs by navArgs()

    private val reservationDetailBindingHelper = ReservationDetailBindingHelper(
        ::onReservationConfirmButtonClickListener,
        { itemId: Long -> reservationDetailViewModel.plusConfirmedAmount(itemId) },
        { itemId: Long -> reservationDetailViewModel.minusConfirmedAmount(itemId) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReservationDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        setArgsToViewModel()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding.rcvReservation) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ReservationDetailItemDecorator())
        }
    }

    private fun setArgsToViewModel() {
        reservationDetailViewModel.setArgsToViewModel(args.reservation)
    }

    private fun onReservationConfirmButtonClickListener(reservation : ReservationModel) {
        ReservationConfirmBottomSheet(reservation).show(parentFragmentManager, null)
    }

    private fun initBinding() {
        with(binding) {
            adapter = ReservationDetailRcvAdapter(
                reservationDetailBindingHelper
            )
            viewModel = reservationDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            bindingHelper = reservationDetailBindingHelper
        }
    }

}