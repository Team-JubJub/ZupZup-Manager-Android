package zupzup.manager.ui.orderdetail

import zupzup.manager.domain.models.order.OrderModel

interface OrderDetailFragmentClickListener {

    fun confirmOrder(orderModel: OrderModel, isPartial: Boolean)

    fun rejectOrder(orderModel: OrderModel)

    fun cancelOrder(orderId: Long)

    fun completeOrder(orderId: Long)

    fun onBackBtnClick()
}