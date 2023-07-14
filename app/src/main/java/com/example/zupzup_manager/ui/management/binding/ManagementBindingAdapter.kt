package com.example.zupzup_manager.ui.management.binding

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.zupzup_manager.R
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.ui.common.ManagementState
import com.example.zupzup_manager.ui.custom.CustomRoundedCornersTransformation
import com.example.zupzup_manager.ui.management.clicklistener.ManagementBtnClickListener
import com.example.zupzup_manager.ui.management.recyclerview.ManagementRcvAdapter
import kotlin.math.roundToInt

@BindingAdapter("merchandise")
fun bindManagementDetailToRecyclerView(
    recyclerView: RecyclerView,
    dataList: List<MerchandiseModel>
) {
    (recyclerView.adapter as ManagementRcvAdapter).submitList(dataList.toList())
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
        .into(imageView)
}

@BindingAdapter("clickListener", "itemId", "tvConfirmedAmount")
fun bindBindingHelperToAmountButton(
    ivBtnModifyAmount: ImageView,
    clickListener: ManagementBtnClickListener,
    itemId: Long,
    tvConfirmedAmount: TextView
) {
    ivBtnModifyAmount.setOnClickListener {
        when (ivBtnModifyAmount.id) {
            R.id.btn_plus -> {
                if (tvConfirmedAmount.text.toString().toInt() < 100) {
                    clickListener.onPlusMerchandiseModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().plus(1).toString()
                }
            }

            R.id.btn_minus -> {
                if (tvConfirmedAmount.text.toString().toInt() > 0) {
                    clickListener.onMinusMerchandiseModifiedAmountBtnClick(itemId)
                    tvConfirmedAmount.text =
                        tvConfirmedAmount.text.toString().toInt().minus(1).toString()

                }
            }
        }
    }
}

@BindingAdapter("backButton")
fun hideBackButton(
    backButton: LinearLayout,
    managementState: ManagementState
) {
    when (managementState) {
        ManagementState.DefaultMode -> {
            println("default")
            backButton.visibility = View.GONE
        }

        else -> {
            println("else")
            backButton.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("amountState", "price")
fun visibleModifyAmount(
    amount: RelativeLayout,
    managementState: ManagementState,
    price: RelativeLayout
) {
    when (managementState) {
        ManagementState.AmountMode -> {
            println("Amount")
            amount.visibility = View.VISIBLE
            price.visibility = View.GONE
        }

        else -> {
            println("else")
            amount.visibility = View.GONE
            price.visibility = View.VISIBLE
        }
    }
}