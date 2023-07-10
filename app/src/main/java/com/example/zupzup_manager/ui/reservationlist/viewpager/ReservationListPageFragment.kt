package com.example.zupzup_manager.ui.reservationlist.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zupzup_manager.databinding.FragmentReservationListBinding
import com.example.zupzup_manager.databinding.FragmentReservationListPageBinding
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.common.User
import com.example.zupzup_manager.ui.reservationlist.ReservationListViewModel
import com.example.zupzup_manager.ui.reservationlist.recyclerview.ReservationListItemDecorator
import com.example.zupzup_manager.ui.reservationlist.recyclerview.ReservationListRcvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationListPageFragment : Fragment() {

    private val reservationListViewModel: ReservationListViewModel by viewModels()
    private lateinit var binding: FragmentReservationListPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationListPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateList()
        initBinding()
        initRecyclerView()
    }

    private fun navigateToReservationDetail(reservation: ReservationModel) {
        val action =
            ReservationListPageFragmentDirections.actionReservationListPageFragmentToFragReservationDetail(
                reservation
            )
        findNavController().navigate(action)
    }

    private fun updateList() {
        val index = arguments?.getInt(KEY_INDEX)
        if (index != null) {
            reservationListViewModel.getReservationList(User.getStoreId(), index)
        }
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = reservationListViewModel
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            rcvReservationList.layoutManager = LinearLayoutManager(context)
            rcvReservationList.addItemDecoration(ReservationListItemDecorator())
            adapter =
                ReservationListRcvAdapter(this@ReservationListPageFragment::navigateToReservationDetail)
        }
    }

    companion object {
        private const val KEY_INDEX = "index"

        @JvmStatic
        fun newInstance(index: Int): ReservationListPageFragment {
            val f = ReservationListPageFragment()
            val args = Bundle()
            args.putInt(KEY_INDEX, index)
            f.arguments = args

            return f
        }
    }
}