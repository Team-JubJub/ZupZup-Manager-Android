package com.example.zupzup_manager.ui.merchandisedetail

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
import com.example.zupzup_manager.databinding.FragmentManagementDetailBinding
import com.example.zupzup_manager.databinding.FragmentMerchandiseDetailBinding
import com.example.zupzup_manager.databinding.FragmentReservationDetailBinding
import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.common.UiEventState
import com.example.zupzup_manager.ui.reservationdetail.binding.ReservationDetailBindingHelper
import com.example.zupzup_manager.ui.reservationdetail.bottomsheet.ReservationConfirmBottomSheetFragment
import com.example.zupzup_manager.ui.reservationdetail.progress.ProgressDialogFragment
import com.example.zupzup_manager.ui.reservationdetail.recyclerview.ReservationDetailItemDecorator
import com.example.zupzup_manager.ui.reservationdetail.recyclerview.ReservationDetailRcvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MerchandiseDetailFragment : Fragment() {

    private lateinit var binding: FragmentMerchandiseDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMerchandiseDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initRecyclerView()
    }

    private fun initRecyclerView() {

    }

    private fun initBinding() {

    }
}