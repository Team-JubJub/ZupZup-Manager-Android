package com.example.zupzup_manager.ui.reservationdetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.zupzup_manager.R
import com.example.zupzup_manager.databinding.FragmentReservationConfirmBottomsheetBinding
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.common.toDecimalFormat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReservationConfirmBottomSheet(
    private val reservation: ReservationModel
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentReservationConfirmBottomsheetBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
    }

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
        addDynamicCartList()
    }

    private fun addDynamicCartList() {
        reservation.cartList.forEach {
            val layout = requireActivity().layoutInflater.inflate(
                R.layout.item_reservation_confirm_bottom_sheet_cart,
                null
            )
            val tvName: TextView = layout.findViewById(R.id.tv_item_name)
            val tvItemPrice: TextView = layout.findViewById(R.id.tv_item_price)
            tvName.text = "${it.name} ${it.amount}개"
            tvItemPrice.text = (it.salesPrice * it.amount).toDecimalFormat()
            binding.linearLayoutCartListContainer.addView(layout)
        }
    }

    private fun initBinding() {
        binding.reservation = reservation
    }
}