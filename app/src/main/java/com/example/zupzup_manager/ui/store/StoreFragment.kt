package com.example.zupzup_manager.ui.store

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zupzup_manager.databinding.FragmentStoreBinding
import com.example.zupzup_manager.domain.models.ModifyStoreModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody

@AndroidEntryPoint
class StoreFragment : Fragment() {
    companion object{
        const val REQ_GALLERY = 1
    }

    private lateinit var binding: FragmentStoreBinding
    private val storeViewModel: StoreViewModel by viewModels()

    private val storeClickListener = object : StoreClickListener {
        override fun navigateToBackStack() {
            findNavController().popBackStack()
        }

        override fun modifyStoreDetail(modifyStoreModel: ModifyStoreModel, image: MultipartBody.Part?) {
            storeViewModel.modifyStoreDetail(modifyStoreModel, image)
            findNavController().popBackStack()
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

    private fun selectGallery(){
        val writePermission = ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQ_GALLERY
            )
        } else{
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivity(intent)
        }
    }

    interface StoreClickListener {
        fun navigateToBackStack()
        fun modifyStoreDetail(modifyStoreModel: ModifyStoreModel, image: MultipartBody.Part?)
        fun selectImage()
    }
}