package com.example.zupzup_manager.ui.management.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.zupzup_manager.ui.management.models.ManagementViewType
import com.example.zupzup_manager.ui.management.recyclerview.ManagementRcvAdapter
import kotlin.math.roundToInt

@BindingAdapter("managementDetailBody")
fun bindManagementDetailToRecyclerView(
    recyclerView: RecyclerView,
    dataList: List<ManagementViewType>
) {
    (recyclerView.adapter as ManagementRcvAdapter).submitList(dataList.toList())
}

@BindingAdapter("imgUrl")
fun bindImageUrlToImageView(imageView: ImageView, imgUrl: String) {

    val density: Float = imageView.context.resources.displayMetrics.density
    val px = (14.toFloat() * density).roundToInt()
    Glide
        .with(imageView.context)
        .load(imgUrl)
        .transform(
            CenterCrop(),
            //CustomRoundedCornersTransformation(px)
        )
        .into(imageView)
}