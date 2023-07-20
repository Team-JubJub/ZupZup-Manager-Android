package com.example.zupzup_manager.ui.reservationlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ReservationListFilterVpAdapter(fm: Fragment) : FragmentStateAdapter(fm) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = ReservationListFilterFragment()
        fragment.arguments = Bundle().apply {
            putInt("position", position)
        }
        return fragment
    }
}

