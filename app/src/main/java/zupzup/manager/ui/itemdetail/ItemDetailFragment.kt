package zupzup.manager.ui.itemdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import zupzup.manager.databinding.FragmentItemDetailBinding
import zupzup.manager.ui.item.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import zupzup.manager.domain.models.item.ItemAddModel
import zupzup.manager.ui.item.recyclerview.ItemRcvAdapter
import java.io.File

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private lateinit var binding: FragmentItemDetailBinding
    private val itemViewModel: ItemViewModel by viewModels()
    private val itemDetailViewModel: ItemDetailViewModel by viewModels()

    private val itemDetailClickListener = object : ItemDetailClickListener {
        // 여기 뷰모델 수정해야 할 것 같음 -> 얘는 ItemDetailFragmentArgs.fromBundle(requireArguments()).item 이거 써야 하지 않나?
        override fun onPlusItemModifiedAmountBtnClick(itemId: Long) {
            itemViewModel.plusModifiedAmount(itemId)
        }

        override fun onMinusItemModifiedAmountBtnClick(itemId: Long) {
            itemViewModel.minusModifiedAmount(itemId)
        }

        override fun navigateToBackStack() {
            findNavController().popBackStack()
        }

        override fun addItem(item: ItemAddModel, image: File?) {
//            lifecycleScope.launch {
//                async {itemDetailViewModel.addItem(item, image)}.await()
//                findNavController().popBackStack()
//            }
            itemDetailViewModel.addItem(item, image)
        }

        override fun modifyItem() {
            //itemViewModel.modifyItemList(itemViewModel.managementDetailBody.value)
        }

        override fun deleteItem(itemId: Long) {
                itemDetailViewModel.deleteItem(itemId)
                itemViewModel.deleteItemById(itemId)
//                findNavController().popBackStack()
//            }
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