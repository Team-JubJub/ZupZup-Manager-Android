package zupzup.manager.ui.orderlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrderListFilterVpAdapter(fm: Fragment) : FragmentStateAdapter(fm) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = OrderListFilterFragment()
        fragment.arguments = Bundle().apply {
            putInt("position", position)
        }
        return fragment
    }
}

