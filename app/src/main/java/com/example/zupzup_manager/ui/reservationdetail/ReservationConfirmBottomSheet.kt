package com.example.zupzup_manager.ui.reservationdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zupzup_manager.databinding.FragmentReservationConfirmBottomsheetBinding
import com.example.zupzup_manager.domain.models.ReservationModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReservationConfirmBottomSheet(
    private val reservation: ReservationModel
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentReservationConfirmBottomsheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentReservationConfirmBottomsheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {

    }
}