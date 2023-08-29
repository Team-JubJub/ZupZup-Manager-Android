package zupzup.manager.ui.itemdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zupzup.manager.databinding.FragmentItemDetailBinding
import zupzup.manager.domain.models.item.ItemAddModel
import zupzup.manager.domain.models.item.ItemModifyModel
import zupzup.manager.ui.item.ItemViewModel
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private lateinit var binding: FragmentItemDetailBinding

    // 두 fragment에서 사용하는 viewmodel은 activityViewModels로 해야 함
    // 아니면 MutableStateFlow 값이 안 읽히는 문제 생김
    private val itemViewModel: ItemViewModel by activityViewModels()
    private val itemDetailViewModel: ItemDetailViewModel by viewModels()
    private var imageChanged = false
    private lateinit var mediaPath: Uri
    private lateinit var file: File
    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val dataUri = result.data?.data
            itemDetailViewModel.loadImageWithCenterCrop(dataUri, binding.ivItem)
            // 갤러리에서 선택한 이미지 uri 전역 변수에 저장함
            // TODO 여기서 선택 안 하고 뒤로 가기 누르면 NullPointerException 발생
            mediaPath = dataUri!!
        }

    private val itemDetailClickListener = object : ItemDetailClickListener {
        override fun onPlusItemModifiedAmountBtnClick(itemId: Long) {
            itemViewModel.plusModifiedAmount(itemId)
        }

        override fun onMinusItemModifiedAmountBtnClick(itemId: Long) {
            itemViewModel.minusModifiedAmount(itemId)
        }

        override fun navigateToBackStack() {
            findNavController().popBackStack()
        }

        override fun selectImage() {
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"
            getContent.launch(galleryIntent)
            imageChanged = true
        }

        override fun addItem(item: ItemAddModel) {
            lifecycleScope.launch {
                uriToFile()
                val addedURL = itemDetailViewModel.addItem(item, file)
                itemViewModel.addItemWithUpdatedId(item, addedURL)
                // 추가한 뒤 내부 저장소에서는 삭제
                file.delete()
                findNavController().popBackStack()
            }
        }

        override fun modifyItem(updatedItem: ItemModifyModel, itemId: Long) {
            lifecycleScope.launch {
                if (imageChanged) {
                    uriToFile()
                    val modifiedURL = itemDetailViewModel.modifyItem(itemId, updatedItem, file)
                    file.delete()
                    itemViewModel.modifyItemById(updatedItem, itemId, modifiedURL)
                } else {
                    // 이미지가 변경되지 않은 경우 -> null로 보내면 기존 이미지가 초기화됨...ㅠㅠ
                    itemDetailViewModel.modifyItem(itemId, updatedItem, null)
                    itemViewModel.modifyItemById(updatedItem, itemId, null)
                }
                findNavController().popBackStack()
            }
        }

        override fun deleteItem(itemId: Long) {
            lifecycleScope.launch {
                itemDetailViewModel.deleteItem(itemId)
                itemViewModel.deleteItemById(itemId)
                findNavController().popBackStack()
            }
        }
    }

    private fun uriToFile() {
        val inputStream = requireContext().contentResolver.openInputStream(mediaPath)
        // device file explorer에 /data/user/0/zupzup.manager/cache 에 저장됨 (내부 저장소)
        file = File(requireContext().cacheDir, "temp_image.jpg")
        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
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