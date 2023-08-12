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
import zupzup.manager.domain.models.item.ItemModel
import zupzup.manager.ui.itemdetail.ItemDetailClickListener

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