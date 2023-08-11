package com.example.zupzup_manager.ui.item.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.zupzup_manager.R
import com.example.zupzup_manager.domain.models.item.ItemModel
import com.example.zupzup_manager.ui.common.ManagementState
import com.example.zupzup_manager.ui.custom.CustomRoundedCornersTransformation
import com.example.zupzup_manager.ui.item.clicklistener.ItemBtnClickListener
import com.example.zupzup_manager.ui.item.recyclerview.ItemRcvAdapter
import kotlin.math.roundToInt

@BindingAdapter("item")
fun bindManagementDetailToRecyclerView(
    recyclerView: RecyclerView,
    dataList: List<ItemModel>
) {
    (recyclerView.adapter as ItemRcvAdapter).submitList(dataList.toList())
}

@BindingAdapter("imgUrl")
fun bindImageUrlToImageView(imageView: ImageView, imgUrl: String) {

    val density: Float = imageView.context.resources.displayMetrics.density
    val px = (8.toFloat() * density).roundToInt()
    Glide
        .with(imageView.context)
        .load(imgUrl)
        .transform(
            CenterCrop(),
            CustomRoundedCornersTransformation(px)
        )
        // disk 캐싱 추가
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}

@BindingAdapter("clickListener", "itemId", "tvConfirmedAmount")
fun bindBindingHelperToAmountButtonInList(
    ivBtnModifyAmount: ImageView,
    clickListener: ItemBtnClickListener,
    itemId: Long,
    tvConfirmedAmount: TextView
) {
    ivBtnModifyAmount.setOnClickListener {
        when (ivBtnModifyAmount.id) {
            R.id.btn_plus -> {
                if (tvConfirmedAmount.text.toString().toInt() < 100) {
                    clickListener.onPlusItemModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().plus(1).toString()
                }
            }

            R.id.btn_minus -> {
                if (tvConfirmedAmount.text.toString().toInt() > 0) {
                    clickListener.onMinusItemModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().minus(1).toString()
                }
            }
        }
    }
}

@BindingAdapter("managementMode")
fun changeTitleText(
    title: TextView,
    managementState: ManagementState
) {
    when (managementState) {
        ManagementState.DefaultMode -> {
            title.text = "제품 관리"
        }

        ManagementState.AmountMode -> {
            title.text = "수량 수정"
        }

        ManagementState.InfoMode -> {
            title.text = "정보 수정"
        }
    }
}