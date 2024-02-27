package zupzup.manager.domain.models.store

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModifyStoreModel (
    val openTime: String = "",
    val closeTime: String = "",
    val saleTimeStart: String = "",
    val saleTimeEnd: String = "",
    val closedDay: String? = ""
): Parcelable