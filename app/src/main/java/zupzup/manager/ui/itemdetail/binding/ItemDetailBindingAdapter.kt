package zupzup.manager.ui.itemdetail.binding

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import zupzup.manager.R
import zupzup.manager.domain.models.item.ItemAddModel
import zupzup.manager.domain.models.item.ItemModel
import zupzup.manager.ui.itemdetail.ItemDetailClickListener
import java.io.File
import java.io.FileOutputStream


// 갤러리에서 이미지 선택 후
//val selectedImageUri: Uri = // 이미지 Uri 가져오기
//val imageFile: File = File(selectedImageUri.path)
//imageView.setImageURI(selectedImageUri)
@BindingAdapter("clickListener", "itemList", "image", "item")
fun bindItemAddOrModifyButton(
    button: TextView,
    clickListener: ItemDetailClickListener,
    itemList: List<*>,
    image: ImageView,
    item: ItemModel?
){
//    val imageURL: String,
    if (item == null) {
        button.setOnClickListener{
            val itemName = itemList[0] as EditText
            val itemPrice = itemList[1] as EditText
            val salePrice = itemList[2] as EditText
            val itemCount = itemList[3] as EditText

            Log.d("BINDING TEST", itemList.toString())
            Log.d("BINDING TEST", image.toString())
            val regex = Regex("[^0-9]")
            val itemAddModel = ItemAddModel(
                itemName = itemName.text.toString(),
                itemPrice = regex.replace(itemPrice.text.toString(), "").toInt(),
                salePrice = regex.replace(salePrice.text.toString(), "").toInt(),
                itemCount = itemCount.text.toString().toInt()
            )
            val imageFile = saveImageViewImageToFile(image)
            Log.d("BINDING TEST", imageFile.toString())
            clickListener.addItem(itemAddModel, imageFile)
        }
    }
}

fun saveImageViewImageToFile(imageView: ImageView): File? {
    val drawable = imageView.drawable
    if (drawable is BitmapDrawable) {
        val bitmap = drawable.bitmap
        val file = File(imageView.context.cacheDir, "temp_image.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        return file
    }
    return null
}

@BindingAdapter("detailImgUrl")
fun bindDetailImageUrlToImageView(imageView: ImageView, imgUrl: String?) {
    if(imgUrl != null) {
        Glide
            .with(imageView.context)
            .load(imgUrl)
            .transform(
                CenterCrop()
            )
            // disk 캐싱 추가
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}

@BindingAdapter("amount2", "item")
fun bindTwoAmountSame(
    amount: EditText,
    amount2: TextView,
    item: ItemModel?
){
    if (item != null) {
        amount.setText(item.itemCount.toString())
    } else {
        amount.setText("0")
    }
    amount.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            amount2.text = s.toString()
        }
        override fun afterTextChanged(s: Editable?) {
        }

    })
}

@BindingAdapter("clickListener", "itemId", "tvConfirmedAmount", "tvConfirmedAmount2")
fun bindBindingHelperToAmountButtonInDetail(
    ivBtnModifyAmount: ImageView,
    clickListener: ItemDetailClickListener,
    itemId: Long,
    tvConfirmedAmount: TextView,
    tvConfirmedAmount2: TextView
) {
    ivBtnModifyAmount.setOnClickListener {
        when (ivBtnModifyAmount.id) {
            R.id.btn_plus -> {
                if (tvConfirmedAmount.text.toString().toInt() < 100) {
                    clickListener.onPlusItemModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().plus(1).toString()
                    tvConfirmedAmount2.text = tvConfirmedAmount2.text.toString().toInt().plus(1).toString()
                }
            }

            R.id.btn_minus -> {
                if (tvConfirmedAmount.text.toString().toInt() > 0) {
                    clickListener.onMinusItemModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().minus(1).toString()
                    tvConfirmedAmount2.text = tvConfirmedAmount2.text.toString().toInt().minus(1).toString()
                }
            }
        }
    }
}