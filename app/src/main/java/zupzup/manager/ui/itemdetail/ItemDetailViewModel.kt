package zupzup.manager.ui.itemdetail

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import zupzup.manager.domain.models.item.ItemAddModel
import zupzup.manager.domain.models.item.ItemModifyModel
import zupzup.manager.domain.usecase.item.AddItemUseCase
import zupzup.manager.domain.usecase.item.DeleteItemUseCase
import zupzup.manager.domain.usecase.item.ModifyItemUseCase
import zupzup.manager.ui.common.User
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase,
    private val modifyItemUseCase: ModifyItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModel() {

    // 갤러리에서 이미지 등록
    fun loadImageWithCenterCrop(dataUri: Uri?, imageView: ImageView) {
        if (dataUri != null) {
            imageView.setImageResource(0)
            Glide
                .with(imageView)
                .load(dataUri)
                .transform(CenterCrop())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }

    // 제품 등록
    suspend fun addItem(item: ItemAddModel, image: File?) {
        viewModelScope.launch {
            val photo: MultipartBody.Part? = if (image != null) {
                val fileBody = image.asRequestBody("image/*".toMediaTypeOrNull())
                // name도 서버와 동일하게 맞출 것!!
                MultipartBody.Part.createFormData("image", image.name, fileBody)
            } else null

            addItemUseCase(User.getStoreId(), item, photo)
        }.join()
    }

    // 제품 수정
    fun modifyItem(item: ItemModifyModel, image: File?) {
        val photo: MultipartBody.Part? = if (image != null) {
            val fileBody = image.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", image.name, fileBody)
        } else null

        viewModelScope.launch {
            modifyItemUseCase(User.getStoreId(), item, photo)
        }
    }

    // 제품 삭제
    suspend fun deleteItem(itemId: Long) {
        viewModelScope.launch {
            deleteItemUseCase(User.getStoreId(), itemId)
        }.join()
    }
}