package com.example.zupzup_manager.ui.reservationlist.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zupzup_manager.ui.reservationlist.ReservationListFragment
import com.example.zupzup_manager.ui.setting.SettingFragment

class TabPagerAdapter(reservationListFragment: ReservationListFragment): FragmentStateAdapter(reservationListFragment) {
    // 0: "NEW", 1: "CONFIRM", 2: "COMPLETE / "
    var pageList = listOf(0, 1, 2)

    override fun getItemCount(): Int {
        return 3;
    }

    override fun createFragment(position: Int): Fragment {
        return ReservationListPageFragment.newInstance(pageList[position])
    }
}