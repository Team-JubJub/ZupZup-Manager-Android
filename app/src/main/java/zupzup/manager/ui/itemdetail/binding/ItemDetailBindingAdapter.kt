package zupzup.manager.ui.itemdetail.binding

import android.text.Editable
import android.text.TextWatcher
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
import zupzup.manager.domain.models.item.ItemModifyModel
import zupzup.manager.ui.itemdetail.ItemDetailClickListener

@BindingAdapter("clickListener", "itemList", "item")
fun bindItemAddOrModifyButton(
    button: TextView,
    clickListener: ItemDetailClickListener,
    itemList: List<*>,
    item: ItemModel?
) {
    button.setOnClickListener {
        val itemNameEditText = itemList[0] as EditText
        val itemPriceEditText = itemList[1] as EditText
        val salePriceEditText = itemList[2] as EditText
        val itemCountEditText = itemList[3] as EditText

        val regex = Regex("[^0-9]")
        val itemName = itemNameEditText.text.toString()
        val itemPriceStr = regex.replace(itemPriceEditText.text.toString(), "")
        val itemPrice = if(itemPriceStr == "") 0 else itemPriceStr.toInt()
        val salePriceStr = regex.replace(salePriceEditText.text.toString(), "")
        val salePrice = if(salePriceStr == "") 0 else salePriceStr.toInt()
        val itemCount = itemCountEditText.text.toString().toInt()

        val itemModel: Any = if (item == null) {
            ItemAddModel(itemName, itemPrice, salePrice, itemCount)
        } else {
            ItemModifyModel(itemName, item.imageURL, itemPrice, salePrice, itemCount)
        }

        if (item == null) {
            clickListener.addItem(itemModel as ItemAddModel)
        } else {
            clickListener.modifyItem(itemModel as ItemModifyModel, item.itemId)
        }
    }
}

@BindingAdapter("detailImgUrl")
fun bindDetailImageUrlToImageView(imageView: ImageView, imgUrl: String?) {
    if (imgUrl != null) {
        Glide
            .with(imageView.context)
            .load(imgUrl)
            .transform(
                CenterCrop()
            )
            // disk 캐싱 추가
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    } else {
        imageView.setBackgroundResource(R.drawable.img_edit)
    }
}

@BindingAdapter("amount2", "item")
fun bindTwoAmountSame(
    amount: EditText,
    amount2: TextView,
    item: ItemModel?
) {
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
                    tvConfirmedAmount2.text =
                        tvConfirmedAmount2.text.toString().toInt().plus(1).toString()
                }
            }

            R.id.btn_minus -> {
                if (tvConfirmedAmount.text.toString().toInt() > 0) {
                    clickListener.onMinusItemModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().minus(1).toString()
                    tvConfirmedAmount2.text =
                        tvConfirmedAmount2.text.toString().toInt().minus(1).toString()
                }
            }
        }
    }
}