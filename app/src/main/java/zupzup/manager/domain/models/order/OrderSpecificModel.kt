package zupzup.manager.domain.models.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderSpecificModel(
    val itemId: Long,
    val itemName: String,
    val imageUrl: String,
    val itemPrice: Int,
    val salePrice: Int,
    val itemCount: Int
) : Parcelable
