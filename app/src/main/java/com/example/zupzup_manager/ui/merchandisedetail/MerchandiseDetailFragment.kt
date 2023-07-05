package com.example.zupzup_manager.ui.merchandisedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zupzup_manager.databinding.FragmentMerchandiseDetailBinding
import dagger.hilt.android.AndroidEntryPoint

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
        isAddOrModify()
    }

    private fun isAddOrModify() {
        val merchandiseArgs = MerchandiseDetailFragmentArgs.fromBundle(requireArguments()).merchandise

        if(merchandiseArgs != null) {
            with(binding){
                merchandise = merchandiseArgs
            }
        }
    }

    private fun initRecyclerView() {

    }

    private fun initBinding() {

    }
}