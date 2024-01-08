package zupzup.manager.ui.store

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import zupzup.manager.databinding.FragmentStoreBinding
import zupzup.manager.domain.models.store.ModifyStoreModel
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class StoreFragment : Fragment() {
    companion object {
        const val REQ_GALLERY = 1
    }

    private var mediaPath: Uri? = null
    private var file: File? = null

    private val imageIntentResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val dataUri = result.data?.data
                if (dataUri != null) {
                    binding.ivAddStore.setImageResource(0)
                    Glide
                        .with(binding.ivAddStore)
                        .load(dataUri)
                        .transform(CenterCrop())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivAddStore)
                    // 갤러리에서 선택한 이미지 uri 전역 변수에 저장함
                    mediaPath = dataUri
                } else {
                    // 갤러리에서 이미지를 선택하지 않은 경우
                }
            } else {
                // 갤러리 액티비티가 성공적으로 닫히지 않은 경우
            }
        }

    private lateinit var binding: FragmentStoreBinding
    private val storeViewModel: StoreViewModel by viewModels()

    private val storeClickListener = object : StoreClickListener {
        override fun navigateToBackStack() {
            findNavController().popBackStack()
        }

        override fun modifyStoreDetail(
            modifyStoreModel: ModifyStoreModel,
            image: MultipartBody.Part?
        ) {
            AlertDialog.Builder(requireContext())
                .setTitle("가게 정보 수정하기")
                .setMessage("가게 정보를 수정합니다.")
                .setPositiveButton("확인") { _, _ ->
                    Log.d("가게 정보 수정", "확인")
                    lifecycleScope.launch{
                        uriToFile()
                        storeViewModel.modifyStoreDetail(modifyStoreModel, file)

                        if (file != null) {
                            file!!.delete()
                        }
                        findNavController().popBackStack()
                    }
                }
                .setNegativeButton("취소") { _, _ ->
                    Log.d("가게 정보 수정", "취소")
                }
                .create()
                .show()
        }

        override fun selectImage() {
            selectGallery()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            clickListener = storeClickListener
            store = StoreFragmentArgs.fromBundle(requireArguments()).store
        }
    }

    private fun selectGallery() {
        val writePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val readPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQ_GALLERY
            )
        } else {
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"
            imageIntentResult.launch(galleryIntent)
        }
    }

    private fun uriToFile() {
        if (mediaPath != null) {
            val inputStream = requireContext().contentResolver.openInputStream(mediaPath!!)
            // device file explorer에 /data/user/0/zupzup.manager/cache 에 저장됨 (내부 저장소)
            file = File(requireContext().cacheDir, "temp_image.jpg")
            inputStream?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    interface StoreClickListener {
        fun navigateToBackStack()
        fun modifyStoreDetail(modifyStoreModel: ModifyStoreModel, image: MultipartBody.Part?)
        fun selectImage()
    }
}