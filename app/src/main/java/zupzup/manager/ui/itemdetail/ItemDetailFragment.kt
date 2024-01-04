package zupzup.manager.ui.itemdetail

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import zupzup.manager.ui.utils.toPriceString
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
            if (result.resultCode == Activity.RESULT_OK) {
                val dataUri = result.data?.data
                if (dataUri != null) {
                    // 선택된 이미지가 있는 경우 처리
                    itemDetailViewModel.loadImageWithCenterCrop(dataUri, binding.ivItem)
                    // 갤러리에서 선택한 이미지 uri 전역 변수에 저장함
                    mediaPath = dataUri
                } else {
                    // 갤러리에서 이미지를 선택하지 않은 경우
                }
            } else {
                // 갤러리 액티비티가 성공적으로 닫히지 않은 경우
            }
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
                AlertDialog.Builder(requireContext())
                    .setTitle("제품 등록하기")
                    .setMessage("제품 등록을 완료합니다.")
                    .setPositiveButton("확인") { _, _ ->
                        Log.d("제품 등록", "확인")
                        lifecycleScope.launch {
                            if (validateItemInfo()) {
                                uriToFile()
                                val addedURL = itemDetailViewModel.addItem(item, file)
                                itemViewModel.addItemWithUpdatedId(item, addedURL)
                                // 추가한 뒤 내부 저장소에서는 삭제
                                file.delete()
                                findNavController().popBackStack()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "상품 정보를 올바르게 입력해주새요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    .setNegativeButton("취소") { _, _ ->
                        Log.d("제품 등록", "취소")
                    }
                    .create()
                    .show()
            }
        }

        override fun modifyItem(updatedItem: ItemModifyModel, itemId: Long) {
            lifecycleScope.launch {
                AlertDialog.Builder(requireContext())
                    .setTitle("제품 정보 수정하기")
                    .setMessage("제품 수정을 완료합니다.")
                    .setPositiveButton("확인") { _, _ ->
                        Log.d("제품 수정", "확인")
                        lifecycleScope.launch {
                            if (validateItemInfo()) {
                                if (imageChanged) {
                                    uriToFile()
                                    val modifiedURL =
                                        itemDetailViewModel.modifyItem(itemId, updatedItem, file)
                                    file.delete()
                                    itemViewModel.modifyItemById(updatedItem, itemId, modifiedURL)
                                } else {
                                    // 이미지가 변경되지 않은 경우 -> null로 보내면 기존 이미지가 초기화됨...ㅠㅠ
                                    itemDetailViewModel.modifyItem(itemId, updatedItem, null)
                                    itemViewModel.modifyItemById(updatedItem, itemId, null)
                                }
                                findNavController().popBackStack()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "상품 정보를 올바르게 입력해주새요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    .setNegativeButton("취소") { _, _ ->
                        Log.d("제품 수정", "취소")
                    }
                    .create()
                    .show()
            }
        }

        override fun deleteItem(itemId: Long) {
            lifecycleScope.launch {
                AlertDialog.Builder(requireContext())
                    .setTitle("제품 삭제하기")
                    .setMessage("제품을 리스트에서 삭제합니다.")
                    .setPositiveButton("확인") { _, _ ->
                        Log.d("제품 삭제", "확인")
                        lifecycleScope.launch {
                            itemDetailViewModel.deleteItem(itemId)
                            itemViewModel.deleteItemById(itemId)
                            findNavController().popBackStack()
                        }
                    }
                    .setNegativeButton("취소") { _, _ ->
                        Log.d("제품 삭제", "취소")
                        findNavController().popBackStack()
                    }
                    .create()
                    .show()
            }
        }
    }

    private fun validateItemInfo(): Boolean {
        if (ItemDetailFragmentArgs.fromBundle(requireArguments()).item == null) {
            return ::mediaPath.isInitialized && binding.etItemName.text.isNotEmpty() &&
                    (binding.etSalePrice.text.toString().replace("₩", "").replace(",", "")
                        .replace(" ", "").toInt()
                            <= binding.etItemPrice.text.toString().replace("₩", "").replace(",", "")
                        .replace(" ", "").toInt())
                    && binding.etSalePrice.text.toString().replace("₩", "").replace(",", "")
                .replace(" ", "").toInt() > 0
                    && binding.etItemPrice.text.toString().replace("₩", "").replace(",", "")
                .replace(" ", "").toInt() > 0
        } else {
            return binding.etItemName.text.isNotEmpty() &&
                    (binding.etSalePrice.text.toString().replace("₩", "").replace(",", "")
                        .replace(" ", "").toInt()
                            <= binding.etItemPrice.text.toString().replace("₩", "").replace(",", "")
                        .replace(" ", "").toInt())
                    && binding.etSalePrice.text.toString().replace("₩", "").replace(",", "")
                .replace(" ", "").toInt() > 0
                    && binding.etItemPrice.text.toString().replace("₩", "").replace(",", "")
                .replace(" ", "").toInt() > 0
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
        initEditTextChangeListener()
    }

    private var userInput = true
    private fun initEditTextChangeListener() {
        with(binding) {
            etItemPrice.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s != null && userInput) {
                        userInput = false
                        val str = s.toString()
                        val priceStr = str.replace("₩", "").replace(",", "").replace(" ", "")
                        val price = if (priceStr == "") 0 else priceStr.toInt()
                        if (price > 0) {
                            etItemPrice.setText("₩ ${price.toPriceString()}")
                        } else {
                            etItemPrice.setText("₩ ")
                        }
                        Log.d("TAG", "afterTextChanged: ${s.length}")
                        etItemPrice.setSelection(etItemPrice.text.length)
                        userInput = true
                    }
                }
            })

            etSalePrice.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s != null && userInput) {
                        userInput = false
                        val str = s.toString()
                        val priceStr = str.replace("₩", "").replace(",", "").replace(" ", "")
                        val price = if (priceStr == "") 0 else priceStr.toInt()
                        if (price > 0) {
                            etSalePrice.setText("₩ ${price.toPriceString()}")
                        } else {
                            etSalePrice.setText("₩ ")
                        }
                        etSalePrice.setSelection(etSalePrice.text.length)
                        userInput = true
                    }
                }
            })
        }
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            clickListener = itemDetailClickListener
            item = ItemDetailFragmentArgs.fromBundle(requireArguments()).item
            Log.d("TAG", "initBinding: $item ")
        }
    }
}