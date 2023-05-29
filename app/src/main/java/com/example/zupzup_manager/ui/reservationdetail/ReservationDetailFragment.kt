package com.example.zupzup_manager.ui.reservationdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zupzup_manager.databinding.FragmentReservationDetailBinding
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.common.UiEventState
import com.example.zupzup_manager.ui.common.progress.ProgressDialogFragment
import com.example.zupzup_manager.ui.reservationdetail.binding.ReservationDetailBindingHelper
import com.example.zupzup_manager.ui.reservationdetail.bottomsheet.ReservationConfirmBottomSheetFragment
import com.example.zupzup_manager.ui.reservationdetail.recyclerview.ReservationDetailItemDecorator
import com.example.zupzup_manager.ui.reservationdetail.recyclerview.ReservationDetailRcvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReservationDetailFragment : Fragment() {

    private val args: ReservationDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentReservationDetailBinding
    private val reservationDetailViewModel: ReservationDetailViewModel by viewModels()

    private var reservationConfirmBottomSheet: ReservationConfirmBottomSheetFragment? = null
    private val progressDialog = ProgressDialogFragment()

    private val reservationDetailBindingHelper = ReservationDetailBindingHelper(
        ::onReservationConfirmButtonClickListener,
        { itemId: Long -> reservationDetailViewModel.plusConfirmedAmount(itemId) },
        { itemId: Long -> reservationDetailViewModel.minusConfirmedAmount(itemId) }
    )

    private val reservationHandler = object : HandleReservationBtnClickListener {
        override fun confirmReservation(reservationModel: ReservationModel, isPartial: Boolean) {
            reservationDetailViewModel.confirmReservation(reservationModel, isPartial)
        }

        override fun rejectReservation(reservationModel: ReservationModel) {
            reservationDetailViewModel.rejectReservation(reservationModel)
        }

        override fun cancelReservation(reserveId: Long) {
            reservationDetailViewModel.cancelReservation(reserveId)
        }

        override fun completeReservation(reserveId: Long) {
            reservationDetailViewModel.completeReservation(reserveId)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReservationDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArgsToViewModel()
        initBinding()
        initRecyclerView()
        collectReservationProcessEventState()
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

    private fun onReservationConfirmButtonClickListener(
        reservation: ReservationModel,
        isPartial: Boolean
    ) {
        reservationConfirmBottomSheet =
            ReservationConfirmBottomSheetFragment(reservation, isPartial, reservationHandler)
        reservationConfirmBottomSheet!!.show(parentFragmentManager, null)
    }

    private fun initBinding() {
        with(binding) {
            adapter = ReservationDetailRcvAdapter(
                reservationDetailBindingHelper
            )
            handler = reservationHandler
            viewModel = reservationDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            bindingHelper = reservationDetailBindingHelper
        }
    }


    private fun collectReservationProcessEventState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reservationDetailViewModel.reservationProcessingUiState.collect { eventState ->
                    when (eventState) {
                        is UiEventState.Processing -> {
                            progressDialog.show(parentFragmentManager, null)
                        }
                        is UiEventState.Complete -> {
                            if (reservationConfirmBottomSheet != null) {
                                reservationConfirmBottomSheet!!.dismiss()
                            }
                            progressDialog.dismiss()
                            findNavController().popBackStack()
                        }
                        is UiEventState.Fail -> {
                            if (reservationConfirmBottomSheet != null) {
                                reservationConfirmBottomSheet!!.dismiss()
                            }
                            progressDialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                eventState.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

        }
    }
}