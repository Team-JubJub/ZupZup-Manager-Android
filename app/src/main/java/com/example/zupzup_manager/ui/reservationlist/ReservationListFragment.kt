package com.example.zupzup_manager.ui.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.zupzup_manager.databinding.FragmentReservationListBinding
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.ui.reservationlist.recyclerview.ReservationListItemDecorator
import com.example.zupzup_manager.ui.reservationlist.recyclerview.ReservationListRcvAdapter
import com.example.zupzup_manager.ui.reservationlist.viewpager.TabPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationListFragment : Fragment() {

    private lateinit var binding: FragmentReservationListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationListBinding.inflate(layoutInflater, container, false)

        initViewPager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner

            val tabTitles = listOf("신규", "확정", "완료 및 취소")
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    private fun initViewPager() {
        with(binding.viewPager) {
            adapter = TabPagerAdapter(this@ReservationListFragment)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val view = (this@with[0] as RecyclerView).layoutManager?.findViewByPosition(position)
                    view?.post {
                        val wMeasureSpec =
                            View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                        val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                        view.measure(wMeasureSpec, hMeasureSpec)

                        if (this@with.layoutParams.height != view.measuredHeight) {
                            this@with.layoutParams = (this@with.layoutParams).also { lp ->
                                lp.height = view.measuredHeight
                            }
                        }
                    }
                }
            })
        }


    }
}