package zupzup.manager.ui.itemdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import zupzup.manager.databinding.FragmentItemDetailBinding
import zupzup.manager.ui.item.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private lateinit var binding: FragmentItemDetailBinding
    private val managementViewModel: ItemViewModel by viewModels()

    private val itemDetailClickListener = object : ItemDetailClickListener {
        override fun onPlusItemModifiedAmountBtnClick(itemId: Long) {
            managementViewModel.plusModifiedAmount(itemId)
        }

        override fun onMinusItemModifiedAmountBtnClick(itemId: Long) {
            managementViewModel.minusModifiedAmount(itemId)
        }

        override fun navigateToBackStack() {
            findNavController().popBackStack()
        }

        override fun modifyItem() {
            // TODO : 파베에서는 문서 업데이트만 가능하며, 배열 내부에 접근해서 업데이트할 수는 없음
            //managementViewModel.modifyItemList(managementViewModel.managementDetailBody.value)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            clickListener = itemDetailClickListener
            item = ItemDetailFragmentArgs.fromBundle(requireArguments()).item
        }
    }
}