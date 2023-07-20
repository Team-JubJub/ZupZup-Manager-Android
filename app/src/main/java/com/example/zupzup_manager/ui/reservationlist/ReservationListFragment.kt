package com.example.zupzup_manager.ui.reservationlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.zupzup_manager.databinding.FragmentReservationListBinding
import com.example.zupzup_manager.ui.common.User
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationListFragment : Fragment() {

    private lateinit var binding: FragmentReservationListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        binding.vpReservationState.adapter = ReservationListFilterVpAdapter(this@ReservationListFragment)
    }

    private fun initTabLayout() {
        val tabTitles = listOf("신규", "확정", "완료 및 취소")
        TabLayoutMediator(binding.tabLayout, binding.vpReservationState) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}