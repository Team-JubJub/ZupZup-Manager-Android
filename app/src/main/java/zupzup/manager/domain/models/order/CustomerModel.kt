package zupzup.manager.domain.models.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerModel(
    val id: Long,
    val name: String,
    val phoneNumber: String,
) : Parcelable