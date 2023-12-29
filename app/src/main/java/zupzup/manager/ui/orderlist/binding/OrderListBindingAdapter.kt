package zupzup.manager.ui.orderlist.binding

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import zupzup.manager.R
import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.ui.common.OrderStateMapper
import zupzup.manager.ui.common.UiState
import zupzup.manager.ui.orderlist.recyclerview.OrderListRcvAdapter

@BindingAdapter("uiState", "filter", "emptyIconLayout")
fun bindOrderListToRecyclerView(
    recyclerView: RecyclerView,
    uiState: UiState<List<OrderModel>>?,
    filter: String,
    emptyIconLayout: LinearLayout
) {
    when (uiState) {
        is UiState.Success -> {
            val orderList: List<OrderModel> = when (filter) {
                "NEW" -> {
                    emptyIconLayout.findViewById<TextView>(R.id.tv_empty_text).text =
                        "지금은 신규 주문이 없어요"
                    uiState.data.filter { it.orderStatus == "NEW" }
                }

                "CONFIRM" -> {
                    emptyIconLayout.findViewById<TextView>(R.id.tv_empty_text).text =
                        "지금은 확정된 주문이 없어요"
                    uiState.data.filter { it.orderStatus == "CONFIRM" }
                }

                else -> {
                    emptyIconLayout.findViewById<TextView>(R.id.tv_empty_text).text =
                        "지금은 완료 및 취소 주문이 없어요"
                    uiState.data.filter { it.orderStatus != "NEW" && it.orderStatus != "CONFIRM" }
                }
            }
            if (orderList.isNotEmpty()) {
                emptyIconLayout.visibility = View.GONE
            }
            (recyclerView.adapter as OrderListRcvAdapter).submitList(orderList)
        }

        is UiState.Error -> {
            Toast.makeText(recyclerView.context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        }

        is UiState.Empty -> {
            recyclerView.visibility = View.GONE
        }

        else -> {
            Toast.makeText(recyclerView.context, "로딩중", Toast.LENGTH_SHORT).show()
        }
    }
}


@BindingAdapter("uiState")
fun bindEmptyListToLinearLayout(
    linearLayout: LinearLayout,
    uiState: UiState<List<OrderModel>>?
) {
    when (uiState) {
        is UiState.Success -> {
        }

        is UiState.Error -> {
            Toast.makeText(linearLayout.context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        }

        is UiState.Empty -> {
        }

        else -> {
            Toast.makeText(linearLayout.context, "로딩중", Toast.LENGTH_SHORT).show()
        }
    }
}

@BindingAdapter("orderStatus")
fun bindOrderStatusToTextView(
    constraintLayout: ConstraintLayout,
    orderStatus: String
) {
    (constraintLayout.background as GradientDrawable).setColor(
        ContextCompat.getColor(
            constraintLayout.context,
            OrderStateMapper.getStateBackgroundColor(orderStatus)
        )
    )
}

@BindingAdapter("orderTime")
fun bindOrderTimeToTextView(
    textView: TextView,
    orderTime: String
) {
    val year = orderTime.substring(0, 4)
    val month = orderTime.substring(5, 7)
    val day = orderTime.substring(8, 10)
    val time = orderTime.substring(11)

    textView.text = year + "년 " + month + "월 " + day + "일 " + time
}

@BindingAdapter("visitTime")
fun bindOrderVisitTimeToTextView(
    textView: TextView,
    visitTime: String
) {
    val timePattern = Regex("\\d{4}-\\d{2}-\\d{2} (\\d{1,2}):(\\d{2})")
    val matchResult = timePattern.find(visitTime)
    if (matchResult != null) {
        // HH:MM 형태로 포맷팅
        val (hour, minute) = matchResult.destructured
        textView.text = "${hour.padStart(2, '0')}:${minute}"
    }
}