package zupzup.manager.domain.models.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModifyItemModel (
    val itemName: String,
    val imageURL: String,
    val itemPrice: Int,
    val salePrice: Int,
    var itemCount: Int
): Parcelable