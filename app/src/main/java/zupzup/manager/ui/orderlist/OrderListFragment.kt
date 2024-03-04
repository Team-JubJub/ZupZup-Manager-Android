package zupzup.manager.ui.orderlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import zupzup.manager.databinding.FragmentOrderListBinding

@AndroidEntryPoint
class OrderListFragment : Fragment() {

    private lateinit var binding: FragmentOrderListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        binding.vpOrderState.adapter = OrderListFilterVpAdapter(this@OrderListFragment)
    }

    private fun initTabLayout() {
        val tabTitles = listOf("신규", "확정", "완료 및 취소")
        TabLayoutMediator(binding.tabLayout, binding.vpOrderState) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}